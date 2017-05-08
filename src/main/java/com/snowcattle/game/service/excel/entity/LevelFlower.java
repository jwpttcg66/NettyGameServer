// 此文件由导表工具自动生成，禁止手动修改。

package com.snowcattle.game.service.excel.entity;

public class LevelFlower {

    private int        ID;        // 编号
    private String     desc;      // 描述
    private float      mpScale;   // 体积能量比
    private float      mpThreshold; // 能量阀值
    private int        mpSkill;   // 能量触发技能
    private float      startTime; // 首次生成时间
    private String     nStartAreas; // 首次区域数
    private float      interval;  // 生成时间间隔
    private String     nAreas;    // 随机区域数
    private String     entityName; // 生成器名字
    private int        revivalID; // 复活NPC实体ID

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

    public float getMpScale() {
        return mpScale;
    }
    public void setMpScale(float _mpScale) {
        mpScale = _mpScale;
    }

    public float getMpThreshold() {
        return mpThreshold;
    }
    public void setMpThreshold(float _mpThreshold) {
        mpThreshold = _mpThreshold;
    }

    public int getMpSkill() {
        return mpSkill;
    }
    public void setMpSkill(int _mpSkill) {
        mpSkill = _mpSkill;
    }

    public float getStartTime() {
        return startTime;
    }
    public void setStartTime(float _startTime) {
        startTime = _startTime;
    }

    public String getNStartAreas() {
        return nStartAreas;
    }
    public void setNStartAreas(String _nStartAreas) {
        nStartAreas = _nStartAreas;
    }

    public float getInterval() {
        return interval;
    }
    public void setInterval(float _interval) {
        interval = _interval;
    }

    public String getNAreas() {
        return nAreas;
    }
    public void setNAreas(String _nAreas) {
        nAreas = _nAreas;
    }

    public String getEntityName() {
        return entityName;
    }
    public void setEntityName(String _entityName) {
        entityName = _entityName;
    }

    public int getRevivalID() {
        return revivalID;
    }
    public void setRevivalID(int _revivalID) {
        revivalID = _revivalID;
    }


};
