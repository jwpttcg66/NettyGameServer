// 此文件由导表工具自动生成，禁止手动修改。

package com.snowcattle.game.service.excel.entity;

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
    public void setID(int _ID) {
        ID = _ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String _name) {
        name = _name;
    }

    public boolean getVisible() {
        return visible;
    }
    public void setVisible(boolean _visible) {
        visible = _visible;
    }

    public boolean getIsManual() {
        return isManual;
    }
    public void setIsManual(boolean _isManual) {
        isManual = _isManual;
    }

    public String getEntities() {
        return entities;
    }
    public void setEntities(String _entities) {
        entities = _entities;
    }

    public boolean getIsRandomID() {
        return isRandomID;
    }
    public void setIsRandomID(boolean _isRandomID) {
        isRandomID = _isRandomID;
    }

    public int getMaxTrigger() {
        return maxTrigger;
    }
    public void setMaxTrigger(int _maxTrigger) {
        maxTrigger = _maxTrigger;
    }

    public int getMaxAlive() {
        return maxAlive;
    }
    public void setMaxAlive(int _maxAlive) {
        maxAlive = _maxAlive;
    }

    public int getMinLimit() {
        return minLimit;
    }
    public void setMinLimit(int _minLimit) {
        minLimit = _minLimit;
    }

    public int getMaxLimit() {
        return maxLimit;
    }
    public void setMaxLimit(int _maxLimit) {
        maxLimit = _maxLimit;
    }

    public float getStartTime() {
        return startTime;
    }
    public void setStartTime(float _startTime) {
        startTime = _startTime;
    }

    public float getInterval() {
        return interval;
    }
    public void setInterval(float _interval) {
        interval = _interval;
    }

    public int getAreaType() {
        return areaType;
    }
    public void setAreaType(int _areaType) {
        areaType = _areaType;
    }

    public float getRadius() {
        return radius;
    }
    public void setRadius(float _radius) {
        radius = _radius;
    }

    public float getAngle() {
        return angle;
    }
    public void setAngle(float _angle) {
        angle = _angle;
    }

    public float getWidth() {
        return width;
    }
    public void setWidth(float _width) {
        width = _width;
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float _height) {
        height = _height;
    }


};
