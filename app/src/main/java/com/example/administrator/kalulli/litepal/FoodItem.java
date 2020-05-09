package com.example.administrator.kalulli.litepal;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

/*
单次食物记录
 */
public class FoodItem extends LitePalSupport {

    private DailyCalorie dailyCalorie;//DailyCalorie与FoodItem一对多

    private long id; //菜品单目id(自增,自赋值)
    private String foodName; //食物名称
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

    public DailyCalorie getDailyCalorie() {
        return dailyCalorie;
    }

    public void setDailyCalorie(DailyCalorie dailyCalorie) {
        this.dailyCalorie = dailyCalorie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
