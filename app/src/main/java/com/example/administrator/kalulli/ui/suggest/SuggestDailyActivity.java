package com.example.administrator.kalulli.ui.suggest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.utils.HealthUtil;
import com.example.administrator.kalulli.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
已将AVOS cloud相关的代码全部注释
 */
public class SuggestDailyActivity extends BaseActivity {

    private static final String TAG = "SuggestDailyActivity";
    @BindView(R.id.back_daily_img)
    ImageView backDailyImg;
    @BindView(R.id.morning_image)
    ImageView morningImage;
    @BindView(R.id.morning_name)
    TextView morningName;
    @BindView(R.id.morning_number)
    TextView morningNumber;
    @BindView(R.id.afternoon1_image)
    ImageView afternoon1Image;
    @BindView(R.id.afternoon1_name)
    TextView afternoon1Name;
    @BindView(R.id.afternoon1_number)
    TextView afternoon1Number;
    @BindView(R.id.afternoon2_image)
    ImageView afternoon2Image;
    @BindView(R.id.afternoon2_name)
    TextView afternoon2Name;
    @BindView(R.id.afternoon2_number)
    TextView afternoon2Number;
    @BindView(R.id.evening_image)
    ImageView eveningImage;
    @BindView(R.id.evening_name)
    TextView eveningName;
    @BindView(R.id.evening_number)
    TextView eveningNumber;
    @BindView(R.id.sum_tv)
    TextView sumTv;
//    private List<AVObject> alllist = new ArrayList<>();
    private boolean morningIs = false;
    private boolean afternoon1Is = false;
    private boolean afternoon2Is = false;
    private boolean eveningIs = false;
    private double sum;

    protected void logicActivity(Bundle mSavedInstanceState) {
//        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_suggest_daily;
    }

    @OnClick(R.id.back_daily_img)
    public void onViewClicked() {
        mActivity.finish();
    }

}
