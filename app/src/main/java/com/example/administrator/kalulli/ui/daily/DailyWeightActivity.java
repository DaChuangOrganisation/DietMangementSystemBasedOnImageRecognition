package com.example.administrator.kalulli.ui.daily;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.litepal.DailyCalorie;
import com.example.administrator.kalulli.utils.TableUtil;
import com.example.administrator.kalulli.utils.TimeUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyWeightActivity extends BaseActivity {

    private static final String TAG = "DailyWeightActivity";
    @BindView(R.id.weight_et)
    EditText weightEt;
    @BindView(R.id.send_btn)
    Button send_btn;
    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.back_daily_img)
    ImageView backDailyImg;
    private List<Entry> entries = new ArrayList<>();
    private List<String> meList = new ArrayList<>();
    private LineDataSet dataSet;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    private final SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd", Locale.US);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initChart();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChart();
    }

    /**
     * 初始化表格
     */
    private void initChart() {
        // 获取最近15天的数据
        long todayMillis = TimeUtil.todayToMillis();
        long upper = todayMillis + DateUtils.DAY_IN_MILLIS;
        long floor = todayMillis - 15 * DateUtils.DAY_IN_MILLIS;
        List<DailyCalorie> dailyCalories = LitePal
                .where("date>=? and date<?", String.valueOf(floor), String.valueOf(upper))
                .order("date")
                .find(DailyCalorie.class);

        // 测试
//        List<DailyCalorie> dailyCalories = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            DailyCalorie dailyCalorie = new DailyCalorie();
//            dailyCalorie.setDate(new Date(TimeUtil.todayToMillis() + 12 * DateUtils.HOUR_IN_MILLIS - i * DateUtils.DAY_IN_MILLIS));
//            dailyCalorie.setTotalIntake((15 - i) * 10);
//            dailyCalories.add(dailyCalorie);
//        }

        // 以日期为key, 每日卡路里总量为value, 构建哈希表，目的是消除属于同一天的多个DailyCalorie
        HashMap<String, Double> hashMap = new HashMap<>();
        dailyCalories.forEach(dailyCalorie -> hashMap.put(TimeUtil.dateToFloat(dailyCalorie.getDate()), dailyCalorie.getTotalIntake()));

        // 为图表输入数据，如果某天没有数据，则显示为0
        for (int i = 0; i < 15; i++) {
            long currentMillis = todayMillis + 12 * DateUtils.HOUR_IN_MILLIS - i * DateUtils.DAY_IN_MILLIS;
            Double a = hashMap.get(TimeUtil.dateToFloat(currentMillis));
            if (a != null) {
                entries.add(new Entry(15 - i, a.floatValue()));
            } else {
                entries.add(new Entry(15 - i, 0f));
            }
        }

        Collections.sort(entries, new EntryXComparator());

        Description description = new Description();
        description.setText("时间");
        description.setTextSize(15);
        chart.setNoDataText("当前还没有数据");
        chart.setDescription(description);
        chart.setBorderColor(Color.CYAN);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
//        chart.setTouchEnabled(true);
        // 支持缩放和拖动
//        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);//禁止缩放
//        chart.setMaxVisibleValueCount(10);
//        chart.setPinchZoom(true);

//        Matrix m = new Matrix();
//        m.postScale(1.5f, 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
//        chart.getViewPortHandler().refresh(m, chart, false);//将图表动画显示之前进行缩放
//        chart.animateX(1000);// 立即执行的动画,x轴

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // 将编号转化为对应的日期
                float v = System.currentTimeMillis() - (15 - value) * DateUtils.DAY_IN_MILLIS;
                return TimeUtil.dateToFloat(new Date((long) v));
            }
        });

        YAxis axisRight = chart.getAxisRight();
        axisRight.setEnabled(false);

        //2
        dataSet = new LineDataSet(entries, "体重曲线图");
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(1.8f);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        dataSet.setColor(Color.CYAN);
        dataSet.setFillColor(Color.CYAN);
        dataSet.setFillAlpha(10);
        dataSet.setDrawHorizontalHighlightIndicator(false);

        //3
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
//        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daily_weight;
    }

    @OnClick(R.id.send_btn)
    public void onClick() {
        final String weight = weightEt.getText().toString().trim();
        System.out.println("......................................................");
        long time = System.currentTimeMillis();
        dataSet.addEntry(new Entry(time, Float.parseFloat(weight)));
        Log.i("time", simpleDateFormat.format(new Date(System.currentTimeMillis())));
        chart.setData(new LineData(dataSet));
//        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    public void onViewClicked() {
        final String weight = weightEt.getText().toString().trim();
        final String date = TimeUtil.getDate().split("-")[2];
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_WEIGHT_TABLE_NAME);
        query.whereEqualTo(TableUtil.DAILY_WEIGHT_USER, mAVUserFinal);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException avException) {
                if (avException == null) {
                    if (avObjects == null || avObjects.size() == 0) {
                        AVObject avObject = new AVObject(TableUtil.DAILY_WEIGHT_TABLE_NAME);
                        List<String> list = new ArrayList<>();
                        list.add("00|" + mAVUserFinal.get(TableUtil.USER_WEIGHT));
                        list.add(date + "|" + weight);
                        avObject.put(TableUtil.DAILY_WEIGHT_ARRAY, list);
                        avObject.put(TableUtil.DAILY_WEIGHT_USER, mAVUserFinal);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    toast("上传成功", 0);
                                    getData();
                                } else {
                                    Log.e(TAG, "done: " + e.getMessage());
                                }
                            }
                        });
                    } else {
                        AVObject avObject = avObjects.get(0);
                        Log.i(TAG, "done: " + avObject.getObjectId());
                        List<String> list = avObject.getList(TableUtil.DAILY_WEIGHT_ARRAY);
                        list.add(date + "|" + weight);
                        avObject.put(TableUtil.DAILY_WEIGHT_ARRAY, list);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    toast("上传成功", 0);
                                    getData();
                                } else {
                                    Log.e(TAG, "done: " + e.getMessage());
                                }
                            }
                        });

                    }
                } else {
                    Log.e(TAG, "done: " + avException.getMessage());
                }
            }
        });
    }

    public void getData() {

    }

    @OnClick(R.id.back_daily_img)
    public void onViewClicked2() {
        mActivity.finish();
    }
}
