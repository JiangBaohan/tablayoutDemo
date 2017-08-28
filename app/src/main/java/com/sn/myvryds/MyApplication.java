package com.sn.myvryds;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

/**
 * 
 * function:进行网络框架OKGO的初始化,记得在清单文件里注册Application
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //okGo全局初始化
        OkGo.init(this);
        //设置网络请求的一些参数
        OkGo.getInstance().setConnectTimeout(3000)//连接超时
        .setReadTimeOut(3000)//读取超时
        .setWriteTimeOut(3000)//写入超时
        .setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST)//设置缓存模式,从类点击进去,可以看到具体情况
        .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)//设置缓存时间
        .setRetryCount(3);//设置网络请求失败的重试次数
    }
}
