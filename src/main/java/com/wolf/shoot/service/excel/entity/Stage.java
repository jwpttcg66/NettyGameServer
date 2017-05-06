// 此文件由导表工具自动生成，禁止手动修改。

package com.wolf.shoot.service.excel.entity;

public class Stage {

    private int        ID;        // 编号
    private int        entityID;  // 敌人ID
    private String     desc;      // 描述
    private String     position;  // 位置
    private int        camp;      // 阵营
    private String     heroList;  // 英雄列表
    private String     bornPosition; // 英雄出生点

    public int getID() {
        return ID;
    }
    public void setID(int _ID) {
        ID = _ID;
    }

    public int getEntityID() {
        return entityID;
    }
    public void setEntityID(int _entityID) {
        entityID = _entityID;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String _desc) {
        desc = _desc;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String _position) {
        position = _position;
    }

    public int getCamp() {
        return camp;
    }
    public void setCamp(int _camp) {
        camp = _camp;
    }

    public String getHeroList() {
        return heroList;
    }
    public void setHeroList(String _heroList) {
        heroList = _heroList;
    }

    public String getBornPosition() {
        return bornPosition;
    }
    public void setBornPosition(String _bornPosition) {
        bornPosition = _bornPosition;
    }


};
