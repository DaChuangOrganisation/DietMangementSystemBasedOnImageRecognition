package com.example.administrator.kalulli.litepal;

import java.util.Date;

enum IntakeState{
    excess(1),appropriacy(2),smidgen(3);

    private String name;
    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    IntakeState(int index){
        this.index = index;
        this.name = index == 1?"过量":(index == 2?"适量":"少量");
    }
}

/*
每日食物清单
 */
public class DailyCalorie {
    private Date date; //今日日期
    private double totalIntake; //每日卡路里摄入总量
    private IntakeState intakeState; //每日卡路里指标状态

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalIntake() {
        return totalIntake;
    }

    public void setTotalIntake(double totalIntake) {
        this.totalIntake = totalIntake;
    }

    public IntakeState getIntakeState() {
        return intakeState;
    }

    public void setIntakeState(IntakeState intakeState) {
        this.intakeState = intakeState;
    }
}
