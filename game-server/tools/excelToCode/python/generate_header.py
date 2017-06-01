# -*- coding: utf-8 -*-
import re
import os
import sys

import xlsconfig
from util import gather_all_files, import_file


def generate_header():
	create_header_in_path(xlsconfig.INPUT_PATH)


# 为所有excel表，生成表头。
def create_header_in_path(path):
	ext = ".xlsx" if xlsconfig.USE_OPENPYXL else ".xls"
	excel_files = gather_all_files(path, set((ext, )))
	print "total excel files:", len(excel_files)

	return create_header_for_excels(excel_files)


def create_header_for_excels(excel_files):
	print "=== create header for excels ..."

	if xlsconfig.USE_OPENPYXL:
		from gen_header_with_openpyxl import create_header
	else:
		from gen_header_with_xlrd import create_header

	sys.path.insert(0, xlsconfig.CONVERTER_PATH)

	for value in xlsconfig.CONVENTION_TABLE:
		pattern 	= value[0]
		converter 	= import_file(xlsconfig.CONVERTER_ALIAS + "." + value[1])
		if not getattr(converter, "AUTO_GEN_HEADER", True): continue

		new_name 	= value[2] if len(value) > 2 else None
		sheet_index = value[3] if len(value) > 3 else 0

		compiled_pattern = re.compile(pattern)

		for infile in excel_files:
			if not compiled_pattern.match(infile): continue

			input_file = os.path.join(xlsconfig.INPUT_PATH, infile)

			if create_header(input_file, converter, sheet_index):
				pass

			elif not xlsconfig.FORCE_RUN:
				return

	sys.path.remove(xlsconfig.CONVERTER_PATH)
	return

if __name__ == "__main__":
	generate_header()
