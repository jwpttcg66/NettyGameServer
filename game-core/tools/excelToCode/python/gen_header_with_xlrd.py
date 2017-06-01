# -*- coding: utf-8 -*-
import xlrd
import xlwt
from xlutils.copy import copy as xlcopy
from tps.tp0 import type2string

style_field = xlwt.Style.easyxf('pattern: pattern solid, fore_colour orange;')
style_type = xlwt.Style.easyxf('pattern: pattern solid, fore_colour sky_blue;')

def create_header(input_file, converter, sheet_index):
	header_row = 2
	type_row = 3

	book = xlrd.open_workbook(input_file, formatting_info=True)
	table = book.sheet_by_index(sheet_index)

	headers = {}
	for i, field in enumerate(table.row_values(header_row)):
		if field == "": break
		headers[field] = i

	new_headers = []
	for field, value in converter.CONFIG.iteritems():
		field = field.decode("utf-8")
		if field in headers: continue

		type = value[1]
		new_headers.append((field, type))

	if len(new_headers) == 0: return True

	book = xlcopy(book)
	table = book.get_sheet(sheet_index)

	new_headers.sort(key = lambda x: x[0])
	col = len(headers)
	for field, type in new_headers:
		table.write(header_row, col, field, style_field)
		table.write(type_row, col, type2string(type), style_type)
		col += 1

	book.save(input_file)
	print "generate header for", input_file
	return True
