// 此文件由导表工具自动生成，禁止手动修改。

package com.snowcattle.game.wolfshoot.service.dict.entity;

import com.snowcattle.game.service.dict.IDict;

public class DictExample implements IDict {

    private int        ID;        // 编号
    private String     describe;  // 描述
    private String     drops;     // 掉落关卡
    private String     name;      // 名称
    private int        quality;   // 品质

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDrops() {
        return drops;
    }
    public void setDrops(String drops) {
        this.drops = drops;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getQuality() {
        return quality;
    }
    public void setQuality(int quality) {
        this.quality = quality;
    }


};
