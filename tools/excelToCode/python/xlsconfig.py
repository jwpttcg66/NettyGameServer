# -*- coding: utf-8 -*-

######################################################################
### 导表阶段。可以控制writer在哪个阶段输出写文件
######################################################################

EXPORT_STAGE_BEGIN = 1

EXPORT_STAGE_FINAL = 2

######################################################################
###
######################################################################

# 出错后是否继续运行。常用来显示出，所有出错的地方。
FORCE_RUN = False

# 是否使用openpyxl来解析excel表。目前总是使用该插件。
USE_OPENPYXL = True

# 是否执行快速导出。仅导出修改了的excel文件。
FAST_MODE = False

######################################################################
### 以下是需要配置的路径。可以通过配置文件来设置，见load_configure
######################################################################

# 导出类
EXPORTER_CLASS = "DirectExporter"

# Excel路径
INPUT_PATH      = "excels"

# 中间文件路径
TEMP_PATH       = "export/temp"

DEFAULT_JAVA_PACKAGE = "com.mygame.default.package"

# 生成的java代码输出路径
CODE_GENERATORS  = [
	{"class" : "JavaCodeGen", "file_path" : "export/java/code", "package" : "com.mygame.excel"}
]

# 输出数据配置。
DATA_WRITERS = [
	{"stage" : EXPORT_STAGE_FINAL, "class" : "LuaWriter", "file_path": "export/lua", "file_posfix" : ".lua"},
]

# 后处理器。在导表最后阶段调用，能够访问到exporter的所有数据。常用于生成文件列表。
POSTPROCESSORS = [
	{"class" : "JavaFileEnumProcessor", "file_path" : "export/java/excel/DictEnum.java", }
]

# 额外的初始化脚本。
POST_INIT_SCRIPT = ""

# python插件安装包路径。
DEPENDENCIES = {}

# Excel表头参数解析
ARGUMENT_CONVERTER = {
	"版本：" : ("version", "int"),
	"键值是否重复：" : ("multiKey", "bool"),
	"说明：" : ("describe", "string"),
}

# Excel表格数据所在行。索引从0开始，不填或填-1表示该行不存在。
SHEET_ROW_INDEX = {
	# Excel表头参数。通常是版本信息和说明等，该行必须存在。
	"argument" : 0,
	# 表头。该行必须存在。
	"header" : 1,
	# 数据首行索引。该行必须存在。
	"data" : 2,
	# 字段。Direct模式下，该行必须存在
	"field" : -1,
	# 类型行。Direct模式下，该行必须存在
	"type" : -1,
}

#----------- ConfigExporter/MixExporter需要的参数 ---------------------#

# 转换器所在的路径。
CONVERTER_PATH  = "converters"

# 转换器子目录，位于CONVERTER_PATH下。防止命名冲突。
CONVERTER_ALIAS = "converter"

# Excel与转换器对应关系表
CONVENTION_TABLE = ()

# 分表合并关系表
MERGE_TABLE = ()

#----------- ConfigExporter/MixExporter需要的参数 ---------------------#

######################################################################
###
######################################################################

