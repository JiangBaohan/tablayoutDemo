package com.sn.myvryds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sn.myvryds.R;

//显示播放详情页的类
public class VideoDeTailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_de_tail);
        init();
    }

    //初始化数据
    private void init() {
        //拿到传过来的Intent对象
        final Intent intent = getIntent();
        //从Intent对象里,根据键值,拿到我们所需要的数据
        String tilte = intent.getStringExtra("title");
        String img = intent.getStringExtra("img");
        String text = intent.getStringExtra("text");
        final String play = intent.getStringExtra("play");

        //分别把拿到的数据放入设置到对应的控件里去,设置标题
        TextView tvTitle = (TextView) findViewById(R.id.title_text);
        tvTitle.setText(tilte);

        //用Glide设置图片
        ImageView ivImg = (ImageView) findViewById(R.id.detail_img_view);
        Glide.with(this)//上下文
                .load(img)//UI地址
                .into(ivImg);//ImageView控件对象

        //设置影片介绍文本
        TextView tv_detail = (TextView) findViewById(R.id.detail_text);
        tv_detail.setText(text);

        //找到ImageButton控件对象,设置点击事件
        findViewById(R.id.play_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建Intent对象,  参数  1.上下文   2.类字节码,处理VR播放的Activity
                Intent vrIntent = new Intent(VideoDeTailActivity.this, VideoPlayerActivity.class);
                //使用Intent传输所要传的值
                vrIntent.putExtra("play",play);
                //开启界面
                startActivity(vrIntent);

            }
        });

    }
}
