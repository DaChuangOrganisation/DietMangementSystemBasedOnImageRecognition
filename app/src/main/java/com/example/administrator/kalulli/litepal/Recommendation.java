package com.example.administrator.kalulli.litepal;

import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//用于推荐的菜品类
public class Recommendation extends LitePalSupport implements DataManipulation {
    long id;//自增id
    String classification;//食物所属类别
    String name;//食物名称
    double calorie;//所含卡路里
    String imgUrl;//食物图片链接
    String remark;//食物评价
    double carbohydrate;//碳水化合物
    double fat;//脂肪
    double protein;//蛋白质
    double cellulose;//纤维素

    public Recommendation() {
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCellulose() {
        return cellulose;
    }

    public void setCellulose(double cellulose) {
        this.cellulose = cellulose;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return String.format("name:%s calorie:%f classification:%s %f %f %f %f",
                name,calorie,classification,carbohydrate,fat,protein,cellulose);
    }

    public static FoodNutrition getFoodNutrition(String foodName){
        FoodNutrition nutrition = new FoodNutrition();
        List<Recommendation> recommendations = LitePal
                .where("name like ?","%"+foodName+"%")
                .find(Recommendation.class);
        if(recommendations!=null){
            Recommendation r =  recommendations.get(0);
            nutrition.setCarbohydrate(r.getCarbohydrate());
            nutrition.setFat(r.getFat());
            nutrition.setCellulose(r.cellulose);
        }
        return nutrition;
    }



}
