# -*- coding: utf-8 -*-
import os

INDENTS = [" " * (i * 4) for i in xrange(10)]

class BaseCodeGen(object):

	def __init__(self, module, module_name, output_path, generator_info):
		super(BaseCodeGen, self).__init__()
		self.content = []
		self.module = module
		self.module_name = module_name
		self.output_path = output_path
		self.generator_info = generator_info

	def run(self):
		pass
		
	def save_to_file(self, path):
		content = "".join(self.content)
		self.content = []

		origin_content = None
		if os.path.exists(path):
			with open(path, "rb") as f:
				origin_content = f.read()

		if content == origin_content: return

		print "生成", path

		with open(path, "wb") as f:
			f.write(content)

		return

	def output(self, *args):
		self.content.extend(args)

	def write(self, indent, *args):
		assert(type(indent) == int)
		if indent > 0: self.content.append(INDENTS[indent])
		self.content.extend(args)

	def write_line(self, indent = 0, *args):
		assert(type(indent) == int)
		if indent > 0: self.content.append(INDENTS[indent])
		self.content.extend(args)
		self.content.append("\n")

