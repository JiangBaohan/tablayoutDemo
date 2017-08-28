package com.sn.myvryds.bean;

/**
 * date:2017/3/16
 * function:这就是一个bean类
 */
public class ImageItem {
    //标题
    public String title;
    //图片的url
    public String url;
    //音乐,也是一个网址url
    public String map3;

    public ImageItem(String title, String url, String map3) {
        this.title = title;
        this.url = url;
        this.map3 = map3;
    }

}
