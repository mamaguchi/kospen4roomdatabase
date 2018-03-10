package com.example.intel.kospenmove02.singleton;

import android.app.Activity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.intel.kospenmove02.R;

@Deprecated
public class ViewPagerSingleton {

    private static ViewPagerSingleton mInstance;
    private static Activity mActivity;
    private static ViewPager mViewPager;
    private static View mRootView;


    //Constructor
    private ViewPagerSingleton(Activity activity) {
        mActivity = activity;
        mViewPager = (ViewPager) activity.findViewById(R.id.pager);
    }

    //Get-an-Instance-of-ViewPagerSingleton
    public static synchronized ViewPagerSingleton getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new ViewPagerSingleton(activity);
        }
        return mInstance;
    }


    public ViewPager getViewPagerInstance() {
        return mViewPager;
    }


    public void setAdapter(FragmentPagerAdapter fragmentPagerAdapter) {
        getViewPagerInstance().setAdapter(fragmentPagerAdapter);
    }


    public void setCurrentItem(int item) {
        getViewPagerInstance().setCurrentItem(item);
    }







}
