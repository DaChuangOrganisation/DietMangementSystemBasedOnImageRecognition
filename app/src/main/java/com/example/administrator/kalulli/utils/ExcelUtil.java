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
            String pattern3 = "";
            Pattern r1 = Pattern.compile(pattern1);
            Pattern r2 = Pattern.compile(pattern2);
            Pattern r3 = Pattern.compile(pattern3);
            Matcher matcher1;
            Matcher matcher2;
            Matcher matcher3;

            InputStream is = context.getResources().getAssets().open(file);
            Workbook book = Workbook.getWorkbook(is);
            for(int i =0; i<11; i++){
                Sheet sheet = book.getSheet(0);
                String classification = sheet.getName();
                for (int j = 1; j < sheet.getRows(); ++j) { //忽略第一行
                    Recommendation recommendation = new Recommendation();
                    //简化名称
                    String foodName = sheet.getCell(0, j).getContents();//从sheet中获取数据
                    int commaIndex = foodName.indexOf("，");
                    if(commaIndex!=-1){
                        foodName = foodName.substring(0,commaIndex);
                    }
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
                    //remark（去掉评价两个字）
                    String remark = sheet.getCell(2, j).getContents();
                    if("评价：".contentEquals(remark))
                        remark = "暂无评价";
                    else
                        remark = remark.substring(remark.indexOf("：")+1);
                    //url
                    String url = sheet.getCell(3,j).getContents();

                    recommendation.setClassification(classification);//设置分类
                    recommendation.setName(foodName);//设置食物名称
                    recommendation.setCalorie(calorie);//设置卡路里值
                    recommendation.setRemark(remark);
                    recommendation.setImgUrl(url);
                    recommendation.save();
                }
            }
            book.close();
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }

    }
}
