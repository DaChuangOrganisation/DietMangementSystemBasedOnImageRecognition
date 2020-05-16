package com.example.administrator.kalulli.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class User extends LitePalSupport implements DataManipulation {

    private List<DailyCalorie> DailyCaloireList = new ArrayList<>(); //DailyCalorie与User一对多

    private long id; //用户id(自增,自赋值)
    private String name;//用户姓名
    private double weight;//体重 kg
    private String gender;//性别
    private int age;// 年龄
    private double height;// cm单位制
    private double calorie;// 该用户每日建议卡路里摄入量
    private double BMI;
    private String figure;//体质


    public User() {
    }

    public User(String name, double weight, String gender,
                int age, double height, double calorie, double BMI, String figure) {
        this.name = name;
        this.weight = weight;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.calorie = calorie;
        this.BMI = BMI;
        this.figure = figure;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public List<DailyCalorie> getDailyCaloireList() {
        return DailyCaloireList;
    }

    public void setDailyCaloireList(List<DailyCalorie> dailyCaloireList) {
        DailyCaloireList = dailyCaloireList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean saveOrUpdateData() {
        return this.save();
    }

    @Override
    public int deleteData() {
        return LitePal.delete( User.class, this.getId() );
    }

    public int suggest_BMI() {
        double BMI = 0;
        double h = height/100;//单位换算为m
        double w = weight;
        BMI = w/Math.pow( h,2 );
        if(BMI<18.5){
            return 1;//偏瘦  对应推荐 菜肴_  谷薯芋、杂豆、主食_  蛋类、肉类及制品_
        }
        else if(BMI>=18.5&&BMI<=23.9){
            return 2;//正常  对应推荐 谷薯芋、杂豆、主食_ 坚果、大豆及制品_   蔬果和菌藻_ 奶类及制品_
        }
        else if(BMI>=24&&BMI<=27.9){
            return 3;//偏胖  对应推荐 谷薯芋、杂豆、主食_  蔬果和菌藻_
        }
        else{
            return 4;//过胖  蔬果和菌藻_
        }

    }

}
