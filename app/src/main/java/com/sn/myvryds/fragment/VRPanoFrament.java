package com.sn.myvryds.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sn.myvryds.adapter.VrPanoAdapter;
import com.sn.myvryds.utli.ImageUrGetter;

/**
 * date:2017/3/16
 * function:展示VR全景图的Fragment
 */

public class VRPanoFrament extends BaseFragemnt {

    //创建自己的LayoutManager,使用默认的即可
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    //创建自己的适配器
    @Override
    public RecyclerView.Adapter getAdatper() {
        //把图片资源数据放入适配器中
        return new VrPanoAdapter(ImageUrGetter.getImageItems());
    }
}
