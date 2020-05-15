package com.example.administrator.kalulli.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.litepal.User;
import com.example.administrator.kalulli.utils.ExcelUtil;
import com.example.administrator.kalulli.utils.TestUtil;

import com.example.administrator.kalulli.R;

public class CustomToastActivity extends BaseActivity{
    private Button  btCollect;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.custom_toast);
        initView();
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    private void initView(){
        btCollect=(Button)super.findViewById(R.id.btn_confirm);
        btCollect.setOnClickListener( (View.OnClickListener) this );
    }

    private void showToast(String txt){
        Toast tast=Toast.makeText(this, txt, Toast.LENGTH_LONG);
        tast.setGravity( Gravity.CENTER, 0, 0);
        View view= LayoutInflater.from(this).inflate(R.layout.custom_toast, null);
        TextView tvMsg=(TextView)view.findViewById( R.id.tvMsg);
        tvMsg.setText(txt);
        tast.setView(view);
        tast.show();
    }
    public void onClick(View view) {
        String txtCollect=btCollect.getText().toString();
        if(txtCollect.equals("收藏")){
            btCollect.setText("取消收藏");
            showToast("收藏成功");
        }else{
            btCollect.setText("收藏");
            showToast("取消收藏");
        }
    }
}
