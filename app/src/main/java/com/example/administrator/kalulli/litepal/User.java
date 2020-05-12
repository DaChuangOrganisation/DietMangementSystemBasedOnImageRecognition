package com.example.administrator.kalulli.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

enum Gender{
    Male(1), Female(2);

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


    Gender(int index){
        this.name = index == 1 ? "男":"女";
        this.index = index;
    }


}
public class User extends LitePalSupport implements DataManipulation {

    private List<DailyCalorie> DailyCaloireList = new ArrayList<>(); //DailyCalorie与User一对多

    private long id; //用户id(自增,自赋值)
    private String name;//用户姓名
    private double weight;//体重 kg
    private Gender gender;//性别
    private int age;// 年龄
    private double height;// cm单位制
    private double calorie;// 该用户每日建议卡路里摄入量

    public User() {
    }

    public User(String name, double weight, Gender gender, int age, double height, double calorie) {
        this.name = name;
        this.weight = weight;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.calorie = calorie;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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
        return   this.save();
    }

    @Override
    public int deleteData() {
        return LitePal.delete(User.class,this.getId());
    }

}
