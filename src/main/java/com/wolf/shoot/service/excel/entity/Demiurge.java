// 此文件由导表工具自动生成，禁止手动修改。

package com.wolf.shoot.service.excel.entity;

public class Demiurge {

    private int        ID;        // 编号
    private String     name;      // 名称
    private boolean    visible;   // 是否可见
    private boolean    isManual;  // 是否手动触发
    private String     entities;  // 实体id
    private boolean    isRandomID; // 是否随机id
    private int        maxTrigger; // 总生成次数
    private int        maxAlive;  // 同时存活数量
    private int        minLimit;  // 单次创建数量下限
    private int        maxLimit;  // 单次创建数量上限
    private float      startTime; // 首次生成时间
    private float      interval;  // 时间间隔
    private int        areaType;  // 区域类型
    private float      radius;    // 区域半径
    private float      angle;     // 区域角度
    private float      width;     // 区域宽
    private float      height;    // 区域高

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

    public boolean getVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getIsManual() {
        return isManual;
    }
    public void setIsManual(boolean isManual) {
        this.isManual = isManual;
    }

    public String getEntities() {
        return entities;
    }
    public void setEntities(String entities) {
        this.entities = entities;
    }

    public boolean getIsRandomID() {
        return isRandomID;
    }
    public void setIsRandomID(boolean isRandomID) {
        this.isRandomID = isRandomID;
    }

    public int getMaxTrigger() {
        return maxTrigger;
    }
    public void setMaxTrigger(int maxTrigger) {
        this.maxTrigger = maxTrigger;
    }

    public int getMaxAlive() {
        return maxAlive;
    }
    public void setMaxAlive(int maxAlive) {
        this.maxAlive = maxAlive;
    }

    public int getMinLimit() {
        return minLimit;
    }
    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
    }

    public int getMaxLimit() {
        return maxLimit;
    }
    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public float getStartTime() {
        return startTime;
    }
    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getInterval() {
        return interval;
    }
    public void setInterval(float interval) {
        this.interval = interval;
    }

    public int getAreaType() {
        return areaType;
    }
    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public float getRadius() {
        return radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getAngle() {
        return angle;
    }
    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }


};
