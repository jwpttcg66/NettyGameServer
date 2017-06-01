# -*- coding: utf-8 -*-

import traceback
import numfmt
import xlsconfig
import util
from tps import tp0

def format_number_with_xlrd(f, cell, wb):
	xf = wb.xf_list[cell.xf_index]
	fmt_key = xf.format_key
	fmt = wb.format_map[fmt_key]
	s_fmt = fmt.format_str
	a_fmt = numfmt.extract_number_format(s_fmt)
	if a_fmt:
		s_f = numfmt.format_number(f, a_fmt, ',', '.')
	else:
		s_f = "%g" % f
	return s_f

def format_number_with_openpyxl(f, cell, wb):
	s_fmt = cell.number_format
	a_fmt = numfmt.extract_number_format(s_fmt)
	if a_fmt:
		return numfmt.format_number(f, a_fmt, ',', '.')
	else:
		return "%g" % f

format_number = format_number_with_openpyxl

FAST_CONVERTER = {
	"byte" : int,
	"short" : int,
	"int" : tp0.to_int,
	"float" : tp0.to_float,
	"string" : tp0.to_str,
	"bool" : tp0.to_bool,
	"boolean" : tp0.to_bool,
}

class ConverterInfo:
	def __init__(self, info):
		self.header = info[0]
		self.field = info[1]
		self.convert = info[2]
		self.is_default = info[3] if len(info) > 3 else False
		self.exist_default_value = len(info) > 4
		self.default_value = info[4] if len(info) > 4 else None

class BaseParser(object):

	def __init__(self, filename, module, sheet_index=0):
		super(BaseParser, self).__init__()

		self.key_name = "ID"
		self.is_multi_key = False

		self.filename = filename
		self.module = module
		self.sheet_index = sheet_index

		# 转换器。列索引 -> 转换器(ConverterInfo)
		self.converters = {}

		# 最终生成的数据表。key是key_name对应的数据。
		# 如果表格是multi_key，则value是一个数组，包含了所有key相同的行。
		self.sheet = {}

		# 表头的类型数据
		self.sheet_types = {}

		self.field_2_col = {}

		self.argument_row_index = xlsconfig.SHEET_ROW_INDEX["argument"]
		self.header_row_index = xlsconfig.SHEET_ROW_INDEX["header"]
		self.data_row_index = xlsconfig.SHEET_ROW_INDEX["data"]

		self.workbook = None
		self.worksheet = None

		# excel表头第一行的参数
		self.arguments = {}

	def run(self):
		self.do_parse()

	# 将单元格数据转换成字符串形式。
	# 因为'123,456'格式的字符串，会被Excel存贮成浮点数：123456，
	# 而openpyxl仅仅是读取存贮的数据，不会自动将数字还原成字符串，所以这里手动进行转换。
	def extract_cell_value(self, cell):
		value = cell.value
		tp = type(value)

		if value is None:
			value = ""
		elif tp == float or tp == long or tp == int:
			value = format_number(value, cell, self.workbook)
		elif tp == unicode:
			value = value.encode("utf-8").strip()
		elif tp == str:
			value = value.strip()
		else:
			msg = "不支持的数据类型: %s -> '%s'" % (type(value), value)
			raise ValueError, msg

		return value

	def convert_cell(self, row, col, value, output_row):
		converter = self.converters[col]
		if converter is None: return

		ret = None
		if value == "":
			if not converter.is_default:
				raise ValueError, "该项必填"

		else:
			ret = converter.convert(value)

		if ret is None and converter.is_default:
			if not converter.exist_default_value:
				return
			ret = converter.default_value

		output_row[converter.field] = ret

	def do_parse(self):
		import openpyxl
		self.workbook = openpyxl.load_workbook(self.filename)

		sheets = self.workbook.worksheets
		if self.sheet_index >= len(sheets):
			log_error("Excel表'%s'没有子表'%d'", self.filename, self.sheet_index)
			return

		table = sheets[self.sheet_index]
		self.worksheet = table

		rows = list(table.rows)
		self.parse_arguments(rows)
		self.parse_header(rows)

		if self.data_row_index >= len(rows):
			return

		ncols = len(self.converters)

		# the remain rows is raw data.
		for r in xrange(self.data_row_index, len(rows)):
			cells = rows[r]
			
			# 遇到空白行，表示解析完成
			first_value = cells[0].value
			if first_value == '' or first_value is None: break

			current_row_data = {}
			for c in xrange(ncols):
				value = self.extract_cell_value(cells[c])
				try:
					self.convert_cell(r, c, value, current_row_data)
				except:
					traceback.print_exc()

					util.log_error("单元格(%d, %s) = [%s] 数据解析失败", r + 1, util.int_to_base26(c), value)

			self.add_row(current_row_data)

		return

	def add_row(self, current_row_data):
		if self.key_name not in current_row_data:
			raise ValueError, "没有找到Key'%s'" % (self.key_name, )

		key_value = current_row_data.pop(self.key_name)
		
		if self.is_multi_key:
			row = self.sheet.setdefault(key_value, [])
			row.append(current_row_data)

		else:
			if key_value in self.sheet:
				raise ValueError, "Key'%s'重复" % key_value

			self.sheet[key_value] = current_row_data

	def parse_header(self, rows):
		pass

	def parse_arguments(self, rows):
		row_index = self.argument_row_index
		arg_row = [self.extract_cell_value(cell) for cell in rows[row_index]]

		self.arguments = {}
		for col in xrange(0, len(arg_row), 2):
			header = arg_row[col]
			if header is None: break

			converter = xlsconfig.ARGUMENT_CONVERTER.get(header)
			if converter is None: continue

			field, type = converter
			method = FAST_CONVERTER[type.lower()]

			value = arg_row[col + 1]
			ret = None
			try:
				ret = method(value)
			except:
				traceback.print_exc()

				log_error("参数转换失败，(%d, %s) = [%s]", row_index + 1, util.int_to_base26(col), value)

			self.arguments[field] = ret

		return
