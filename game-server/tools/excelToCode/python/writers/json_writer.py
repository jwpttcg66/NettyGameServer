# -*- coding: utf-8 -*-
from base_writer import BaseWriter

FORMAT_CHARS = {
	'"'  : '\\"',
	'\\' : '\\\\',
	'\f' : '\\f',
	'\t' : '\\t',
	'\r' : '\\r',
	'\n' : '\\n',
	'\b' : '\\b',
}

def format_str(s):
	ret = []
	for ch in s: ret.append(FORMAT_CHARS.get(ch, ch))
	return "".join(ret)


class JsonWriter(BaseWriter):

	def begin_write(self):
		self.is_started = False
		self._output_line(0, "{")

	def end_write(self):
		self.output("\n")
		self._output_line(0, "}")

	def ensure_split(self):
		if self.is_started:
			self.output(",\n")
		self.is_started = True

	def write_sheet(self, name, sheet):
		self.write_value(name, sheet, 2)

	def write_value(self, name, value, max_indent = 2):
		self.ensure_split()

		indent = 1

		self._output(indent, '"%s" : ' % name)
		self.write(value, indent, max_indent)

		self.flush()

	def write_comment(self, comment): pass

	def write(self, value, indent, max_indent):
		output = self.output

		if value is None:
			return output("null")

		tp = type(value)
		if tp == bool:
			output("true" if value else "false")

		elif tp == int:
			output("%d" % value)

		elif tp == float:
			output("%g" % value)

		elif tp == str:
			output('"%s"' % format_str(value))

		elif tp == unicode:
			output('"%s"' % format_str(value.encode("utf-8")))

		elif tp == tuple or tp == list:
			output("[")
			indent += 1
			if indent <= max_indent: output("\n")

			for i, v in enumerate(value):
				if indent <= max_indent: self._output(indent)

				self.write(v, indent, max_indent)
				if i + 1 < len(value): output(", ")

				if indent <= max_indent: output("\n")

			if indent <= max_indent:
				self._output(indent - 1, "]")
			else:
				output("]")
			indent -= 1

		elif tp == dict:
			output("{")
			indent += 1
			if indent <= max_indent: output("\n")

			keys = value.keys()
			keys.sort()
			for i, k in enumerate(keys):
				if indent <= max_indent: self._output(indent)

				self.write(str(k), indent, max_indent)
				output(" : ")
				self.write(value[k], indent, max_indent)
				if i + 1 < len(value): output(", ")

				if indent <= max_indent: output("\n")

			if indent <= max_indent:
				self._output(indent - 1, "}")
			else:
				output("}")
			indent -= 1

		else:
			raise TypeError, "unsupported type %s" % (str(tp), )

		return
