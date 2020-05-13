package com.example.administrator.kalulli;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.administrator.kalulli.base.BaseActivity;

import org.apache.log4j.chainsaw.Main;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    String alreInput = "false";
    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        mActivity=this;
        if(SharePreUtil.get( mContext, "boolStr","")!=null&&SharePreUtil.get( mContext, "boolStr","").toString().equals( "true" )) {
            alreInput = SharePreUtil.get( mContext, "boolStr", "" ).toString();
            Toast.makeText( SplashActivity.this, "Spalshactivity:boolStr= " + alreInput, Toast.LENGTH_LONG ).show();
        }
        else{
            Toast.makeText( SplashActivity.this, "Spalshactivity:boolStr= " + alreInput, Toast.LENGTH_LONG ).show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(alreInput.equals( "true" )) {
                    try {
                        Thread.sleep( 500 );
                        Intent intent = new Intent( getApplication(), MainActivity.class );
                        startActivity( intent );
                        mActivity.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        Thread.sleep( 500 );
                        Intent intent = new Intent( getApplication(), BasicInfoInpu.class );
                        startActivity( intent );
                        mActivity.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        SharePreUtil.put( mContext,"boolStr","false");
    }
}
