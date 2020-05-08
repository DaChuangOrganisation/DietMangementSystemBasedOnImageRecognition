package com.example.administrator.kalulli.litepal;

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
public class User {
    private Gender gender;
    private int age;
    private double height;// cm单位制
    private double calorie;// 该用户每日建议卡路里摄入量

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


}
