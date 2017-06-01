# -*- coding: utf-8 -*-
import traceback
import xlsconfig
import util
from tps import tp0
from base_parser import ConverterInfo, BaseParser, FAST_CONVERTER

# 利用Excel表头描述，进行导表，不需要转换器
class DirectParser(BaseParser):

	def __init__(self, filename, module, sheet_index=0):
		super(DirectParser, self).__init__(filename, module, sheet_index)

		self.field_row_index = xlsconfig.SHEET_ROW_INDEX["field"]
		self.type_row_index = xlsconfig.SHEET_ROW_INDEX["type"]

	# 使用Excel表头提供的信息，构造转换器
	def parse_header(self, rows):
		header_row 	= [self.extract_cell_value(cell) for cell in rows[self.header_row_index]]
		field_row 	= [self.extract_cell_value(cell) for cell in rows[self.field_row_index]]
		type_row 	= [self.extract_cell_value(cell) for cell in rows[self.type_row_index]]

		for col, field in enumerate(field_row):
			if field == "": break

			self.converters[col] = None

			if field in self.field_2_col:
				util.log_error("列名'%s'重复，列：%s", field, util.int_to_base26(col))
				continue
			self.field_2_col[field] = col

			header = header_row[col] or field
			type = type_row[col] or "String"
			method = None
			try:
				method = FAST_CONVERTER[type.lower()]
			except:
				util.log_error("无效的类型'%s'，列：%s", type, util.int_to_base26(col))
				continue

			self.converters[col] = ConverterInfo((header, field, method, True))
			self.sheet_types[field] = (col, field, header, type)

		self.key_name = self.converters[0].field
		return

	def parse_arguments(self, rows):
		super(DirectParser, self).parse_arguments(rows)
		self.is_multi_key = self.arguments.get("multiKey", False)
