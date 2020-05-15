package com.example.administrator.kalulli.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

//用于推荐的菜品类
public class Recommendation extends LitePalSupport implements DataManipulation {
    long id;//自增id
    String classification;//食物所属类别
    String name;//食物名称
    double calorie;//所含卡路里

    public Recommendation() {
    }

    public Recommendation(String classification, String name, double calorie) {
        this.classification = classification;
        this.name = name;
        this.calorie = calorie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    @Override
    public boolean saveOrUpdateData() {
        return this.save();
    }

    @Override
    public int deleteData() {
        return LitePal.delete(Recommendation.class,this.getId());
    }

    @Override
    public String toString() {
        return String.format("name:%s calorie:%f classification:%s",name,calorie,classification);
    }
}
