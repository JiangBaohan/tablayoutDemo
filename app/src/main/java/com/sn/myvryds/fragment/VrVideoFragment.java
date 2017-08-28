package com.sn.myvryds.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.sn.myvryds.R;
import com.sn.myvryds.bean.VideoItem;
import com.sn.myvryds.adapter.VrVideoAdapter;
import com.sn.myvryds.utli.ApiUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * date:2017/3/16
 * function:展示VR视频的Fragment
 */
public class VrVideoFragment extends BaseFragemnt{
    private RecyclerView recyclerView;

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        //VR视频使用GridView的样式,参数    1.上下文      2.决定一行几列
        return new GridLayoutManager(getActivity(),2);
    }

    //因为要访问网络成功,才能刷新RecyclerView的加载,所以我们要把BaseFragment里的onViewCreated重写了,否则执行顺序就出了问题
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //这里必须注释掉,否则会走BaseFragment的方法,导致没有效果
//        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        //从接口中拿到LayoutManager
        RecyclerView.LayoutManager loaderManager = getLayoutManager();
        //设置LayoutManager,让子类不用再设置
        recyclerView.setLayoutManager(loaderManager);

        //使用开源框架OKGO,加载网址
        OkGo.get(ApiUrls.URL_Query)
                .cacheKey(ApiUrls.URL_Query)//默认加载键
                .cacheMode(CacheMode.DEFAULT)//采用默认格式
                .execute(new StringCallback() {
                    //网络请求成功的回调
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            //创建原生的JSONObject来解析数据
                            JSONObject obj = new JSONObject(s);
                            //从Json字段里,根据关键字段拿到我们需要的内容
                            String content = obj.getString("content");
                            //然后报解析的数据先放在Bean类,再放到集合里,这里我们用Gson,fastJson会更方便
                            List<VideoItem> videoItems = JSON.parseArray(content, VideoItem.class);
                            //给RecyclerView设置适配器,把数据集合传到适配器里
                            recyclerView.setAdapter(new VrVideoAdapter(videoItems));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public RecyclerView.Adapter getAdatper() {
        return null;
    }
}
