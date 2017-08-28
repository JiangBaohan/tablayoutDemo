package com.sn.myvryds;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sn.myvryds.adapter.MyPagerAdapter;
import com.sn.myvryds.fragment.VRPanoFrament;
import com.sn.myvryds.fragment.VrVideoFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTableLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //A.初始化控件对象
        iniView();
        //A.初始化ActionBar
        initActionBar();
        //初始化ViewPager
        initVIewPager();
    }

    private void iniView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mViewPager = (ViewPager) findViewById(R.id.vp);

        //注意:这里是TabLayout,不是tableLayout.会报强类型转换异常
        mTableLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    //A.设置左上角按钮,当点击时,弹出左边的抽屉Fragment
    private void initActionBar() {
        //获取一个ActionBar对象
        ActionBar actionBar = getSupportActionBar();
        //给左上角图标的左边加上一个返回的图标		参数:boolean,true为加上
        actionBar.setDisplayHomeAsUpEnabled(true);//在4.0及其以上系统,默认是false

        //这个类提供了一种方便的方式来绑定的功能   DrawableLayout和框架ActionBar来实现推荐的导航抽屉设计
        //参数:1.上下文   2.DrawerLayout    3,4:R.string.资源(照顾盲人,当盲人点击时,会发出声音)
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open,R.string.close);
        //将抽屉指示器的状态与连接的DrawableLayout同步其状态
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
    }

    //A.设置左上角按钮具有点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //初始化DrawerLayout,ViewPager,使DrawerLayout与ViewPager相关联
    private void initVIewPager() {

        //创建一个集合,用来装Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        //把VR全景图和VR视频的fragment对象装入集合显示
        fragments.add(new VRPanoFrament());
        fragments.add(new VrVideoFragment());

        //创建适配器对象
        MyPagerAdapter adapter = new MyPagerAdapter(getFragmentManager());
        adapter.setFragments(fragments);

        //给VIewPager设置适配器
        mViewPager.setAdapter(adapter);

        //tabLayout指示器有几个,就创建几个
        mTableLayout.addTab(mTableLayout.newTab());
        mTableLayout.addTab(mTableLayout.newTab());

        //使用DrawerLayout与VIewPager象关联
        mTableLayout.setupWithViewPager(mViewPager);

        //给TabLayout指示器设置文本,万物从0开始,0就是给第一个指示器设置的文本
        mTableLayout.getTabAt(0).setText("全景图");
        mTableLayout.getTabAt(1).setText("全景视频");

    }

}
