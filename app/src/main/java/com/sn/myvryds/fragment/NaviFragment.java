package com.sn.myvryds.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sn.myvryds.R;

/**
 * date:2017/3/16
 * function:框架左边的Fragment,没有什么东西,可以按照自己的要求自行添加
 */
public class NaviFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //使用布局填充器,把一个XML资源转换为一个VIew对象,返回出去
        return inflater.inflate(R.layout.fragment_navi,container,false);
    }
}
