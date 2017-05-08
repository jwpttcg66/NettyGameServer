// 此文件由导表工具自动生成，禁止手动修改。

package com.wolf.shoot.service.excel.entity;

public class EntityAi {

    private int        ID;        // 编号
    private int        step;      // 步骤
    private String     desc;      // 描述
    private String     position;  // 设置位置
    private String     path;      // 移动轨迹
    private float      wait;      // 等待
    private String     skills;    // 释放技能
    private String     jumps;     // 随机跳转
    private boolean    breakable; // 步骤是否可打断

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStep() {
        return step;
    }
    public void setStep(int step) {
        this.step = step;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public float getWait() {
        return wait;
    }
    public void setWait(float wait) {
        this.wait = wait;
    }

    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getJumps() {
        return jumps;
    }
    public void setJumps(String jumps) {
        this.jumps = jumps;
    }

    public boolean getBreakable() {
        return breakable;
    }
    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }


};
