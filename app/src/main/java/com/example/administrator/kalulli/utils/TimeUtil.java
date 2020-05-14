package com.example.administrator.kalulli.utils;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2019/5/18.
 */

public class TimeUtil {
    public static String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis()).split(" ")[0];
    }

    public static String getNowTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis()).split(" ")[1];
    }

    public static String getRealDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis()).split(":")[0]
                + ":" + sdf.format(System.currentTimeMillis()).split(":")[1];
    }


    /**
     * 将今天的日期转换为该日期0时0分0秒与1970/1/1 00:00:00 GMT的相差的毫秒数
     * 因为日期来至于系统，一般情况下不会引发异常
     * 所以发生异常后，不上抛异常
     */
    public static long todayToMillis() {
        try {
            return dayToMillis(Calendar.getInstance().getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /**
     * 将日期转换为该日期0时0分0秒与1970/1/1 00:00:00 GMT的相差的毫秒数
     * example: 2020/5/11 -> 1586534400000
     */
    public static long dayToMillis(Date date) throws ParseException {
        String format = SimpleDateFormat.getDateInstance().format(date);
        return SimpleDateFormat.getDateInstance().parse(format).getTime();
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd", Locale.CHINA);

    public static String dateToFloat(Date date) {
        return simpleDateFormat.format(date);
    }

    public static String dateToFloat(long date) {
        return simpleDateFormat.format(new Date(date));
    }
}
