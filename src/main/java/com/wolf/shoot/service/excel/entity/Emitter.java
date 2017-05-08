// 此文件由导表工具自动生成，禁止手动修改。

package com.wolf.shoot.service.excel.entity;

public class Emitter {

    private int        ID;        // 编号
    private String     name;      // 名称
    private String     desc;      // 描述
    private int        type;      // 类型
    private float      interval;  // 发射间隔
    private int        count;     // 发射组数
    private String     groups;    // 组ID
    private String     intervals; // 组内间隔
    private boolean    blockMove; // 释放时禁止移动
    private boolean    playOrigin; // 是否原地释放

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

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public float getInterval() {
        return interval;
    }
    public void setInterval(float interval) {
        this.interval = interval;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getGroups() {
        return groups;
    }
    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getIntervals() {
        return intervals;
    }
    public void setIntervals(String intervals) {
        this.intervals = intervals;
    }

    public boolean getBlockMove() {
        return blockMove;
    }
    public void setBlockMove(boolean blockMove) {
        this.blockMove = blockMove;
    }

    public boolean getPlayOrigin() {
        return playOrigin;
    }
    public void setPlayOrigin(boolean playOrigin) {
        this.playOrigin = playOrigin;
    }


};
