// 此文件由导表工具自动生成，禁止手动修改。

package com.snowcattle.game.service.dict.entity;

import com.snowcattle.game.service.dict.IDict;

public class RoleSkill implements IDict {

    private int        ID;        // 编号
    private String     desc;      // 描述
    private int        lvl;       // 等级
    private int        emitterID; // 发射器id
    private String     skills;    // 技能列表
    private int        mpStage;   // 能量百分比

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getLvl() {
        return lvl;
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getEmitterID() {
        return emitterID;
    }
    public void setEmitterID(int emitterID) {
        this.emitterID = emitterID;
    }

    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getMpStage() {
        return mpStage;
    }
    public void setMpStage(int mpStage) {
        this.mpStage = mpStage;
    }


};
