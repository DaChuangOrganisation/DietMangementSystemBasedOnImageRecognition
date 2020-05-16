package com.example.administrator.kalulli.ui.daily;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.litepal.DailyCalorie;
import com.example.administrator.kalulli.litepal.FoodItem;
import com.example.administrator.kalulli.litepal.User;
import com.example.administrator.kalulli.utils.DailyUtil;
import com.example.administrator.kalulli.utils.HealthUtil;
import com.example.administrator.kalulli.utils.TableUtil;
import com.example.administrator.kalulli.utils.TimeUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {

    private static final String TAG = "DailyFragment";

    @BindView(R.id.textView6)
    TextView dailyNeedTv;
    @BindView(R.id.daily_more_tv)
    TextView dailyMoreTv;
    @BindView(R.id.daily_date_tv)
    TextView dailyDateTv;
    @BindView(R.id.morning_tv)
    TextView morningTv;
    @BindView(R.id.afternoon_tv)
    TextView afternoonTv;
    @BindView(R.id.evening_tv)
    TextView eveningTv;
    @BindView(R.id.daily_btn)
    Button dailyBtn;
    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;
    Unbinder unbinder;
    @BindView(R.id.loading_tv)
    TextView loadingTv;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.weight_btn)
    Button weightBtn;
    private String id;
    List<DailyCalorie> dcList=new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loading.hide();
            loadingTv.setVisibility(View.GONE);
            if (id == null || id.equals("")) {
                Toast.makeText(getActivity(), "今日还未进食", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getActivity(), DailyActivity.class);
                intent.putExtra("id", id);

                startActivity(intent);
            }

        }
    };

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment getInstance() {
        return new DailyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        unbinder = ButterKnife.bind(this, view);
        loading.hide();
        dailyDateTv.setText(TimeUtil.getDate());
        getData();
        return view;
    }

    private void getData() {
        // 用户今日还需摄入的卡路里
        double dailyNeedCalorie = DailyUtil.getKC();
        double finalNeedCalorie = DailyUtil.getNeedCalorie();

        dailyNeedTv.setText(String.format("每日应当摄入 %.0f", dailyNeedCalorie));
        if (finalNeedCalorie > 0) {
            dailyMoreTv.setText(String.format("今日还需摄入 %.0f", finalNeedCalorie));
        } else {
            dailyMoreTv.setText("今日还需摄入 0");
        }
        /*
         * 显示早餐、午餐、晚餐卡路里数
         * */
        try {
            dcList= DailyUtil.getDailyFoodList();
//            for (DailyCalorie dc:
//                 dcList) {
//                dc.deleteData();
//            }
            if(dcList.size()!=0){
                double breakfastSum=0;
                double lunchSum=0;
                double dinnerSum=0;
                DailyCalorie dc=dcList.get(0);
//                for (DailyCalorie dc:
//                        dcList) {
                for (FoodItem foodItem:
                        dc.getItemList()) {
                    if(foodItem.getDate().getHours()<12){
                        breakfastSum+=foodItem.getCalorie();
                    }
                    if(foodItem.getDate().getHours()>=12 && dc.getDate().getHours()<18){
                        lunchSum+=foodItem.getCalorie();
                    }
                    if(foodItem.getDate().getHours()>=18){
                        dinnerSum+=foodItem.getCalorie();
                    }
                }
                if(breakfastSum!=0){
                    morningTv.setText(String.valueOf(breakfastSum)+"千卡");
                }
                if(lunchSum!=0){
                    afternoonTv.setText(String.valueOf(lunchSum)+"千卡");
                }
                if(dinnerSum!=0){
                    eveningTv.setText(String.valueOf(dinnerSum)+"千卡");
                }
            }
        } catch (Exception e) {
            afternoonTv.setText(String.valueOf(1));
            Log.e(TAG, "done: " + e.getMessage());
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Breakfast_btn)
    public void onViewClickedBreakfast(){
        List<FoodItem> bf=new ArrayList<>();
        if(dcList.size()!=0){
            DailyCalorie dc=dcList.get(0);
            for (FoodItem foodItem:
                 dc.getItemList()) {
                if(foodItem.getDate().getHours()<12){
                    bf.add(foodItem);
                }
            }
        }
        ArrayList<FoodItem> Breakfast=new ArrayList<FoodItem>();
        Breakfast.addAll(bf);
        Intent intent=new Intent();
        intent.putParcelableArrayListExtra("com.example.administrator.kalulli.litepal.FoodItem",Breakfast);
        intent.putExtra("type",1);
        intent.setClass(getContext(),DailyDetailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.Lunch_btn)
    public void onViewClickedLunch(){
        List<FoodItem> bf=new ArrayList<>();
        if(dcList.size()!=0){
            DailyCalorie dc=dcList.get(0);
            for (FoodItem foodItem:
                    dc.getItemList()) {
                if(foodItem.getDate().getHours()>=12 && foodItem.getDate().getHours()<18){
                    bf.add(foodItem);
                }
            }
        }
        ArrayList<FoodItem> Breakfast=new ArrayList<FoodItem>();
        Breakfast.addAll(bf);
        Intent intent=new Intent();
        intent.putParcelableArrayListExtra("com.example.administrator.kalulli.litepal.FoodItem",Breakfast);
        intent.putExtra("type",1);
        intent.setClass(getContext(),DailyDetailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.Dinner_btn)
    public void onViewClickedDinner(){
        List<FoodItem> bf=new ArrayList<>();
//        for (DailyCalorie dc:
//                dcList) {
//            if(dc.getDate().getHours()>=18){
//                if(dc.getItemList().size()!=0){
//                    bf.add(dc.getItemList().get(0));
//                }
//            }
//        }
        if(dcList.size()!=0){
            DailyCalorie dc=dcList.get(0);
            for (FoodItem foodItem:
                    dc.getItemList()) {
                if(foodItem.getDate().getHours()>=18){
                    bf.add(foodItem);
                }
            }
        }
        ArrayList<FoodItem> Breakfast=new ArrayList<FoodItem>();
        Breakfast.addAll(bf);
        Intent intent=new Intent();
        intent.putParcelableArrayListExtra("com.example.administrator.kalulli.litepal.FoodItem",Breakfast);
        intent.putExtra("type",1);
        intent.setClass(getContext(),DailyDetailActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.daily_btn)
    public void onViewClicked() {
        loading.show();

        loadingTv.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(3000);

                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @OnClick(R.id.weight_btn)
    public void onViewClicked2() {
        Intent intent = new Intent(getContext(), DailyWeightActivity.class);
        startActivity(intent);
    }
}