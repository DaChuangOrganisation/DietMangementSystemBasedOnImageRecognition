package com.example.administrator.kalulli.utils;

import android.app.Activity;
import android.util.Log;

import com.example.administrator.kalulli.litepal.FoodNutrition;
import com.example.administrator.kalulli.litepal.NutritionUtil;

import org.litepal.LitePal;

import java.util.List;

public class TestUtil {
    private static final String TAG = "TestUtil";
    public static void test(Activity context){
        long startTime = System.currentTimeMillis();

        ExcelUtil.importSheetToDB(context,"FoodCalorieData.xls");


        long endTime = System.currentTimeMillis();
        Log.e(TAG,String.format("加载数据运行时间:%f s",(endTime-startTime)/1000.0));
    }
    public static void test2(Activity context){
        FoodNutrition nutrition = NutritionUtil.getFoodNutritionOnline("鸡蛋");
        Log.d(TAG,NutritionUtil.getFoodNutritionOnline("鸡蛋").toString());
    }
}
