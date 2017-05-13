# -*- coding: utf-8 -*-
import os
import json
import sys
import imp

import xlsconfig

######################################################################
### 加载配置文件。cfg_file是json格式的文件。
### 注意，json中读取出来的字符串格式都是unicode，路径需要转换成utf-8格式。
######################################################################
def load_configure(cfg_file):
	cfg_file = os.path.abspath(cfg_file)
	print "load configure file", cfg_file

	cfg = imp.load_source("custom_configure", cfg_file)

	root_path = os.path.dirname(cfg_file)

	xlsconfig.PROJECT_PATH = root_path

	xlsconfig.EXPORTER_CLASS = cfg.EXPORTER_CLASS

	xlsconfig.SHEET_ROW_INDEX = cfg.SHEET_ROW_INDEX

	xlsconfig.INPUT_PATH = join_path(root_path, cfg.INPUT_PATH)

	xlsconfig.TEMP_PATH = join_path(root_path, cfg.TEMP_PATH)

	xlsconfig.CONVERTER_PATH = join_path(root_path, getattr(cfg, "CONVERTER_PATH", "converters"))
	xlsconfig.CONVERTER_ALIAS = getattr(cfg, "CONVERTER_ALIAS", "converter")
	xlsconfig.CONVENTION_TABLE = getattr(cfg, "CONVENTION_TABLE", ())
	xlsconfig.MERGE_TABLE = getattr(cfg, "MERGE_TABLE", ())

	val = getattr(cfg, "ARGUMENT_CONVERTER", None)
	if val: xlsconfig.ARGUMENT_CONVERTER = val

	xlsconfig.CODE_GENERATORS = getattr(cfg, "CODE_GENERATORS", ())
	for info in xlsconfig.CODE_GENERATORS:
		info["file_path"] = join_path(root_path, info["file_path"])

	xlsconfig.DATA_WRITERS = getattr(cfg, "DATA_WRITERS", ())
	for info in xlsconfig.DATA_WRITERS:
		info["file_path"] = join_path(root_path, info["file_path"])

	xlsconfig.DEPENDENCIES = getattr(cfg, "DEPENDENCIES", {})
	for k in xlsconfig.DEPENDENCIES.keys():
		path = xlsconfig.DEPENDENCIES[k]
		xlsconfig.DEPENDENCIES[k] = join_path(root_path, path)

	xlsconfig.POSTPROCESSORS = getattr(cfg, "POSTPROCESSORS", ())
	for info in xlsconfig.POSTPROCESSORS:
		info["file_path"] = join_path(root_path, info["file_path"])

	xlsconfig.DEFAULT_JAVA_PACKAGE = getattr(cfg, "DEFAULT_JAVA_PACKAGE", None)


	# 加载完毕回调
	post_init_method = getattr(cfg, "post_init_method", None)
	if post_init_method:
		post_init_method()

	return

def join_path(*args):
	return os.path.normpath(os.path.join(*args))
