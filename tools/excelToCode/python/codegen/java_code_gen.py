# -*- coding: utf-8 -*-
import os
import util
import xlsconfig
from base_code_gen import BaseCodeGen
from tps import tp0

def type2string(tp):
	if tp == int or tp == tp0.to_int: return "int"
	if tp == long: return "long"
	if tp == float or tp == tp0.to_float: return "float"
	if tp == bool or tp == tp0.to_bool: return "boolean"
	return "String"

BUILTIN_TYPES = set((
	"int", "long", "float", "boolean", "String",
))

INDENTS = [" " * (i * 4) for i in xrange(10)]

class JavaCodeGen(BaseCodeGen):

	def run(self):
		src_name = self.module_name.split('.')[-1]
		self.class_name = util.to_class_name(src_name)

		name_format = util.to_utf8(self.generator_info.get("name_format"))
		if name_format:
			self.class_name = name_format % self.class_name

		self.file_path = os.path.join(self.output_path, self.class_name + ".java")

		path = os.path.dirname(self.file_path)
		util.safe_makedirs(path)

		self.write_line(0, "// 此文件由导表工具自动生成，禁止手动修改。")
		self.write_line()

		package = util.to_utf8(self.generator_info.get("package", xlsconfig.DEFAULT_JAVA_PACKAGE))
		
		self.write_line(0, "package %s;" % package)
		self.write_line()

		imports = self.generator_info.get("imports")
		if imports:
			for imp in imports:
				self.write_line(0, "import %s;" % imp.encode("utf-8"))
			self.write_line()

		items = self.collect_members(self.module)

		self.gen_class(items, 0)

		self.save_to_file(self.file_path)

	def collect_members(self, config):
		# info = (0, "ID", "编号", "int", )
		
		items = []
		for k, info in config.iteritems():
			col, field, text, type = info
			items.append([field, text, type, None])

		items.sort(key = lambda v: v[0])
		return items

	def gen_class(self, items, indent):
		self.write(indent, "public class ", self.class_name)
		
		base = self.generator_info.get("base")
		if base: self.output(" extends ", base.encode("utf-8"))

		interface = self.generator_info.get("interface")
		if interface: self.output(" implements ", interface.encode("utf-8"))

		self.output(" {")
		self.write_line()
		self.write_line()

		indent += 1
		self.gen_field_list(items, indent)
		#self.gen_contructor(self.class_name, indent)
		#self.gen_init_method(items, indent)
		self.gen_get_set(items, indent)
		indent -= 1

		self.write_line(indent, "};")

	def gen_field_list(self, items, indent):
		for item in items:
			name, comment, type, _ = item

			self.write_line(indent, "private %-10s %-10s // %s" % (type, name + ";", comment))

		self.write_line()

	def gen_get_set(self, items, indent):
		for item in items:
			name, comment, type, _ = item

			java_name = util.to_class_name(name)

			self.write_line(indent, "public %s get%s() {" % (type, java_name))
			self.write_line(indent + 1, "return %s;" % name)
			self.write_line(indent, "}")

			self.write_line(indent, "public void set%s(%s %s) {" % (java_name, type, name))
			self.write_line(indent + 1, "this.%s = %s;" % (name, name))
			self.write_line(indent, "}")

			self.write_line()

		self.write_line()

	def gen_contructor(self, class_name, indent):
		self.write_line(indent, "public %s(JsonObject row) {" % class_name)

		indent += 1
		self.write_line(indent, "init(row);")
		indent -= 1

		self.write_line(indent, "}")
		self.write_line()

	def gen_init_method(self, items, indent):
		self.write_line(indent, "public void init(JsonObject row) {")

		indent += 1
		for i, item in enumerate(items):
			name, comment, type, method = item

			if type in BUILTIN_TYPES:
				self.write_line(indent, "%-10s = (%s)row[%d];" % (name, type, i))
			elif method:
				self.write_line(indent, "%-10s = %s(row[%d]);" % (name, method, i))
			else:
				self.write_line(indent, "%-10s = new %s(row[%d]);" % (name, type, i))

		indent -= 1

		self.write_line(indent, "}")
		self.write_line()
