# -*- coding: utf-8 -*-
from base_writer import BaseWriter

IndentChar = '\t'
Indents = [IndentChar * i for i in xrange(1, 10)]

class LuaWriter(BaseWriter):

	def begin_write(self):
		self.output("\n", "module(...)", "\n\n")

	def write_sheet(self, name, sheet, maxIndent = 1):
		self.write_types_comment(name)

		output = self.output

		output(name, " = {\n")

		keys = sheet.keys()
		keys.sort()

		key_format = "\t[%d] = "
		if len(keys) > 0 and isinstance(keys[0], basestring):
			key_format = "\t[\"%s\"] = "

		for k in keys:
			output(key_format % k)
			self.write(sheet[k], 1, maxIndent)
			output(",\n")

			self.flush()

		output("}\n\n")

		if name == "main_sheet":
			self.write_value("main_length", len(sheet))

			keys = sheet.keys()
			keys.sort()
			self.write_value("main_keys", keys)

		self.flush()

	def write_value(self, name, value):
		self.write_types_comment(name)

		output = self.output

		output(name, " = ")
		self.write(value)
		output("\n\n")

		self.flush()

	def write_comment(self, comment):
		self.output("-- ", comment, "\n")

	def write(self, value, indent = 0, maxIndent = 0):
		output = self.output

		if value is None:
			return output("nil")

		tp = type(value)
		if tp == bool:
			output("true" if value else "false")

		elif tp == int:
			output("%d" % (value, ))

		elif tp == float:
			output("%g" % (value, ))

		elif tp == str:
			output('"%s"' %(value, ))

		elif tp == unicode:
			output('"%s"' % (value.encode("utf-8"), ))

		elif tp == tuple or tp == list or tp == set or tp == frozenset:
			output("{")
			indent += 1
			for v in value:
				self.write_indent(indent, maxIndent)
				self.write(v, indent, maxIndent)
				output(", ")
			indent -= 1
			if len(value) > 0 and indent + 1 <= maxIndent:
				self.write_indent(indent, maxIndent)
			output("}")

		elif tp == dict:
			output("{")
			indent += 1

			keys = value.keys()
			keys.sort()
			for k in keys:
				self.write_indent(indent, maxIndent)
				output("[")
				self.write(k, 0, 0)
				output("] = ")
				self.write(value[k], indent, maxIndent)
				output(", ")

			indent -= 1
			if len(value) > 0 and indent + 1 <= maxIndent:
				self.write_indent(indent, maxIndent)
			output("}")

		else:
			raise TypeError, "unsupported type %s" % (str(tp), )

		return

	def write_indent(self, indent, maxIndent):
		if indent <= maxIndent:
			self.output("\n")
			self.output(Indents[indent])
