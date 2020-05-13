package com.example.administrator.kalulli.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.example.administrator.kalulli.SharePreUtil;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    protected FragmentManager mFragmentManager;
    protected Activity mActivity;
    protected AVUser mAVUserFinal;
    public static boolean bl=false;
    public Context mContext;
//    public SharePreUtil SpUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
//        mAVUserFinal = AVUser.getCurrentUser();
        mContext = this;

        logicActivity(savedInstanceState);
    }

    protected abstract void logicActivity(Bundle mSavedInstanceState);

    protected abstract int getLayoutId();

    public void toast(String toast,int time){
        if (time == 0){
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    public void snackBar(View v, String snackBar, int time){
        if (time == 0){
            Snackbar.make(v,snackBar,Snackbar.LENGTH_SHORT)
                    .show();
        }else {
            Snackbar.make(v,snackBar,Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}