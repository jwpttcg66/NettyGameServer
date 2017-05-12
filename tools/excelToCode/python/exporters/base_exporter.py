# -*- coding: utf-8 -*-

import os
import sys
import shutil
import re
import traceback
from copy import copy

import xlsconfig
import writers
import postprocess
import util

from tps import tp0

class DataModule(object):
	def __init__(self, path, info, sheet, converter = None):
		super(DataModule, self).__init__()
		self.path = path
		self.info = info
		self.main_sheet = sheet
		self.converter = converter

class NewConverter(object):
	def __init__(self, name, types, arguments, filename):
		super(NewConverter, self).__init__()
		self.name = name
		self.types = types
		self.arguments = arguments
		self.filename = filename

	def compare_types(self, types, filename):
		for k, info1 in self.types.iteritems():
			info2 = types.get(k)
			if info2 and self.if_info_equal(info1, info2): continue

			util.log_error("'%s':'%s'与'%s':'%s'表头不一致", filename, info2, info1, self.filename)
			return False
		return True

	def if_info_equal(self, info1, info2):
		for i in xrange(1, 4):
			if info1[i] != info2[i]:
				return False
		return True

class BaseExporter(object):
	def __init__(self, input_path, exts):
		super(BaseExporter, self).__init__()
		self.input_path = input_path
		self.exts = exts or (".xlsx", )

		self.excel_files = None
		self.data_modules = {}

		self.configures = {}

		self.parser_class = None

	def run(self):
		pass

	def export_excels(self):
		pass

	def find_converter(self, converter_name):
		pass

	def gather_excels(self):
		print "=== 搜索Excel文件 ..."

		self.excel_files = util.gather_all_files(self.input_path, self.exts)
		print "发现 %d 个excel文件" % len(self.excel_files)

	def store_data_module(self, data_module):
		module_info = data_module.info
		infile = module_info["infile"]
		outfile = module_info["outfile"]
		converter_name = module_info["parser"]

		converter = self.configures.get(converter_name)
		if converter is None:
			converter = NewConverter(converter_name, module_info["sheet_types"]["main_sheet"], module_info["arguments"], infile)
			self.configures[converter_name] = converter
		else:
			if not converter.compare_types(module_info["sheet_types"]["main_sheet"], infile):
				return False

		# 真实的转换器
		data_module.converter = self.find_converter(converter_name)
		self.data_modules[outfile] = data_module
		return True

	def create_data_module(self, infile, outfile, converter_name, parser):
		info = {
			"infile" : infile,
			"outfile" : outfile,
			"parser" : converter_name,
			"multi_key" : parser.is_multi_key,
			"key_name" : parser.key_name,
			"arguments" : parser.arguments,
			"sheet_types" : {"main_sheet" : parser.sheet_types},
		}

		util.ensure_package_exist(xlsconfig.TEMP_PATH, outfile)
		output_path = os.path.join(xlsconfig.TEMP_PATH, outfile + ".py")

		wt = writers.PyWriter(output_path, None)
		wt.begin_write()

		wt.write_value("path", outfile)
		wt.write_sheet("info", info)
		wt.write_sheet("main_sheet", parser.sheet)

		wt.end_write()
		wt.close()

		return DataModule(outfile, info, parser.sheet)

	def write_sheets(self, stage, name_format = "d_%s"):
		print "=== 保存阶段[%d]数据 ..." % stage

		for writer_info in xlsconfig.DATA_WRITERS:
			if writer_info["stage"] != stage: continue

			for data_module in self.data_modules.itervalues():
				outfile = data_module.info["outfile"]
				if name_format:
					fpath, fname = os.path.split(outfile)
					fname = name_format % fname
					outfile = os.path.join(fpath, fname)

				self.write_one_sheet(writer_info, outfile, data_module)
		return

	def write_one_sheet(self, writer_info, outfile, data_module):
		writer_name = writer_info["class"]

		full_path = os.path.join(writer_info["file_path"], outfile + writer_info["file_posfix"])

		util.ensure_folder_exist(full_path)

		writer_class = getattr(writers, writer_name)
		wt = writer_class(full_path, data_module, writer_info)
		wt.write_comment("此文件由导表工具自动生成，禁止手动修改。")
		wt.write_comment("from " + data_module.info["infile"])
		wt.output("\n")

		wt.begin_write()
		wt.write_sheet("main_sheet", data_module.main_sheet)

		extra_sheets = getattr(data_module, "extra_sheets", None)
		if extra_sheets is not None:
			assert(isinstance(extra_sheets, dict))
			wt.write_module(extra_sheets)

		wt.end_write()
		wt.close()
		return

	def write_file_list(self):
		print "=== 写入文件列表 ..."

		sheet = {}
		for k, v in self.data_modules.iteritems():
			sheet[k] = v.info["parser"]

		full_path = os.path.join(xlsconfig.TEMP_PATH, "files.py")
		util.ensure_folder_exist(full_path)

		wt = writers.PyWriter(full_path, None)
		wt.begin_write()
		wt.write_sheet("main_sheet", sheet)
		wt.end_write()
		wt.close()
		return

	def merge_sheets(self):
		print "=== 合并分表 ..."

		for value in xlsconfig.MERGE_TABLE:
			outfile = value[0]

			sub_files = []

			for i in xrange(1, len(value)):
				compiled_pattern = re.compile(value[i])

				for infile in self.data_modules.iterkeys():
					if compiled_pattern.match(infile):
						sub_files.append(infile)

			if len(sub_files) > 0:
				data_module = self.merge_sub_files(outfile, sub_files)

				for sub_file in sub_files:
					self.data_modules.pop(sub_file)

				self.data_modules[outfile] = data_module

		return

	def merge_sub_files(self, outfile, sub_files):
		assert(len(sub_files) > 0)
		print "合并", outfile, "<-", sub_files

		util.ensure_package_exist(xlsconfig.TEMP_PATH, outfile)
		new_path = os.path.join(xlsconfig.TEMP_PATH, outfile + ".py")

		info = None
		infiles = []
		sheet = {}

		for sub_file in sub_files:
			module = self.data_modules[sub_file]

			if info is None:
				info = copy(module.info)

			sheet.update(module.main_sheet)

			infiles.append(module.info["infile"])

			#os.remove(os.path.join(TEMP_PATH, sub_file + ".py"))

		info["infile"] = ", ".join(infiles)
		info["outfile"] = outfile

		wt = writers.PyWriter(new_path, None)
		wt.begin_write()
		wt.output("\n")
		wt.write_value("path", outfile)
		wt.write_sheet("info", info)
		wt.write_sheet("main_sheet", sheet)
		wt.end_write()
		wt.close()

		converter = self.find_converter(info["parser"])
		return DataModule(outfile, info, sheet, converter)

	def write_configs(self):
		output_path = os.path.join(xlsconfig.TEMP_PATH, "configures.py")

		wt = writers.PyWriter(output_path, None)
		wt.begin_write()

		sheet = {}
		for k, v in self.configures.iteritems():
			sheet[k] = {"types" : v.types, "arguments" : v.arguments }
		wt.write_sheet("configures", sheet)

		wt.end_write()
		wt.close()

	def post_process_sheet(self, data_module):
		converter = data_module.converter
		if converter is None: return

		process_method = getattr(converter, "post_process", None)
		if process_method is None:
			return

		try:
			data_module.extra_sheets = process_method(data_module)
		except:
			traceback.print_exc()
			util.log_error("后处理执行失败 '%s'", data_module.path)

		return

	def post_process_sheets(self):
		print "=== 执行后处理 ..."

		for data_module in self.data_modules.itervalues():
			self.post_process_sheet(data_module)

		return

	def post_check_sheet(self, data_module):
		converter = data_module.converter
		if converter is None: return

		check_method = getattr(converter, "post_check", None)
		if check_method is None:
			return

		try:
			check_method(data_module, self)
		except:
			traceback.print_exc()
			util.log_error("数据检查失败 '%s'", data_module.path)

		return

	def post_check_sheets(self):
		print "=== 数据合法性检查 ..."

		for data_module in self.data_modules.itervalues():
			self.post_check_sheet(data_module)

		return

	def run_postprocessor(self):
		print "=== 执行后处理器 ..."

		for generator_info in xlsconfig.POSTPROCESSORS:
			 cls = getattr(postprocess, generator_info["class"])
			 cls(self, generator_info).run()

		return
