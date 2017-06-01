// 此文件由导表工具自动生成，禁止手动修改。

package com.snowcattle.game.service.dict.entity;

import com.snowcattle.game.service.dict.IDict;

public class Bullet implements IDict {

    private int        ID;        // 编号
    private String     name;      // 名称
    private String     desc;      // 描述
    private String     className; // 游戏类
    private int        model;     // 模型id
    private float      attackRate; // 伤害系数
    private float      imbibeRate; // 吸血系数
    private float      moveSpeed; // 移动速度
    private float      life;      // 存活时间
    private int        lvl;       // 等级
    private boolean    penetrate; // 是否贯穿
    private boolean    track;     // 是否追随
    private float      trackAngleSpeed; // 追随角速度
    private String     hitEffect; // 命中特效
    private String     deadEffect; // 消散特效
    private String     shakeParam; // 震屏参数
    private boolean    inheritSpeed; // 是否继承速度
    private float      accelerate; // 加速度
    private float      accStartTime; // 加速度开始时间
    private float      accDuration; // 加速度持续时间
    private float      tSpeed;    // 切线移动速度
    private float      tAccelerate; // 切线加速度
    private float      tAccStartTime; // 切线加速度开始时间
    private float      tAccDuration; // 切线加速度持续时间
    private float      angleSpeed; // 角速度
    private int        exploseSkill; // 爆炸触发技能

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

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public int getModel() {
        return model;
    }
    public void setModel(int model) {
        this.model = model;
    }

    public float getAttackRate() {
        return attackRate;
    }
    public void setAttackRate(float attackRate) {
        this.attackRate = attackRate;
    }

    public float getImbibeRate() {
        return imbibeRate;
    }
    public void setImbibeRate(float imbibeRate) {
        this.imbibeRate = imbibeRate;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }
    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getLife() {
        return life;
    }
    public void setLife(float life) {
        this.life = life;
    }

    public int getLvl() {
        return lvl;
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public boolean getPenetrate() {
        return penetrate;
    }
    public void setPenetrate(boolean penetrate) {
        this.penetrate = penetrate;
    }

    public boolean getTrack() {
        return track;
    }
    public void setTrack(boolean track) {
        this.track = track;
    }

    public float getTrackAngleSpeed() {
        return trackAngleSpeed;
    }
    public void setTrackAngleSpeed(float trackAngleSpeed) {
        this.trackAngleSpeed = trackAngleSpeed;
    }

    public String getHitEffect() {
        return hitEffect;
    }
    public void setHitEffect(String hitEffect) {
        this.hitEffect = hitEffect;
    }

    public String getDeadEffect() {
        return deadEffect;
    }
    public void setDeadEffect(String deadEffect) {
        this.deadEffect = deadEffect;
    }

    public String getShakeParam() {
        return shakeParam;
    }
    public void setShakeParam(String shakeParam) {
        this.shakeParam = shakeParam;
    }

    public boolean getInheritSpeed() {
        return inheritSpeed;
    }
    public void setInheritSpeed(boolean inheritSpeed) {
        this.inheritSpeed = inheritSpeed;
    }

    public float getAccelerate() {
        return accelerate;
    }
    public void setAccelerate(float accelerate) {
        this.accelerate = accelerate;
    }

    public float getAccStartTime() {
        return accStartTime;
    }
    public void setAccStartTime(float accStartTime) {
        this.accStartTime = accStartTime;
    }

    public float getAccDuration() {
        return accDuration;
    }
    public void setAccDuration(float accDuration) {
        this.accDuration = accDuration;
    }

    public float getTSpeed() {
        return tSpeed;
    }
    public void setTSpeed(float tSpeed) {
        this.tSpeed = tSpeed;
    }

    public float getTAccelerate() {
        return tAccelerate;
    }
    public void setTAccelerate(float tAccelerate) {
        this.tAccelerate = tAccelerate;
    }

    public float getTAccStartTime() {
        return tAccStartTime;
    }
    public void setTAccStartTime(float tAccStartTime) {
        this.tAccStartTime = tAccStartTime;
    }

    public float getTAccDuration() {
        return tAccDuration;
    }
    public void setTAccDuration(float tAccDuration) {
        this.tAccDuration = tAccDuration;
    }

    public float getAngleSpeed() {
        return angleSpeed;
    }
    public void setAngleSpeed(float angleSpeed) {
        this.angleSpeed = angleSpeed;
    }

    public int getExploseSkill() {
        return exploseSkill;
    }
    public void setExploseSkill(int exploseSkill) {
        this.exploseSkill = exploseSkill;
    }


};
