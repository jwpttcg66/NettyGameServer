// 此文件由导表工具自动生成，禁止手动修改。

package com.snowcattle.game.service.excel.entity;

public class Enemy {

    private int        ID;        // 编号
    private String     name;      // 名称
    private String     desc;      // 描述
    private String     className; // 游戏类
    private int        model;     // 模型编号
    private int        icon;      // 图标编号
    private int        lvl;       // 等级
    private String     aiSkills;  // AI技能循环
    private String     skills;    // 技能列表
    private String     growth;    // 属性成长
    private int        hp;        // 基础血量
    private float      aiInterval; // AI时间间隔
    private float      moveSpeed; // 移动速度
    private String     deadSound; // 死亡音效

    public int getID() {
        return ID;
    }
    public void setID(int _ID) {
        ID = _ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String _name) {
        name = _name;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String _desc) {
        desc = _desc;
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String _className) {
        className = _className;
    }

    public int getModel() {
        return model;
    }
    public void setModel(int _model) {
        model = _model;
    }

    public int getIcon() {
        return icon;
    }
    public void setIcon(int _icon) {
        icon = _icon;
    }

    public int getLvl() {
        return lvl;
    }
    public void setLvl(int _lvl) {
        lvl = _lvl;
    }

    public String getAiSkills() {
        return aiSkills;
    }
    public void setAiSkills(String _aiSkills) {
        aiSkills = _aiSkills;
    }

    public String getSkills() {
        return skills;
    }
    public void setSkills(String _skills) {
        skills = _skills;
    }

    public String getGrowth() {
        return growth;
    }
    public void setGrowth(String _growth) {
        growth = _growth;
    }

    public int getHp() {
        return hp;
    }
    public void setHp(int _hp) {
        hp = _hp;
    }

    public float getAiInterval() {
        return aiInterval;
    }
    public void setAiInterval(float _aiInterval) {
        aiInterval = _aiInterval;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }
    public void setMoveSpeed(float _moveSpeed) {
        moveSpeed = _moveSpeed;
    }

    public String getDeadSound() {
        return deadSound;
    }
    public void setDeadSound(String _deadSound) {
        deadSound = _deadSound;
    }


};
