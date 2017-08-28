package com.sn.myvryds.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sn.myvryds.R;

/**
 * date:2017/3/16
 * author:易宸锋(dell) * function:因为VR视频的Fragment和VR全景图的Fragment界面显示效果是一致的,都用到RecyclerView,所以抽取出来一个基类
 */

public abstract class BaseFragemnt extends Fragment {


    private RecyclerView recyclerView;

    //使Fragment加载一个布局资源
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base,container,false);
    }

    //进行控件的初始化
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化控件
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        //从接口中拿到LayoutManager
        RecyclerView.LayoutManager loaderManager = getLayoutManager();
        //设置LayoutManager,让子类不用再设置
        recyclerView.setLayoutManager(loaderManager);
        //设置Adapter,子类不用再设置
        recyclerView.setAdapter(getAdatper());
    }

    /** 子类不用再设置适配器和LayoutManager,只需要在覆写接口里进行数据填充即可,父类已经写了
     * 定义一个接口,让子类根据需求去写Layout()Manager
     * @return
     */
    public abstract RecyclerView.LayoutManager getLayoutManager() ;

    /**
     * 让子类根据需求去写Adapter,
     * @return
     */
    public abstract RecyclerView.Adapter getAdatper();
}
