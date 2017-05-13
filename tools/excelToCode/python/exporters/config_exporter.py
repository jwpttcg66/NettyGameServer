# -*- coding: utf-8 -*-
import os
import sys
import re
import traceback

import xlsconfig
import util

from parsers import ConfigParser
from base_exporter import BaseExporter

class ConfigExporter(BaseExporter):
	def __init__(self, input_path, exts):
		super(ConfigExporter, self).__init__(input_path, exts)
		self.converter_modules = {}
		self.parser_class = ConfigParser

	def run(self):
		self.gather_excels()

		sys.path.insert(0, xlsconfig.CONVERTER_PATH)
		sys.path.insert(0, xlsconfig.TEMP_PATH)

		# export excels to temp python
		self.export_excels()

		self.write_sheets(xlsconfig.EXPORT_STAGE_BEGIN)

		self.merge_sheets()

		self.post_process_sheets()

		self.post_check_sheets()

		self.write_sheets(xlsconfig.EXPORT_STAGE_FINAL)

		self.write_file_list()
		
		self.write_configs()

		self.run_postprocessor()

		sys.path.remove(xlsconfig.CONVERTER_PATH)
		sys.path.remove(xlsconfig.TEMP_PATH)

	def find_converter(self, name):
		converter = self.converter_modules.get(name)
		if converter is None:
			full_name = xlsconfig.CONVERTER_ALIAS + "." + name
			converter = util.import_file(full_name)
			converter._name = name
			self.converter_modules[name] = converter
		return converter

	def export_excels(self):
		file_2_converter = {}

		for value in xlsconfig.CONVENTION_TABLE:
			pattern 	= value[0]
			converter_name 	= value[1]
			new_name 	= value[2] if len(value) > 2 else None
			sheet_index = value[3] if len(value) > 3 else 0

			compiled_pattern = re.compile(pattern)

			for infile in self.excel_files:
				if not compiled_pattern.match(infile): continue
				if infile in file_2_converter:
					util.log_error("文件'%s'匹配到了多个转换器", infile)

				outfile = None
				if new_name is None:
					outfile = os.path.splitext(infile)[0]
				else:
					outfile = compiled_pattern.sub(new_name, infile)

				if self.export_excel_to_python(infile, outfile, converter_name, sheet_index):
					pass

				elif not xlsconfig.FORCE_RUN:
					return

				file_2_converter[outfile] = value[1]
		return

	def export_excel_to_python(self, infile, outfile, converter_name, sheet_index = 0):
		converter = self.find_converter(converter_name)

		input_path = os.path.join(xlsconfig.INPUT_PATH, infile)
		output_path = os.path.join(xlsconfig.TEMP_PATH, outfile + ".py")
		converter_file = os.path.splitext(converter.__file__)[0] + ".py"

		if xlsconfig.FAST_MODE and util.if_file_newer(output_path, input_path) and util.if_file_newer(output_path, converter_file):
			data_module = util.import_file(outfile)

		else:
			print infile, "-", converter_name
			parser = self.parser_class(input_path, converter, sheet_index)
			try:
				parser.run()
			except:
				traceback.print_exc()
				return False

			data_module = self.create_data_module(infile, outfile, converter_name, parser)
			if data_module is None: return False

		self.store_data_module(data_module)
		return True


