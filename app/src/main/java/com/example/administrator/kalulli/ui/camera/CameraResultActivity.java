package com.example.administrator.kalulli.ui.camera;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.data.FoodJson;
import com.example.administrator.kalulli.litepal.DailyCalorie;
import com.example.administrator.kalulli.litepal.FoodItem;
import com.example.administrator.kalulli.litepal.FoodNutrition;
import com.example.administrator.kalulli.litepal.NutritionUtil;
import com.example.administrator.kalulli.litepal.Recommendation;
import com.example.administrator.kalulli.ui.adapter.CameraResultAdapter;
import com.example.administrator.kalulli.utils.ComputerTypeUtil;
import com.example.administrator.kalulli.utils.DailyUtil;
import com.example.administrator.kalulli.utils.TableUtil;
import com.example.administrator.kalulli.utils.TimeUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraResultActivity extends BaseActivity {

    private static final String TAG = "CameraResultActivity";
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.result_recyclerView)
    RecyclerView resultRecyclerView;
    @BindView(R.id.loading_put)
    AVLoadingIndicatorView loadingPut;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private List<FoodJson> list = new ArrayList<>();
    private String description = "";

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        getData();
    }


    private void getData() {
        loadingPut.hide();
        Bundle bundle = getIntent().getExtras();
        try {
            // 断言bundle不为空，如果为bundle为空则抛出异常
            //assert bundle != null;
            Log.i(TAG, "logicActivity: " + bundle.getString("json"));
            JSONObject object = new JSONObject(bundle.getString("json"));
            Log.i(TAG, "logicActivity: " + object.toString());
            JSONArray jsonArray = object.getJSONArray("result");
            Log.i(TAG, "logicActivity: " + jsonArray.length());
            JSONObject object1 = (JSONObject) jsonArray.get(0);

            // 根据返回的结果，判断图片中是否含有卡路里。
            // 如果没有卡路里则用Toast提示用户，并返回。
//            if (object1.has("has_calorie") && !object1.getBoolean("has_calorie")) {
//                Toast.makeText(this, "该图片中没有卡路里", Toast.LENGTH_LONG).show();
//                return;
//            }

            //似乎description有问题
//            String object2 = object1.getString("baike_info");
//            Log.i(TAG, "getData: " + object2);
//            String[] strings = object2.split("\"");
//            Log.i(TAG, "logicActivity: " + strings.length);
//            if (strings.length > 5) {
//                description = strings[11];
//            }
            description="nice";
            FoodJson foodJson = new FoodJson(object1.getString("name"),
                    object1.getString("calorie"),
                    bundle.getString("str"),
                    description);
            //获取具体营养成本
            double protein=0;
            double fat=0;
            double carbohydrate=0;
            double cellulose=0;
            Log.i(TAG,"15464646");
            try{
                FoodNutrition nutrition;
                nutrition = Recommendation.getFoodNutrition(object1.getString("name"));
                if(nutrition==null){
                    Log.i(TAG,"nutrition null first");
                    nutrition = NutritionUtil.getFoodNutritionOnline(object1.getString("name"));
                }
                protein=nutrition.getProtein();
                fat=nutrition.getFat();
                carbohydrate=nutrition.getCarbohydrate();
                cellulose=nutrition.getCellulose();
            }catch (Exception e){
                Log.e(TAG,e.getMessage() + object1.getString("name"));
            }
            foodJson.push("蛋白质", protein + "g");
            foodJson.push("脂肪", fat + "g");
            foodJson.push("碳水化合物", carbohydrate + "g");
            foodJson.push("纤维素", cellulose + "g");

            list.add(foodJson);
            //Log.i(TAG, "logicActivity: "+ strings[7]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "logicActivity: " + list.size());
        initRecyclerView();
    }

    public void initRecyclerView() {
        resultRecyclerView.setLayoutManager(linearLayoutManager);
        CameraResultAdapter cameraResultAdapter = new CameraResultAdapter(this, list);
        resultRecyclerView.setAdapter(cameraResultAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera_result;
    }

    @OnClick(R.id.button)
    public void onViewClicked(){
        loadingPut.show();
        Log.i(TAG, "onViewClicked: click");
        try{
            //每天为一个Dailycalorie对象
            FoodJson tFoodJson=list.get(0);
            Date date=new Date();
            FoodItem item1=new FoodItem(tFoodJson.getFoodname(),Double.parseDouble(tFoodJson.getNumber()),date,tFoodJson.getPicture_url());
            item1.setDate(date);
            //DailyCalorie dc1 = new DailyCalorie(); 每次新建dc
            List<DailyCalorie> dailyCalories= DailyUtil.getDailyFoodList();//获取今日DailyCalorie
            DailyCalorie dc1;
            if(dailyCalories.size()>0){
                dc1 = dailyCalories.get(0);
            }
            else{
                dc1 = new DailyCalorie();
                dc1.setDate(date);
            }
            item1.saveOrUpdateData();
            dc1.getItemList().add(item1);
            item1.setDailyCalorie(dc1);
            dc1.setTotalIntake(dc1.getTotalIntake()+item1.getCalorie());
            dc1.saveOrUpdateData();
            loadingPut.hide();
            toast("提交成功", 0);
            Log.i(TAG, "onViewClicked: click "+ dc1.getDate());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

//    @OnClick(R.id.button)
//    public void onViewClicked() {
//        loadingPut.show();
//        if (mAVUserFinal != null) {
//            Log.i(TAG, "onViewClicked: click");
//            //AVObject avObject = new AVObject(TableUtil.DAILY_FOOD_TABLE_NAME);
//            Log.i(TAG, "onViewClicked: " + list.get(0).getPicture_url());
//            try {
//                final AVFile file = AVFile.withAbsoluteLocalPath(list.get(0).getFoodname(), list.get(0).getPicture_url());
//                file.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(AVException e) {
//                        if (e == null) {
//                            final AVObject avObject = new AVObject(TableUtil.DAILY_FOOD_TABLE_NAME);
//                            FoodJson foodJson = list.get(0);
//                            String foodName = foodJson.getFoodname();
//                            String imgUrl = file.getUrl();
//                            String type = ComputerTypeUtil.getType();
//                            final String calorie = foodJson.getNumber();
//                            String info = foodJson.getDescription();
//                            avObject.put(TableUtil.DAILY_FOOD_USER, mAVUserFinal);
//                            avObject.put(TableUtil.DAILY_FOOD_URL, imgUrl);
//                            if (type.equals("早餐")) {
//                                avObject.put(TableUtil.DAILY_FOOD_CALORIE, Integer.parseInt(calorie) * 2 + "");
//                            } else if (type.equals("午餐")) {
//                                avObject.put(TableUtil.DAILY_FOOD_CALORIE, Integer.parseInt(calorie) * 3.5 + "");
//                            } else {
//                                avObject.put(TableUtil.DAILY_FOOD_CALORIE, Integer.parseInt(calorie) * 3 + "");
//                            }
//                            avObject.put(TableUtil.DAILY_FOOD_DESCRIPTION, info);
//                            avObject.put(TableUtil.DAILY_FOOD_TIME, TimeUtil.getRealDate());
//                            avObject.put(TableUtil.DAILY_FOOD_NAME, foodName);
//                            avObject.put(TableUtil.DAILY_FOOD_TYPE, type);
//                            avObject.saveInBackground(new SaveCallback() {
//                                @Override
//                                public void done(AVException e) {
//                                    if (e == null) {
//
//                                        AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_TABLE_NAME);
//                                        query.whereEqualTo(TableUtil.DAILY_DATE, TimeUtil.getDate());
//                                        query.whereEqualTo(TableUtil.DAILY_USER, mAVUserFinal);
//                                        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
//                                        query.findInBackground(new FindCallback<AVObject>() {
//                                            @Override
//                                            public void done(List<AVObject> list, AVException e) {
//                                                if (e == null) {
//                                                    if (list == null || list.size() == 0) {
//                                                        AVObject avObject1 = new AVObject(TableUtil.DAILY_TABLE_NAME);
//                                                        if (ComputerTypeUtil.getType().equals("早餐")) {
//                                                            avObject1.put(TableUtil.DAILY_MORNING, Integer.parseInt(calorie) * 2 + "");
//
//                                                        } else if (ComputerTypeUtil.getType().equals("午餐")) {
//                                                            avObject1.put(TableUtil.DAILY_AFTERNOON, Integer.parseInt(calorie) * 3.5 + "");
//
//                                                        } else {
//                                                            avObject1.put(TableUtil.DAILY_EVENING, Integer.parseInt(calorie) * 3 + "");
//                                                        }
//                                                        avObject1.put(TableUtil.DAILY_USER, mAVUserFinal);
//                                                        avObject1.put(TableUtil.DAILY_DATE, TimeUtil.getDate());
//                                                        avObject1.saveInBackground(new SaveCallback() {
//                                                            @Override
//                                                            public void done(AVException e) {
//                                                                if (e == null) {
//                                                                    Log.i(TAG, "done: daily 提交成功");
//                                                                } else {
//                                                                    Log.e(TAG, "done: " + e.getMessage());
//                                                                }
//                                                            }
//                                                        });
//                                                    } else {
//                                                        AVObject avObject2 = list.get(0);
//                                                        if (ComputerTypeUtil.getType().equals("早餐")) {
//                                                            if (avObject2.get(TableUtil.DAILY_MORNING) == null
//                                                                    || avObject2.get(TableUtil.DAILY_MORNING).equals("")) {
//                                                                avObject2.put(TableUtil.DAILY_MORNING, Integer.parseInt(calorie) * 2 + "");
//                                                            } else {
//                                                                double calorie2 = Double.parseDouble(avObject2.getString(TableUtil.DAILY_MORNING));
//                                                                avObject2.put(TableUtil.DAILY_MORNING, calorie2 + Integer.parseInt(calorie) * 2 + "");
//                                                            }
//
//                                                        } else if (ComputerTypeUtil.getType().equals("午餐")) {
//                                                            if (avObject2.get(TableUtil.DAILY_AFTERNOON) == null
//                                                                    || avObject2.get(TableUtil.DAILY_AFTERNOON).equals("")) {
//                                                                avObject2.put(TableUtil.DAILY_AFTERNOON, Integer.parseInt(calorie) * 3.5 + "");
//                                                            } else {
//                                                                double calorie2 = Double.parseDouble(avObject2.getString(TableUtil.DAILY_AFTERNOON));
//                                                                avObject2.put(TableUtil.DAILY_AFTERNOON, calorie2 + Integer.parseInt(calorie) * 3.5 + "");
//                                                            }
//
//                                                        } else {
//                                                            if (avObject2.get(TableUtil.DAILY_EVENING) == null
//                                                                    || avObject2.get(TableUtil.DAILY_EVENING).equals("")) {
//                                                                avObject2.put(TableUtil.DAILY_EVENING, Integer.parseInt(calorie) * 3 + "");
//                                                            } else {
//                                                                double calorie2 = Double.parseDouble(avObject2.getString(TableUtil.DAILY_EVENING));
//                                                                avObject2.put(TableUtil.DAILY_EVENING, calorie2 + Integer.parseInt(calorie) * 3 + "");
//                                                            }
//                                                        }
//                                                        avObject2.saveInBackground(new SaveCallback() {
//                                                            @Override
//                                                            public void done(AVException e) {
//                                                                if (e == null) {
//                                                                    Log.i(TAG, "done: daily 修改成功");
//                                                                } else {
//                                                                    Log.e(TAG, "done: " + e.getMessage());
//                                                                }
//                                                            }
//                                                        });
//
//                                                    }
//                                                    loadingPut.hide();
//                                                    toast("提交成功", 0);
//                                                    mActivity.finish();
//                                                } else {
//                                                    Log.e(TAG, "done: " + e.getMessage());
//                                                }
//                                            }
//
//                                        });
//                                    } else {
//                                        Log.e(TAG, "done: " + e.getMessage());
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        } else {
//            toast("请先登录", 0);
//        }
//
//
//    }

}
