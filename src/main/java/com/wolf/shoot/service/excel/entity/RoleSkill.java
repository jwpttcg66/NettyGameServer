// 此文件由导表工具自动生成，禁止手动修改。

package com.wolf.shoot.service.excel.entity;

public class RoleSkill {

    private int        ID;        // 编号
    private String     desc;      // 描述
    private int        lvl;       // 等级
    private int        emitterID; // 发射器id
    private String     skills;    // 技能列表
    private int        mpStage;   // 能量百分比

    public int getID() {
        return ID;
    }
    public void setID(int _ID) {
        ID = _ID;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String _desc) {
        desc = _desc;
    }

    public int getLvl() {
        return lvl;
    }
    public void setLvl(int _lvl) {
        lvl = _lvl;
    }

    public int getEmitterID() {
        return emitterID;
    }
    public void setEmitterID(int _emitterID) {
        emitterID = _emitterID;
    }

    public String getSkills() {
        return skills;
    }
    public void setSkills(String _skills) {
        skills = _skills;
    }

    public int getMpStage() {
        return mpStage;
    }
    public void setMpStage(int _mpStage) {
        mpStage = _mpStage;
    }


};
