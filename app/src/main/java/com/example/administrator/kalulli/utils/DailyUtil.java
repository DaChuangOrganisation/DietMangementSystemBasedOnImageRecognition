package com.example.administrator.kalulli.utils;

import android.text.format.DateUtils;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.example.administrator.kalulli.litepal.DailyCalorie;
import com.example.administrator.kalulli.litepal.FoodItem;
import com.example.administrator.kalulli.litepal.User;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2019/5/26.
 */

public class DailyUtil {
    private static final String TAG = "DailyUtil";

    public static String getPlanType() {
        AVUser avUser = AVUser.getCurrentUser();
        String healthType = HealthUtil.getHealth(avUser.get(TableUtil.USER_WEIGHT).toString(), avUser.get(TableUtil.USER_HEIGHT).toString());
        if (healthType.equals("胖")) {

            return "减肥";
        } else if (healthType.equals("瘦")) {
            return "增重";
        } else {
            return "增肌";
        }
    }

    public static List<String> getPlanContent() {
        String s = getPlanType();
        List<String> result = new ArrayList();
        switch (s) {
            case "减肥":
                result.add(" 您目前的体重过重,请注意合理控制饮食");
                result.add(" 最好的运动方法是你在规定的时间内，尽量多次的进行小规模的有氧运动而不要一次的运动过量，这样的减肥效果会更佳。虽然说睡觉能够忘记对食物的欲望，但是对于减肥真的起不到多少作用。而且睡觉的时间越长，越容易导致身体的代谢率降到最低，以至于身体消耗的能量就越少，身体胆固醇和脂肪的合成速度加快，从而导致肥胖问题");
                break;
            case "增重":
                result.add(" 您目前的体重过轻,建议保障营养摄入量");
                result.add(" 吃容易消化并且不油腻的食物，规律进餐，进食时细嚼慢咽，专心致志，保持心情愉快。平时里的食物的烹饪以柔软、温热为好，可以常喝新鲜的酸奶，吃面包。馒头等发酵面食，以及各种发酵后的豆制品。同时，再做一些比较温和轻松的运动，比如散步，慢跑，交谊舞，太极拳之类低强度的运动，可以放松心情，并改善消化吸收");
                break;
            case "增肌":
                result.add(" 您目前的体重正常.要注意饮食搭配,好好保持");
                result.add(" 最好的运动方法是你在规定的时间内，尽量多次的进行小规模的有氧运动而不要一次的运动过量，这样的减肥效果会更佳。");
                break;
            default:
        }
        return result;
    }

    public static String testType(String objectId) {
        final String[] result = {""};
        final boolean[] is = {true};
        final AVObject avObject = AVObject.createWithoutData(TableUtil.DAILY_TABLE_NAME, objectId);
        avObject.fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null) {
                    Log.i(TAG, "done: testType");
                    String morning = avObject.get(TableUtil.DAILY_MORNING).toString();
                    String afternoon = avObject.get(TableUtil.DAILY_AFTERNOON).toString();
                    String evening = avObject.get(TableUtil.DAILY_EVENING).toString();
                    if (morning == null || morning.equals("0")) {
                        result[0] = "不佳";
                        is[0] = false;
                    } else if (Double.parseDouble(morning) <= 50 || Double.parseDouble(morning) >= 350) {
                        result[0] = "不佳";
                        is[0] = false;
                    }
                    if (afternoon == null || morning.equals("0")) {
                        result[0] = "不佳";
                        is[0] = false;
                    } else if (Double.parseDouble(afternoon) <= 150 || Double.parseDouble(afternoon) >= 650) {
                        result[0] = "不佳";
                        is[0] = false;
                    }
                    if (evening == null || evening.equals("0")) {
                        result[0] = "不佳";
                        is[0] = false;
                    } else if (Double.parseDouble(evening) <= 50 || Double.parseDouble(evening) >= 450) {
                        result[0] = "不佳";
                        is[0] = false;
                    }
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
        if (is[0]) {
            return "正常";
        } else {
            return result[0];
        }

    }

    public static List<String> testContent(String objectId) {
        final String[] result = {""};
        final List<String> list = new ArrayList<>();
        final boolean[] is = {true};
        final AVObject avObject = AVObject.createWithoutData(TableUtil.DAILY_TABLE_NAME, objectId);
        avObject.fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null) {
                    Log.i(TAG, "done:  e == null");
                    String morning = avObject.get(TableUtil.DAILY_MORNING).toString();
                    String afternoon = avObject.get(TableUtil.DAILY_AFTERNOON).toString();
                    String evening = avObject.get(TableUtil.DAILY_EVENING).toString();
                    //1
                    if (morning == null || morning.equals("")) {
                        is[0] = false;
                        list.add(" 吃早餐,每天都要吃早餐,保证代谢且每天食欲更加稳定");
                    } else if (Double.parseDouble(morning) <= 50 || Double.parseDouble(morning) >= 350) {
                        is[0] = false;
                        list.add(" 三餐要均衡搭配，进食过多过少都伤胃");
                    }
                    if (afternoon == null || morning.equals("")) {
                        is[0] = false;
                        list.add(" 午餐是三餐中比较重要的一餐哦，不吃午餐会导致身体呈现下坡的状态");
                    } else if (Double.parseDouble(afternoon) <= 150 || Double.parseDouble(afternoon) >= 650) {
                        is[0] = false;
                        list.add(" 午餐要吃饱哦，但是也不能吃的太多");
                    }
                    if (evening == null || evening.equals("")) {
                        is[0] = false;
                        list.add(" 晚餐十分重要,必须吃「好」。如果进食不当,过饱、过晚,都可能对人体健康造成一定的损害");
                    } else if (Double.parseDouble(evening) <= 50 || Double.parseDouble(evening) >= 450) {
                        is[0] = false;
                        list.add(" 晚餐要吃七分饱，每餐食物不可过多或者过少，避免大胃口哦");
                    }
                    //2
                    if ((Double.parseDouble(morning) + Double.parseDouble(afternoon) + Double.parseDouble(evening)) <= 700) {
                        is[0] = false;
                        list.add(" 您今天的热量摄取未能达标哦，要多吃点哦");
                    } else if ((Double.parseDouble
                            (morning) + Double.parseDouble(afternoon) + Double.parseDouble(evening)) > 700) {
                        is[0] = false;
                        list.add(" 您今天的热量过多,要注意多运动");
                    } else {
                        if (is[0]) {
                            list.add(" 您今天的热量摄取已经达标");
                        } else {
                            list.add(" 虽然总量达标了,还是要注意各餐分配合理哦");
                        }
                    }
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
        return list;
    }

    public static List<DailyCalorie> getDailyFoodList() throws ParseException {
//        //获取今日首尾时间戳
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
//        SimpleDateFormat tft = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        long startTime = ft.parse(tft.format(date) + " 00:00:00:000").getTime();
//        long endTime = ft.parse(tft.format(date) + " 23:59:59:999").getTime();
//        Log.i(TAG, "starttime" + date);
//        Log.i(TAG, "endtime" + endTime);
//        List<DailyCalorie> dcList = LitePal.where("date>=? and date<=?", String.valueOf(startTime), String.valueOf(endTime))
//                .find(DailyCalorie.class, true);
//        Log.i(TAG, "listsize" + dcList.size());
//        return dcList;
        //修改获取Dailycalorie的方法
        long todayMillis = TimeUtil.todayToMillis();
        List<DailyCalorie> dailyCalories = LitePal
                .where("date>=? and date<?",
                        String.valueOf(todayMillis),
                        String.valueOf(todayMillis + DateUtils.DAY_IN_MILLIS))
                .find(DailyCalorie.class, true);

        return dailyCalories;
    }

    /**
     * 查询今日已经摄入的卡鲁里
     */
    public static double getTodayCalorie() {
        long todayMillis = TimeUtil.todayToMillis();
        List<DailyCalorie> dailyCalories = LitePal
                .where("date>=? and date<?",
                        String.valueOf(todayMillis),
                        String.valueOf(todayMillis + DateUtils.DAY_IN_MILLIS))
                .find(DailyCalorie.class, true);

        if (dailyCalories.size() > 0) {
            return dailyCalories.get(0).getTotalIntake();
        }

        return 0;
    }

    /**
     * 基于用户的信息，计算用户每日所需的卡路里。
     */
    public static double getKC() {
        User user = LitePal.findFirst(User.class, true);
        if (user != null) {
            return HealthUtil.getKC(user.getHeight(), user.getWeight(), user.getAge(), user.getGender());
        }
        return 0;
    }

    /**
     * 获取还需卡鲁里,如果不需获取,返回0
     *
     * @return
     */
    public static double getNeedCalorie() {
        double finalNeedCalorie = getKC() - getTodayCalorie();
        return finalNeedCalorie < 0 ? 0 : finalNeedCalorie;
    }

    /**
     * 获取最近15天的某个表的数据
     */
    public static <T> List<T> get15DayOfData(Class<T> modelClass) {
        long todayMillis = TimeUtil.todayToMillis();
        long upper = todayMillis + DateUtils.DAY_IN_MILLIS;
        long floor = todayMillis - 14 * DateUtils.DAY_IN_MILLIS;
        return getDataByTime(modelClass, floor, upper);
    }

    /**
     * 获取最近15天的某个表的数据
     */
    public static <T> List<T> get15DayOfData(Class<T> modelClass, boolean isEager) {
        long todayMillis = TimeUtil.todayToMillis();
        long upper = todayMillis + DateUtils.DAY_IN_MILLIS;
        long floor = todayMillis - 14 * DateUtils.DAY_IN_MILLIS;
        return getDataByTime(modelClass, floor, upper, isEager);
    }

    /**
     * 获取今天的某个表的数据
     */
    public static <T> List<T> getToDayOfData(Class<T> modelClass) {
        long todayMillis = TimeUtil.todayToMillis();
        return getDataByTime(modelClass, todayMillis, todayMillis + DateUtils.DAY_IN_MILLIS);
    }

    /**
     * 获取今天的某个表的数据
     */
    public static <T> List<T> getToDayOfData(Class<T> modelClass, boolean isEager) {
        long todayMillis = TimeUtil.todayToMillis();
        return getDataByTime(modelClass, todayMillis, todayMillis + DateUtils.DAY_IN_MILLIS, isEager);
    }

    /**
     * 获取某个时间段的某个表的数据
     */
    public static <T> List<T> getDataByTime(Class<T> modelClass, long floor, long upper) {
        return LitePal
                .where("date>=? and date<?", String.valueOf(floor), String.valueOf(upper))
                .order("date")
                .find(modelClass);
    }

    /**
     * 获取某个时间段的某个表的数据
     */
    public static <T> List<T> getDataByTime(Class<T> modelClass, long floor, long upper, boolean isEager) {
        return LitePal
                .where("date>=? and date<?", String.valueOf(floor), String.valueOf(upper))
                .order("date")
                .find(modelClass, isEager);
    }

    /**
     * 通过判断今天的DailyCalories中FoodItem添加的时间来判断是早餐,中餐,晚餐哪一种情况
     * 1表示早餐：6-8点
     * 2表示中餐：11-13点
     * 3表示晚餐：18-20点
     */
    public static int foodType(int n) {
        if (n >= 6 && n <= 8) {
            return 1;
        } else if (n >= 11 && n <= 13) {
            return 2;
        } else if (n >= 18 && n <= 20) {
            return 3;
        }
        return 0;
    }

    /**
     * 统计今天吃了几餐
     */
    public static int count() {
        List<DailyCalorie> dailyCalories = DailyUtil.getToDayOfData(DailyCalorie.class, true);
        if (dailyCalories.size() > 0) {
            List<FoodItem> itemList = dailyCalories.get(0).getItemList();
            HashSet<Integer> set = new HashSet<>();
            itemList.forEach(foodItem -> {
                int i = foodType(TimeUtil.hourOfDay(foodItem.getDate()));
                if (i != 0) {
                    set.add(i);
                }
            });
            return set.size();
        }
        return 0;
    }

}
