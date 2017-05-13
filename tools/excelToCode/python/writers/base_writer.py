# -*- coding: utf-8 -*-
from copy import copy
import util

INDENTS = [" " * (i * 4) for i in xrange(10)]

class BaseWriter(object):

	def __init__(self, file_path, data_module = None, generator_info = None):
		super(BaseWriter, self).__init__()
		self.file_path = file_path
		self.cache = []
		self.data_module = data_module
		self.generator_info = generator_info
		
		self.open_file()

	def __fini__(self):
		if self.handle: self.close()

	def open_file(self):
		self.handle = open(self.file_path, "wb")

	def flush(self):
		if len(self.cache) == 0: return

		text = "".join(self.cache)
		self.cache = []

		self.handle.write(text)

	def close(self):
		self.flush()
		self.handle.close()
		self.handle = None


	def begin_write(self): pass
	def end_write(self): pass

	def write_sheet(self, name, sheet): pass
	def write_value(self, name, value): pass
	def write_comment(self, comment): pass

	def output(self, *args):
		self.cache.extend(args)

	def _output(self, indent, *args):
		assert(type(indent) == int)
		if indent > 0: self.cache.append(INDENTS[indent])
		self.cache.extend(args)

	def _output_line(self, indent = 0, *args):
		assert(type(indent) == int)
		if indent > 0: self.cache.append(INDENTS[indent])
		self.cache.extend(args)
		self.cache.append("\n")

	def write_module(self, module):
		output = self.output

		for k in sorted(module.iterkeys()):
			v = module[k]
			if isinstance(v, dict):
				self.write_sheet(k, v)
			else:
				self.write_value(k, v)

			output("\n")

		self.flush()

	def write_types_comment(self, sheet_name):
		if not self.data_module: return

		module_info = self.data_module.info

		sheet_types = module_info["sheet_types"].get(sheet_name)
		if sheet_types is None: return

		sheet_types = sheet_types.values()
		sheet_types.sort(key = lambda v : v[0])
		for info in sheet_types:
			col, field, text, type = info
			col_name = util.int_to_base26(col) if col is not None else "None"
			comment = "%s\t%-20s%s" %(col_name, field, text)
			self.write_comment(comment)

