# -*- coding: utf-8 -*-
import os
import sys
import xlsconfig
import util

import codegen

def generate_code():
	print "=== 生成代码类文件 ..."

	configure_file_path = os.path.join(xlsconfig.TEMP_PATH, "configures.py")
	if not os.path.exists(configure_file_path):
		return log_error("配置文件'%s'不存在", configure_file_path)

	sys.path.insert(0, xlsconfig.TEMP_PATH)
	configure_module = util.import_file("configures")
	sys.path.remove(xlsconfig.TEMP_PATH)

	for key, cfg in configure_module.configures.iteritems():
		_generate(cfg["types"], key)

def _generate(config, module_name):
	for generator_info in xlsconfig.CODE_GENERATORS:
		cls = getattr(codegen, generator_info["class"])
		output_path = generator_info["file_path"]

		gen = cls(config, module_name, output_path, generator_info)
		gen.run()
