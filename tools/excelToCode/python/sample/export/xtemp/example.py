# -*- coding: utf-8 -*-

path = "example"

info = {
	"arguments": {"describe": "这是一张范例表", "multiKey": False, "version": 100, },
	"infile": "example.xlsx",
	"key_name": "ID",
	"multi_key": False,
	"outfile": "example",
	"parser": "example",
	"sheet_types": {"main_sheet": {"ID": (0, "ID", "编号", "int", ), "describe": (2, "describe", "描述", "String", ), "drops": (4, "drops", "掉落关卡", "String", ), "name": (1, "name", "名称", "String", ), "quality": (3, "quality", "品质", "int", ), }, },
}

main_sheet = {
	1: {"describe": "切菜用的", "drops": "1,2,3,4", "name": "菜刀", "quality": 1, },
	2: {"drops": "2", "name": "上方宝剑", "quality": 5, },
	3: {"name": "偃月弯刀", },
}

