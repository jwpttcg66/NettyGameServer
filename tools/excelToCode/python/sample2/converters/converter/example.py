# -*- coding: utf-8 -*-
from tps import tp0

KEY_NAME = "ID"

#表里是否存在多个相同的key值
MULTI_KEY = False

"""
CONFIG是一个数组，描述了如何将excel表格导出成脚本表文件。
CONFIG每个数组单元也是一个数组，每个元素最多有5个元素：
	索引 0: 对应excel表的表头（excel表中的第3行）
	索引 1：脚本中的列名
	索引 2：转换函数。将excel表中的单元格转换成脚本对象
	索引 3：表示此列是否可缺省，即是否可不填
	索引 4：缺省值。脚本中使用此值替代excel中没有填的位置，如果没有指定缺省值，脚本中就不会出现该单元格的数据。
"""
CONFIG = [
	("编号", "ID", int),
	("名称", "name", tp0.to_str),
	("描述", "describe", tp0.to_str, True),
	("品质", "quality", int, True, 0),
	("掉落关卡", "drop", tp0.to_int_list, True),
]

"""
REMOVED_FIELDS
CONFIG 里面需要要删除的字段，不会显示在注释中。
通常用于字段合并的情况，若干个字段合并成一个新的字段。

REMOVED_FIELDS = ("field1", ...)
"""

"""
HIDDEN_FIELDS
不需要显示在Excel中的字段。通常是程序自动生成的数据。

HIDDEN_FIELDS = ("field1", ...)
"""

"""
JAVA_TYPE_INFO
这里用于生成java代码时，做类型替换。

JAVA_TYPE_INFO = {
	"origin" : ("Vector3", "toVector3"),
	"rightTop" : ("Vector3", ),
}
"""

# 添加此函数，在导表结束之前可以做一些后处理
def post_process(data_module):
	pass

# 添加此函数，用于在导表结束之后进行一些数据合法性检查
def post_check(data_module, exporter):
	pass
