package com.example.administrator.kalulli.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

/*
单次食物记录
 */
public class FoodItem extends LitePalSupport implements DataManipulation{

    private DailyCalorie dailyCalorie;//DailyCalorie与FoodItem一对多

    private long id; //菜品单目id(自增,自赋值)
    private String foodName; //食物名称
    private double calorie; // 每100g
    private Date date; // 添加的时间
    private String imgPath;//食物图片路径
    private double protein;//蛋白质
    private double fat;//脂肪
    private double carbohydrate;//碳水化合物

    public FoodItem(){

    }

    public FoodItem(String foodName, double calorie, Date date, String imgPath) {
        this.foodName = foodName;
        this.calorie = calorie;
        this.date = date;
        this.imgPath = imgPath;
    }

    public FoodItem(DailyCalorie dailyCalorie, String foodName, double calorie, Date date, String imgPath) {
        this.dailyCalorie = dailyCalorie;
        this.foodName = foodName;
        this.calorie = calorie;
        this.date = date;
        this.imgPath = imgPath;
    }

    public FoodItem(String foodName, double calorie, Date date, String imgPath, double protein, double fat, double carbohydrate) {
        this.foodName = foodName;
        this.calorie = calorie;
        this.date = date;
        this.imgPath = imgPath;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
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

    @Override
    public boolean saveOrUpdateData() {
        return   this.save();
    }

    @Override
    public int deleteData() {
        return LitePal.delete(FoodItem.class,this.getId());
    }

}
