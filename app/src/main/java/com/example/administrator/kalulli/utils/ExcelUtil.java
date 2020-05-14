package com.example.administrator.kalulli.utils;

import android.app.Activity;

import com.example.administrator.kalulli.litepal.Recommendation;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.util.regex.*;
//有关excel表格文件的工具类
public class ExcelUtil {

    public static void importSheetToDB(Activity context,String file) {
        try {
            String pattern1 = "：[0-9]{2,3}";
            String pattern2 = "[0-9]{2,3}";
            Pattern r1 = Pattern.compile(pattern1);
            Pattern r2 = Pattern.compile(pattern2);
            Matcher matcher1;
            Matcher matcher2;

            InputStream is = context.getResources().getAssets().open(file);
            Workbook book = Workbook.getWorkbook(is);
            for(int i =0; i<11; i++){
                Sheet sheet = book.getSheet(0);
                String classification = sheet.getName();
                for (int j = 0; j < sheet.getRows(); ++j) {
                    Recommendation recommendation = new Recommendation();
                    String foodName = sheet.getCell(0, j).getContents();//从sheet中获取数据
                    String calorieStr = sheet.getCell(1, j).getContents();
                    double calorie = 0;
                    //提取卡路里值
                    matcher1 = r1.matcher(calorieStr);
                    if(matcher1.find()){
                        calorieStr = matcher1.group(0);
                        matcher2 = r2.matcher(calorieStr);
                        if(matcher2.find()){
                            calorie = Double.parseDouble(matcher2.group(0));
                        }
                    }

                    recommendation.setClassification(classification);//设置分类
                    recommendation.setName(foodName);//设置食物名称
                    recommendation.setCalorie(calorie);//设置卡路里值
                    recommendation.save();
                }
            }
            book.close();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }

    }
}
