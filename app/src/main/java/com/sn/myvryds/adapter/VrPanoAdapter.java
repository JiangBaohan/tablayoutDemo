package com.sn.myvryds.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sn.myvryds.R;
import com.sn.myvryds.activity.ImageDetailActivity;
import com.sn.myvryds.bean.ImageItem;

import java.util.List;

/**
 * date:2017/3/16
 * function:全景图adapter的RecyclerView适配器,这里的BaseQuickAdapter是开源项目里的类,ImageItem是自定义的bean类
 */
public class VrPanoAdapter extends BaseQuickAdapter<ImageItem> {
    //A.决定了item的布局样式
    public VrPanoAdapter(List<ImageItem> imageItems) {
        super(R.layout.vr_pano_list_item,imageItems);
    }

    //A.把数据填充到布局上
    @Override
    protected void convert(BaseViewHolder helper, ImageItem item) {
        //A.获取ImageVIew,并显示图片到ImageVIew上
        ImageView iv_pano = helper.getView(R.id.iv_pano);
        //A.获取到上下文.
        Context mContext = helper.getConvertView().getContext();
        //A.使用开源框架Glide把url网址图片设置好,必须导入         compile 'com.github.bumptech.glide:glide:3.7.0'
        //注意加权限
        Glide.with(mContext).load(item.url).into(iv_pano);
        //A.设置标题到TextView上,参数  1.控件ID   2.文本        (到这里运行项目,最好运行在内存大的模拟器上,否则会报OOM的问题)
        helper.setText(R.id.tv_title,item.title);

        //RecyclerView没有点击事件的方法,可以给内部控件设置点击事件
        View view = helper.getView(R.id.ll_item_main);
        //把数据以标签的形式设置给控件
        view.setTag(item);
        //不以内部类的形式去写,是为了节约资源
        view.setOnClickListener(listener);

    }

    //设置了item点击的监听对象,这样只写了一个对象,节约内存
    private View.OnClickListener listener =new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //创建意图对象,   参数:    1.上下文     2.要跳转的类
            Intent intent = new Intent(view.getContext(), ImageDetailActivity.class);
            //从控件中取出数据
            ImageItem item = (ImageItem) view.getTag();

            //拿到数据里面的图片网址和音乐网址,通过Intent传给全景图的详情页
            intent.putExtra("url",item.url);
            intent.putExtra("mp3",item.map3);

            //开启意图
            view.getContext().startActivity(intent);
        }
    };


}
