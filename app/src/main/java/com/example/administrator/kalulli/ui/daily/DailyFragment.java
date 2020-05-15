package com.example.administrator.kalulli.ui.daily;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
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
import com.example.administrator.kalulli.utils.HealthUtil;
import com.example.administrator.kalulli.utils.TableUtil;
import com.example.administrator.kalulli.utils.TimeUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.litepal.LitePal;

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

    /**
     * 查询今日已经摄入的卡鲁里
     */
    public double getTodayCalorie() {
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
    private double getKC() {
        User user = LitePal.findFirst(User.class, true);
        if (user != null) {
            return HealthUtil.getKC(user.getHeight(), user.getWeight(), user.getAge(), user.getGender());
        }
        return 0;
    }

    private void getData() {
        // 用户今日还需摄入的卡路里
        double dailyNeedCalorie = getKC();
        double finalNeedCalorie = getTodayCalorie() - dailyNeedCalorie;

        dailyNeedTv.setText(String.format("每日应当摄入 %.0f", dailyNeedCalorie));
        if (finalNeedCalorie > 0) {
            dailyMoreTv.setText(String.format("今日还需摄入 %s", finalNeedCalorie));
        } else {
            dailyMoreTv.setText("今日还需摄入 0");
        }

//        AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_TABLE_NAME);
//        query.whereEqualTo(TableUtil.DAILY_USER, AVUser.getCurrentUser());
//        query.whereEqualTo(TableUtil.DAILY_DATE, TimeUtil.getDate());
//        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
//        query.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//                if (e == null) {
//                    if (list == null || list.size() == 0) {
//                        morningTv.setText("未摄取");
//                        afternoonTv.setText("未摄取");
//                        eveningTv.setText("未摄取");
//                        dailyMoreTv.setText((int) Double.parseDouble(HealthUtil.getKC()) + "千卡");
//                    } else {
//                        AVObject avObject = list.get(0);
//                        id = avObject.getObjectId();
//                        double sum = 0.0;
//                        if (avObject.get(TableUtil.DAILY_MORNING) != null && !avObject.get(TableUtil.DAILY_MORNING).equals("")) {
//                            morningTv.setText(avObject.get(TableUtil.DAILY_MORNING).toString());
//                            sum += Double.parseDouble(avObject.get(TableUtil.DAILY_MORNING).toString());
//                        } else {
//                            sum += 0;
//                        }
//                        if (avObject.get(TableUtil.DAILY_AFTERNOON) != null && !avObject.get(TableUtil.DAILY_AFTERNOON).equals("")) {
//                            afternoonTv.setText(avObject.get(TableUtil.DAILY_AFTERNOON).toString());
//                            sum += Double.parseDouble(avObject.get(TableUtil.DAILY_AFTERNOON).toString());
//                        } else {
//                            sum += 0;
//                        }
//                        if (avObject.get(TableUtil.DAILY_EVENING) != null && !avObject.get(TableUtil.DAILY_EVENING).equals("")) {
//                            eveningTv.setText(avObject.get(TableUtil.DAILY_EVENING).toString());
//                            sum += Double.parseDouble(avObject.get(TableUtil.DAILY_EVENING).toString());
//                        } else {
//                            sum += 0;
//                        }
//                        dailyMoreTv.setText((int) Double.parseDouble(HealthUtil.getKC()) - sum + "千卡");
//                    }
//
//                } else {
//                    Log.e(TAG, "done: " + e.getMessage());
//                }
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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