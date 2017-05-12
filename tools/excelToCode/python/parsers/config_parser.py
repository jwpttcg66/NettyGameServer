# -*- coding: utf-8 -*-

import traceback
import xlsconfig
import util
from tps import tp0
from base_parser import ConverterInfo, BaseParser

class ConfigParser(BaseParser):

	def __init__(self, filename, module, sheet_index = 0):
		super(ConfigParser, self).__init__(filename, module, sheet_index)

		self.key_name = getattr(module, "KEY_NAME", "ID")
		self.is_multi_key = getattr(module, "MULTI_KEY", False)

		self.config = getattr(module, "CONFIG", None)
		
		self.header_2_config = {}

	def run(self):
		self.map_config_array_to_dict()
		self.do_parse()

	def map_config_array_to_dict(self):
		ret = {}
		for info in self.config:
			header = info[0]
			ret[header] = info
		self.header_2_config = ret

	def parse_header(self, rows):
		header_row = [self.extract_cell_value(cell) for cell in rows[self.header_row_index]]

		name_set = set()
		for col, header in enumerate(header_row):
			if header == "": break

			converter = None

			cfg = self.header_2_config.get(header)
			if cfg is None:
				print "警告：第(%s)列的表头'%s'没有被解析。%s" % (util.int_to_base26(col), header, self.filename, )

			else:
				converter = ConverterInfo(cfg)

				field = converter.field
				self.field_2_col[field] = col

				type = tp0.type2string(converter.convert)
				self.sheet_types[field] = (col, field, header, type)

			self.converters[col] = converter

		return

