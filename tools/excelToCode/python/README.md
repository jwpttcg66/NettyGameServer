导表工具
======================

Excel文件：
![](doc/images/direct-header@2x.png)

转换后的python数据表：
```python
# -*- coding: utf-8 -*-
# 此文件由导表工具自动生成，禁止手动修改。
# from example.xlsx

# A ID                  编号
# B name                名称
# C describe            描述
# D quality             品质
# E drop                掉落关卡
main_sheet = {
    1: {"describe": "切菜用的", "drop": [1, 2, 3, 4, ], "name": "菜刀", "quality": 1, },
    2: {"drop": [2, ], "name": "上方宝剑", "quality": 5, },
    3: {"name": "偃月弯刀", "quality": 0, },
}
```

# 原理
1. 构造exporter对象，开始整个导表流程
2. 搜索`INPUT_PATH`路径下的所有Excel文件
3. 构造parser对象，将Excel数据转换成python格式的中间数据表
4. 如果定义了后处理操作，exporter将对数据表执行后处理操作。比如表头合并，合法性检查
5. 实例化writer对象，将python表转换成最终的python、lua、json等格式的数据表。

# 准备
+ python 2.7
+ 安装python插件openpyxl。使用`pip install openpyxl`安装，或者在config文件配置`DEPENDENCIES`项，指定openpyxl的安装包路径，导表工具会自动安装。

# 工具用法

```shell
python main.py --export your_configure_file
```

main导表参数 | 说明
------------|--------
config_file | python格式的配置文件。配置文件的详细写法，参考[配置文件参数详解](doc/how-to-config.md)
--gen-code  | 生成类代码，目前仅支持Java。需要在config文件中，指定代码生成器参数`CODE_GENERATORS`
--export    | 执行导表
--fast-mode | 快速模式，仅重新解析最近修改过的Excel表。解析Excel表的过程非常慢，快速模式会使用已经生成的中间文件来避免二次解析Excel表
--force-run | 出错后是否继续进行导表。常用于发现更多的错误
--gen-header| 根据`转换器`描述信息，自动生成表头

# 范例
见`sample`和`sample2`目录。`sample`目录下的例子使用的是*直接模式(Direct)*，`sample2`目录下的例子使用的是*配置模式(Config)*。

文件名称 | 描述
--------|---------
config.py | 导表工具配置文件
export.bat/export.sh | 导表批处理文件
excels | 存放excel文件所在目录
converters | 转换器父级目录
converters/converter | 转换器脚本存放路径

# 文档
1. [配置文件参数详解](doc/how-to-config.md)
2. [表格添加方法](doc/how-to-create-excel.md)

