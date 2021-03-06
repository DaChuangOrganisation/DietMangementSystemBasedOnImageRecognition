package com.example.administrator.kalulli;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.litepal.NutritionUtil;
import com.example.administrator.kalulli.litepal.User;
import com.example.administrator.kalulli.ui.camera.StartCameraFragment;
import com.example.administrator.kalulli.ui.daily.DailyFragment;
import com.example.administrator.kalulli.ui.me.MeFragment;
import com.example.administrator.kalulli.ui.suggest.SuggestFragment;
import com.example.administrator.kalulli.utils.ActivityUtils;
import com.example.administrator.kalulli.utils.BottomNavigationViewHelper;
import com.example.administrator.kalulli.utils.TestUtil;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.content_main)
    FrameLayout contentMain;
    @BindView(R.id.bottom_menu)
    BottomNavigationView bottomMenu;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        SharePreUtil.put( mContext,"boolStr","true" );
        //findAll()返回值是一个Person类型的List集合
        List<User> user=LitePal.findAll( User.class );

        for(User user1:user)
        {
            Log.e("MainActivity","id: "+user1.getId()+"姓名： "+user1.getName()+"体重： "+user1.getWeight()+"性别： "+user1.getGender());
            Log.e("MainActivity","RandomNum: "+String.valueOf( user1.getRandom( 1,10 )));
            Log.e("MainActivity","BMI: "+String.valueOf( user1.suggest_BMI()));
            Log.e("MainActivity","Dishes: "+user1.getDishes());

        }
        if (mSavedInstanceState==null){{

            ActivityUtils.replaceFragmentToActivity(mFragmentManager,R.id.content_main,1);
        }
        }
//        if (mAVUserFinal == null){
//            toast("请先登录",0);
//        }
        int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
            Log.e("permission", "动态申请");
            //参数分别是当前活动，权限字符串数组，requestcode
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        BottomNavigationViewHelper.disableShiftMode(bottomMenu);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.find_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager,R.id.content_main,1);
                        break;
                    case R.id.near_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, R.id.content_main,2);
                        break;
                    case R.id.daily_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, R.id.content_main,3);
                        break;
                    case R.id.me_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager,R.id.content_main,4);
                        break;
                }
                return true;
            }
        });
//        TestUtil.test(this);//用于测试
//        TestUtil.test2(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent( intent );

    }

    @Override
    protected void onResume() {
        super.onResume();
        int id = getIntent().getIntExtra("id", 0);
        if(id==1){
            ActivityUtils.replaceFragmentToActivity(mFragmentManager,R.id.content_main,4);

        }
    }
}
