// 此文件由导表工具自动生成，禁止手动修改。

package com.wolf.shoot.service.excel.entity;

public class Control {

    private int        no;        // 编号
    private String     name;      // 名称
    private String     desc;      // 描述
    private boolean    disableMove; // 不可移动
    private boolean    disableSkill; // 不可释放技能
    private boolean    disableAI; // 停止AI
    private boolean    freezeAction; // 冻结动作
    private boolean    breakSkill; // 打断技能
    private String     shader;    // shader
    private String     action;    // 播放动作
    private String     topboard;  // 头顶标记

    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
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

    public boolean getDisableMove() {
        return disableMove;
    }
    public void setDisableMove(boolean disableMove) {
        this.disableMove = disableMove;
    }

    public boolean getDisableSkill() {
        return disableSkill;
    }
    public void setDisableSkill(boolean disableSkill) {
        this.disableSkill = disableSkill;
    }

    public boolean getDisableAI() {
        return disableAI;
    }
    public void setDisableAI(boolean disableAI) {
        this.disableAI = disableAI;
    }

    public boolean getFreezeAction() {
        return freezeAction;
    }
    public void setFreezeAction(boolean freezeAction) {
        this.freezeAction = freezeAction;
    }

    public boolean getBreakSkill() {
        return breakSkill;
    }
    public void setBreakSkill(boolean breakSkill) {
        this.breakSkill = breakSkill;
    }

    public String getShader() {
        return shader;
    }
    public void setShader(String shader) {
        this.shader = shader;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String getTopboard() {
        return topboard;
    }
    public void setTopboard(String topboard) {
        this.topboard = topboard;
    }


};
