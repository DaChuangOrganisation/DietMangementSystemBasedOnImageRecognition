package com.example.administrator.kalulli;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.administrator.kalulli.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
<<<<<<< HEAD
                    Thread.sleep(1000);
                    Intent intent = new Intent(getApplication(),BasicInfoInpu.class);
=======
                    Thread.sleep(200);
                    Intent intent = new Intent(getApplication(),MainActivity.class);
>>>>>>> 93a598d581838f6de11ca0c59debe82e3759a244
                    startActivity(intent);
                    mActivity.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
