package com.example.administrator.kalulli.litepal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.kalulli.litepal.FoodNutrition;
import com.example.administrator.kalulli.utils.ExcelUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NutritionUtil{
    private static final String TAG = "NutritionUtil";
    private static String result = null;//149.129.62.232
    private final static String preUrl = "http://39.97.171.144:3000/demo/";
    private static String fullUrl;
    private static Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(fullUrl).build();
            Call call = client.newCall(request);
            try {
                Response r = call.execute();
                result = r.body().string();
                r.close();
//                Message msg = new Message();
//                Bundle data = new Bundle();
//                data.putString("result",result);
//                msg.setData(data);
//                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String result = data.getString("result");
            // UI界面的更新等相关操作
        }
    };


    public static FoodNutrition getFoodNutrition(String foodName){
        fullUrl = preUrl+foodName;
        Thread th = new Thread(networkTask);
        th.start();
        try {
            Thread.currentThread().join(10000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FoodNutrition nutrition = null;
        if(result !=null){
            try {
                JSONObject jsonData = new JSONObject(result);
                nutrition = new FoodNutrition();
                nutrition.setProtein(Double.valueOf(jsonData.getString("蛋白质")));
                nutrition.setFat(Double.valueOf(jsonData.getString("脂肪")));
                nutrition.setCarbohydrate(Double.valueOf(jsonData.getString("碳水化合物")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return nutrition;
    }

    public static FoodNutrition getFoodNutritionOnline(String foodName){
        FoodNutrition nutrition = new FoodNutrition();
        fullUrl = "http://149.129.62.232:8000/";

        Thread th = new Thread(networkTask);
        th.start();
        try {
            Thread.currentThread().join(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String patternStr = "(?!=\')[0-9]+.[0-9]+(?!=\')";
        Log.d(TAG,result);
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(result);

        List<Double> values = new ArrayList<>();
        while (matcher.find()){
            double value = ExcelUtil.convertToDouble(matcher.group(0));
            values.add(new Double(value));
        }
        nutrition.setCarbohydrate(values.get(0));
        nutrition.setFat(values.get(1));
        nutrition.setProtein(values.get(2));
        nutrition.setCellulose(values.get(3));

        return nutrition;
    }

}