// 此文件由导表工具自动生成，禁止手动修改。

package com.wolf.shoot.service.excel.entity;

public class SkillUpgrade {

    private int        ID;        // 编号
    private String     name;      // 描述
    private int        group;     // 技能组
    private int        lvl;       // 等级
    private int        skillPoint; // 升级消耗技能点
    private int        column;    // 显示位置

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }
    public void setGroup(int group) {
        this.group = group;
    }

    public int getLvl() {
        return lvl;
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getSkillPoint() {
        return skillPoint;
    }
    public void setSkillPoint(int skillPoint) {
        this.skillPoint = skillPoint;
    }

    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }


};
