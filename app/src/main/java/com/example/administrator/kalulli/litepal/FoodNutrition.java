package com.example.administrator.kalulli.litepal;

public class FoodNutrition{
    //每100g的含量
    private double protein;//蛋白质,单位：g
    private double fat;//脂肪,单位：g
    private double carbohydrate;//碳水化合物,单位：g
    private double cellulose;//纤维素

    public FoodNutrition() {

    }

    @Override
    public String toString(){
        return String.format("蛋白质：%f　脂肪：%f　碳水化合物：%f 纤维素：%f",protein,fat,carbohydrate,cellulose);
    }

    public double getCellulose() {
        return cellulose;
    }

    public void setCellulose(double cellulose) {
        this.cellulose = cellulose;
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
}
