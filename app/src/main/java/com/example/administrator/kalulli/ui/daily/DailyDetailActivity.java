package com.example.administrator.kalulli.ui.daily;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.base.OnClickListener;
import com.example.administrator.kalulli.litepal.DailyCalorie;
import com.example.administrator.kalulli.litepal.FoodItem;
import com.example.administrator.kalulli.ui.adapter.DailyDetailAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.litepal.LitePal;

import java.util.ArrayList;

import butterknife.BindView;

public class DailyDetailActivity extends BaseActivity {

    private static final String TAG = "DailyDetailActivity";
    @BindView(R.id.result_recyclerView)
    RecyclerView resultRecyclerView;
    @BindView(R.id.loading_put)
    AVLoadingIndicatorView loadingPut;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    //private List<FoodItem> list = new ArrayList<>();
    private ArrayList<FoodItem> list=new ArrayList<FoodItem>();//某顿列表
    //private DailyCalorie dc;
    private int type;//1 2 3表示早餐 中餐 晚餐

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        getData();

    }

    private void getData() {
        loadingPut.hide();
        Log.i("click","success1");
        Intent intent=getIntent();
        //ArrayList<FoodItem> bk =new ArrayList<FoodItem>();
        //早餐列表
        list=intent.getParcelableArrayListExtra("com.example.administrator.kalulli.litepal.FoodItem");
        type=intent.getExtras().getInt("type");

        initRecyclerView();
    }

    public void initRecyclerView() {
        resultRecyclerView.setLayoutManager(linearLayoutManager);
         final DailyDetailAdapter dailyDetailAdapter = new DailyDetailAdapter(this, list);
         dailyDetailAdapter.setOnClickListener(new OnClickListener() {
             @Override
             public void click(final int position, View view) {
                 Log.i("click","success2");
                 //Toast.makeText(DailyDetailActivity.this, "click " + position, Toast.LENGTH_SHORT).show();

                 AlertDialog alertDialog1 = new AlertDialog.Builder(DailyDetailActivity.this)
                         .setTitle("删除")//标题
                         .setMessage("确认删除吗")//内容
                         //.setIcon(R.mipmap.ic_launcher)//图标
                         .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 Toast.makeText(DailyDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                 dailyDetailAdapter.removeData(position);
                                 deleteData(position);
                             }
                         })
                         .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 Toast.makeText(DailyDetailActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                             }
                         })
                         .create();
                 alertDialog1.show();
             }
         });
        resultRecyclerView.setAdapter(dailyDetailAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daily_detail;
    }

    /*
    * 删除
    * */
    public void deleteData(int pos) {
        long id;
        id=list.get(pos).getId();
        FoodItem item= LitePal.find(FoodItem.class,id,true);
        double deleteCalorie=item.getCalorie();
        Log.i(TAG,"item calorie"+deleteCalorie);
        try{
            DailyCalorie dc = item.getDailyCalorie();
            double pre=dc.getTotalIntake();
            dc.setTotalIntake(pre-deleteCalorie);
            dc.saveOrUpdateData();
            item.deleteData();
        }catch (Exception e){
            Log.e(TAG,"getDailyCalorie failed!"+e.getMessage());
        }



    }

}
