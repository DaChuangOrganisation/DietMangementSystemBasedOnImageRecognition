package com.example.administrator.kalulli.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum IntakeState{
    excess(1),//过量
    appropriacy(2),//适量
    smidgen(3);//少量

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
public class DailyCalorie extends LitePalSupport implements DataManipulation {

    private User user;//DailyCalorie与User一对多
    private List<FoodItem> itemList = new ArrayList<>();//DailyCalorie与FoodItem一对多

    private long id; //每日菜品清单id(自增,自赋值)
    private Date date; //今日日期
    private double totalIntake; //每日卡路里摄入总量
    private IntakeState intakeState; //每日卡路里指标状态


    public DailyCalorie() {
    }


    public DailyCalorie(Date date) {
        this.date = date;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<FoodItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<FoodItem> itemList) {
        this.itemList = itemList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean saveOrUpdateData() {
        return   this.save();
    }

    @Override
    public int deleteData() {
        return LitePal.delete(DailyCalorie.class,this.getId());
    }

}
