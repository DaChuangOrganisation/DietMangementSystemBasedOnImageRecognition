package com.example.administrator.kalulli.litepal;

import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public int getRandom(int min, int max){
        int num;
        num = (int) Math.floor( Math.random()*(max-min)+min );
        return num;
    }
    public  int getDishes(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        Log.e("MainActivity","NowSystemTime: "+df.format(new Date()));
        String breakfast = "7:00:00";
        String lunchtime = "11:00:00";
        String dinner = "17:00:00";
        String nowtime = df.format( new Date(  ) );
        if(timeCompare(nowtime,breakfast)<0){
            return 3;
        }
        if(timeCompare( nowtime,lunchtime )<0){
            return 2;
        }
        if(timeCompare( nowtime,dinner )<0){
            return 1;
        }
        return 0;
    }

        public static int timeCompare(String t1,String t2){
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Calendar c1=Calendar.getInstance();
            Calendar c2= Calendar.getInstance();
            try {
                c1.setTime(formatter.parse(t1));
                c2.setTime(formatter.parse(t2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int result=c1.compareTo(c2);
            return result;
        }



}
