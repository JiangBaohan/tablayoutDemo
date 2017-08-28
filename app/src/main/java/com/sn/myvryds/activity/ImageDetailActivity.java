package com.sn.myvryds.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.sn.myvryds.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

//根据点击事件,跳转到VR全景图的页面
public class ImageDetailActivity extends AppCompatActivity {

    private VrPanoramaView vrPano;
    private View loading;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        //初始化ActionBar
        initActionBar();
        initPanoView();
    }

    private void initActionBar() {
        //得到ActionBar对象
        ActionBar actionBar = getSupportActionBar();
        //设置左上角的箭头
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //设置点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //给左上角的箭头设置逻辑,点击时退出页面
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initPanoView() {
        //初始化控件
        vrPano = (VrPanoramaView) findViewById(R.id.vr_pano);
        loading = findViewById(R.id.pb_loading);
        //获取传过来的Intent,取出传过来的值
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String mp3 = intent.getStringExtra("mp3");

        //使用媒体控件播放音乐
        initPlayer(mp3);

        //okGo开源框架,以okhttp为底层,记得在Application进行初始化
        OkGo.get(url)//使用get的方式请求网络,url是网址
        .cacheKey(url)//设置当前请求网络,url是网址
        .execute(new BitmapCallback() {
            @Override
            public void onSuccess(Bitmap bitmap, Call call, Response response) {
                //使进度条消失
                loading.setVisibility(View.GONE);

                //创建VrPanoramaView.Options,去决定VR是普通效果,还是立体效果
                VrPanoramaView.Options options = new VrPanoramaView.Options();
                //VrPanoramaView.Options.TYPE_MONO普通效果
                options.inputType=VrPanoramaView.Options.TYPE_MONO;
                //使用VR框架加载Bitmap,参数  1.Bitmap  2.VrPanoramaView.Options对象
                vrPano.loadImageFromBitmap(bitmap,options);
            }
        });

    }

    private void initPlayer(String mp3) {
        //做非空判断
        if(mp3!= null){
            //创建多媒体对象
            player = new MediaPlayer();
            //设置播放为音频模式
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);

            try {
                //设置播放的数据源    参数 :   上下文   2.url
                player.setDataSource(this, Uri.parse(mp3));
                //准备播放
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //当失去焦点,回调
    @Override
    protected void onPause() {
        super.onPause();
        //暂停渲染和显示
        vrPano.pauseRendering();
        //判断player是否为空
        if(player != null){
            //不为null,暂停播放
            player.pause();
        }
    }

    //当获取焦点时,回调
    @Override
    protected void onResume() {
        super.onResume();
        //继续渲染显示
        vrPano.resumeRendering();
        //判断player是否为空
        if(player != null){
            //不为null,开始播放音乐视频
            player.start();
        }
    }

    //当Activity销毁时,回调
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭渲染视图
        vrPano.shutdown();
        //判断player是否为空
        if(player !=null ){
            //不为null 停止音乐
            player.stop();
            //释放资源
            player.release();
            //把对象置为null
            player = null;

        }
    }
}
