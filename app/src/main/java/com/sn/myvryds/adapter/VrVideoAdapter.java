package com.sn.myvryds.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sn.myvryds.R;
import com.sn.myvryds.activity.VideoDeTailActivity;
import com.sn.myvryds.bean.VideoItem;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * date:2017/3/17
 * function:VR视频模块RecyclerView所需适配器
 */
public class VrVideoAdapter extends BaseQuickAdapter<VideoItem>{


    //通过构造方法,加载一个默认布局及获取传过来的数据
    public VrVideoAdapter(List<VideoItem> videoItems) {
        super(R.layout.vr_video_list_item,videoItems);
    }

    //标签TextView的控件Id
    private int[] ids={R.id.tag0,R.id.tag1,R.id.tag2};

    @Override
    protected void convert(BaseViewHolder helper, VideoItem item) {
        //设置Item的标题,从bean类里拿对象的数据,直接就可以根据控件ID设置进去,这是开源框架的便利
        helper.setText(R.id.topic_init_title,item.title);
        //设置Item的时间,使用simpleDateFormat(java的知识点),对时间数据进行格式化
        helper.setText(R.id.date_text,new SimpleDateFormat("MM/DD/yyyy").format(item.date));
        //根据控件ID获取到控件对象
        ImageView topicImg = helper.getView(R.id.topic_init_img);
        //使用开源框架,根据网址加载图片
        Glide.with(helper.getConvertView().getContext())//获取上下文
            .load(item.img)//网址
            .into(topicImg);//ImageView控件对象
        //从bean类里拿到标签
        String[] tags = item.tags;
        //根据标签的数量,做对应的循环操作,把标签设置到TextView文本里
        for(int x=0; x<tags.length; x++){
            //设置文本,参数  1 数组里的ID   2 要塞入的数据
            helper.setText(ids[x],tags[x]);
        }

        //B.获取到item容器的ID值,设置点击事件
        View view = helper.getView(R.id.video_layout);
        //使用设置标签的形式,以控件为容器,把数据放入其中,进行传递
        view.setTag(item);
        //设置点击事件
        view.setOnClickListener(listener);

    }

    //B.点击事件的处理
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //从控件里取出数据,进行强类型转换
            VideoItem item = (VideoItem) view.getTag();
            //创建Intent对象, 参数  1.上下文   2.类字节,显示播放详情类
            Intent intent = new Intent(view.getContext(), VideoDeTailActivity.class);
            //需要传递的数据,把数据放入Intent,进行传输
            intent.putExtra("title",item.title);
            intent.putExtra("img",item.img);
            intent.putExtra("text",item.text);
            intent.putExtra("play",item.play);
            //获取上下文,开启Activity的跳转,参数就是intent对象
            view.getContext().startActivity(intent);
        }
    };


}
