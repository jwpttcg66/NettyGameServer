# -*- coding: utf-8 -*-
import os
import sys
import traceback

import xlsconfig
import util

from parsers import DirectParser
from base_exporter import BaseExporter

class DirectExporter(BaseExporter):
	def __init__(self, input_path, exts):
		super(DirectExporter, self).__init__(input_path, exts)
		self.parser_class = DirectParser

	def run(self):
		self.gather_excels()

		sys.path.insert(0, xlsconfig.CONVERTER_PATH)
		sys.path.insert(0, xlsconfig.TEMP_PATH)

		self.export_excels()

		# 写出裸数据
		self.write_sheets(xlsconfig.EXPORT_STAGE_BEGIN)

		# direct模式下，暂不支持合并
		# self.merge_sheets()

		# 写出最终数据
		self.write_sheets(xlsconfig.EXPORT_STAGE_FINAL)

		self.write_file_list()
		
		self.write_configs()

		self.run_postprocessor()

		sys.path.remove(xlsconfig.CONVERTER_PATH)
		sys.path.remove(xlsconfig.TEMP_PATH)

	def export_excels(self):
		for infile in self.excel_files:
			converter_name, outfile, sheet_index = self.find_converter_info(infile)

			if self.export_excel_to_python(infile, outfile, converter_name, sheet_index):
				pass

			elif not xlsconfig.FORCE_RUN:
				return
		return

	def export_excel_to_python(self, infile, outfile, converter_name, sheet_index = 0):
		input_path = os.path.join(xlsconfig.INPUT_PATH, infile)
		output_path = os.path.join(xlsconfig.TEMP_PATH, outfile + ".py")

		if xlsconfig.FAST_MODE and util.if_file_newer(output_path, input_path):
			data_module = util.import_file(outfile)

		else:
			print infile, "-", converter_name
			parser = self.parser_class(input_path, None, sheet_index)
			try:
				parser.run()
			except:
				traceback.print_exc()
				return False

			data_module = self.create_data_module(infile, outfile, converter_name, parser)
			if data_module is None: return False

		self.store_data_module(data_module)
		return True

	def find_converter_info(self, infile):
		outfile = os.path.splitext(infile)[0]
		converter_name = os.path.basename(outfile)
		return (converter_name, outfile, 0)



