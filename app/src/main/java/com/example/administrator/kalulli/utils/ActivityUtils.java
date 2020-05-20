package com.example.administrator.kalulli.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.kalulli.ui.camera.CameraFragment;
import com.example.administrator.kalulli.ui.camera.StartCameraFragment;
import com.example.administrator.kalulli.ui.daily.DailyFragment;
import com.example.administrator.kalulli.ui.me.MeFragment;
import com.example.administrator.kalulli.ui.suggest.SuggestFragment;

/**
 * Created by Administrator on 2019/3/30.
 */

public class ActivityUtils {
    private static StartCameraFragment startCameraFragment;
    private static DailyFragment dailyFragment;
    private static SuggestFragment suggestFragment;
    private static MeFragment meFragment;
    private static CameraFragment cameraFragment;

    public static void addFragmentToActivity(FragmentManager mFragmentManager,
                                             Fragment mFragment,
                                             int frameId){
        FragmentTransaction mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.add(frameId,mFragment);
        mFragmentTransaction.commit();
    }

    public static void replaceFragmentToActivity(FragmentManager mFragmentManager,
                                                 int frameId,int index){

        FragmentTransaction mFragmentTransaction=mFragmentManager.beginTransaction();
        hideFragments(mFragmentTransaction);
        Fragment fragment = null;
        switch (index){
            case 1:
                if(startCameraFragment==null) {
                    startCameraFragment = new StartCameraFragment();
                    mFragmentTransaction.add(frameId,startCameraFragment);
                }
                fragment = startCameraFragment;
                break;
            case 3:
                if(dailyFragment==null){
                    dailyFragment = DailyFragment.getInstance();
                    mFragmentTransaction.add(frameId,dailyFragment);
                }
                fragment = dailyFragment;
                break;
            case 2:
                if(suggestFragment==null){
                    suggestFragment = SuggestFragment.getInstance();
                    mFragmentTransaction.add(frameId,suggestFragment);
                }
                fragment = suggestFragment;
                break;
            case 4:
                if(meFragment==null){
                    meFragment = MeFragment.getInstance();
                    mFragmentTransaction.add(frameId,meFragment);
                }
                fragment = meFragment;
                break;
            case 5:
                if(cameraFragment==null){
                    cameraFragment = CameraFragment.getInstance();
                    mFragmentTransaction.add(frameId,cameraFragment);
                }
                fragment = cameraFragment;
                break;
        }
        mFragmentTransaction.show(fragment);
        mFragmentTransaction.commit();
    }

    private static void hideFragments(FragmentTransaction ft) {
        if (meFragment != null)
            ft.hide(meFragment);
        if (suggestFragment != null)
            ft.hide(suggestFragment);
        if (dailyFragment != null)
            ft.hide(dailyFragment);
        if (startCameraFragment != null)
            ft.hide(startCameraFragment);
        if (cameraFragment != null)
            ft.hide(cameraFragment);
    }
}
