package com.example.administrator.kalulli.litepal;

import java.util.Date;

/*
单次食物记录
 */
public class FoodItem {
    private String foodName;
    private double calorie; // 每100g
    private Date date; // 添加的时间
    private String imgPath;//食物图片路径

    public FoodItem(){

    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
