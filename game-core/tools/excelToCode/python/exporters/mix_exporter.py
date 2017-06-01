# -*- coding: utf-8 -*-
import re
import os
import sys
import xlsconfig
import util
from direct_exporter import DirectExporter
from tps import tp0

class MixExporter(DirectExporter):

	def __init__(self, input_path, exts):
		super(MixExporter, self).__init__(input_path, exts)
		self.converter_modules = {}

	def run(self):
		self.gather_excels()

		sys.path.insert(0, xlsconfig.CONVERTER_PATH)
		sys.path.insert(0, xlsconfig.TEMP_PATH)

		# export excels to temp python
		self.export_excels()

		self.write_sheets(xlsconfig.EXPORT_STAGE_BEGIN)

		self.merge_sheets()

		self.post_convert_sheets()

		self.post_process_sheets()

		self.post_check_sheets()

		self.write_sheets(xlsconfig.EXPORT_STAGE_FINAL)

		self.write_configs()

		self.run_postprocessor()

		sys.path.remove(xlsconfig.CONVERTER_PATH)
		sys.path.remove(xlsconfig.TEMP_PATH)

	def find_converter_info(self, infile):
		for value in xlsconfig.CONVENTION_TABLE:
			pattern = value[0]
			compiled_pattern = re.compile(pattern)

			if compiled_pattern.match(infile):
				converter_name 	= value[1]
				new_name 	= value[2] if len(value) > 2 else None
				sheet_index = value[3] if len(value) > 3 else 0

				outfile = None
				if new_name is None:
					outfile = os.path.splitext(infile)[0]
				else:
					outfile = compiled_pattern.sub(new_name, infile)

				return (converter_name, outfile, sheet_index)

		outfile = os.path.splitext(infile)[0]
		converter_name = os.path.basename(outfile)
		return (converter_name, outfile, 0)

	def find_converter(self, name):
		converter = self.converter_modules.get(name)
		if converter is None:
			full_name = xlsconfig.CONVERTER_ALIAS + "." + name
			full_path = os.path.join(xlsconfig.CONVERTER_PATH, full_name.replace(".", '/') + ".py")
			if os.path.exists(full_path):
				converter = util.import_file(full_name)
				converter._name = name
			self.converter_modules[name] = converter
		return converter

	def post_convert_row(self, field_2_cfg, key_value, row):
		keys = row.keys()
		for k in keys:
			cfg = field_2_cfg.get(k)
			if cfg is None: continue

			method = cfg[2]
			if method == tp0.use_empty:
				row.pop(k)
				continue

			try:
				row[k] = method(row[k])
			except:
				traceback.print_exc()
				util.log_error("列(%s, %s)二次转换失败，value = %s", str(key_value), cfg[0], str(row[k]))
		return

	def post_convert_sheet(self, data_module):
		converter = data_module.converter
		if converter is None: return

		field_2_cfg = getattr(converter, "FIELD_2_CFG", None)
		if field_2_cfg is None:
			field_2_cfg = {}
			setattr(converter, "FIELD_2_CFG", field_2_cfg)

			for cfg in converter.CONFIG:
				field = cfg[1]
				field_2_cfg[field] = cfg

		main_sheet = data_module.main_sheet
		for key, row in main_sheet.iteritems():
			if isinstance(row, list):
				for sub_row in row:
					self.post_convert_row(field_2_cfg, key, sub_row)
			else:
				self.post_convert_row(field_2_cfg, key, row)

	def post_convert_sheets(self):
		print "=== 转换为最终数据 ..."

		for data_module in self.data_modules.itervalues():
			self.post_convert_sheet(data_module)

		return
