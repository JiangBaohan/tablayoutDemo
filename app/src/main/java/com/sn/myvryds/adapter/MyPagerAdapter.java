package com.sn.myvryds.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * date:2017/3/16
 * function:ViewPager所需要使用的适配器,专门用来装Fragment的适配器
 * 提示:如果你的Fragment是原生的,那么使用FragmentPagerAdapter必须是V13包下的
 * 参考:http://blog.csdn.net/yung7086/article/details/47702397
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private List<Fragment> mFragments;

    //创建方法,接收外界传来的Fragment的集合
    public void setFragments(List<Fragment>  fragments){
        mFragments=fragments;
    }

    //返回对应位置的Fragment
    @Override
    public Fragment getItem(int position) {
        //从集合中拿对应位置的Fragment
        Fragment fragment = mFragments.get(position);
        return fragment;
    }

    //设置列数多少
    @Override
    public int getCount() {
        return mFragments.size();
    }
}
