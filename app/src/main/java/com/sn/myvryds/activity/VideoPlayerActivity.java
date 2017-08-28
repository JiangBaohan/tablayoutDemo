package com.sn.myvryds.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;
import com.sn.myvryds.R;

import java.io.IOException;

//处理VR视频播放的类
public class VideoPlayerActivity extends AppCompatActivity {
    private VrVideoView vr_video;
    private SeekBar seek_bar;
    private TextView tv_progress;
    private VideoLoadTask mVideoLoadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        //获取传过来的Intent
        Intent intent = getIntent();
        //从Intent里拿到我们的数据
        String play = intent.getStringExtra("play");

        //A.进行控件的初始化
        vr_video = (VrVideoView) findViewById(R.id.vr_video);
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        tv_progress = (TextView) findViewById(R.id.tv_progress);

        //隐藏VR效果左下角的信息按钮显示
        vr_video.setInfoButtonEnabled(false);
        //切换VR的模式   参数:VrVideoView.DisplayMode.FULLSCREEN_STEREO:设备模式(手机横着放试试)      VrVideoView.DisplayMode..FULLSCREEN_MONO手机模式
        vr_video.setDisplayMode(VrVideoView.DisplayMode.FULLSCREEN_STEREO);

        //D.对VR视频进行事件监听
        vr_video.setEventListener(new MyEventListener() );

        //B.播放VR效果,只需执行异步任务即可
        mVideoLoadTask = new VideoLoadTask();
        mVideoLoadTask.execute(play);
    }


    //B.由于VR资源数据量大,获取需要时间,故把加载视频放到子线程中进行,主线程来显示,可以使用一个异步线程AsyncTask或EventBus技术完成.

    //B.自定义一个类继承AsyncTask,只使用我们需要的方法.完成在子线程加载图片资源,在主线程显示
    private class VideoLoadTask extends AsyncTask<String, Void, Void> {
        //B.该方法在子线程运行,从本地文件中把资源加载到内存中
        @Override
        protected Void doInBackground(String... strings) {
            //创建VrVideoView.Options对象,决定VR是普通的效果,还是立体效果
            VrVideoView.Options options = new VrVideoView.Options();
            //立体模式
            options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
            //处理加载的视频格式
            //FORMAT_DEFAULT:默认格式(SD卡或assets)
            //FORMAT_HLS:流媒体数据格式(直播)
            options.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;
            try {
                //提示:视频加载的方法还做了把视频读取到内存中的操作,所以它有一个矛盾,调用该方法是放在主线程还是子线程(一般我们放在子线程)
                //使用VR控件对象,从资产目录加载视频数据,显示效果 参数: 1.String对象 2.VrVideoView.Options对象,决定显示效果
//                vr_video.loadVideoFromAsset(strings[0], options);//不要管他在爆红,就在子线程里执行
                //使用VR控件对象,从网络加载视频数据,显示效果(要加网络权限)   参数:   1.视频网址,String对象
              vr_video.loadVideo(Uri.parse(strings[0]), options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    //C.因为VR很占用内存,所以当界面进入OnPause状态,暂停VR视图显示,进入OnResume状态,继续VR视图显示,进入OnDestroy状态,杀死VR,关闭异步任务

    //当失去焦点时,回到
    @Override
    protected void onPause() {
        super.onPause();
        //暂停渲染和显示
        vr_video.pauseRendering();
    }

    //当获取焦点时,回调
    @Override
    protected void onResume() {
        super.onResume();
        //继续渲染和显示
        vr_video.resumeRendering();
    }

    //当Activity销毁时,回调
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭渲染视图,回调
        vr_video.shutdown();
        //在退出Activity时,如果异步任务没有取消,则取消
        if (mVideoLoadTask != null) {
            if (!mVideoLoadTask.isCancelled()) {
                mVideoLoadTask.cancel(true);
            }
        }
    }

    //VR运行状态监听类,自定义一个类继承VrVideoEventListener,复写里面需要的方法.
    private class MyEventListener extends VrVideoEventListener {
        //当VR视图加载成功的时候的回调,此时还未开始播放
        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();
            //获取视频的长度
            long max = vr_video.getDuration();
            //设置seekbar的进度最大值
            seek_bar.setMax((int)max);

        }

        //当VR视图加载失败的时候回调的方法
        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);
            Toast.makeText(VideoPlayerActivity.this, "播放失败", Toast.LENGTH_SHORT).show();
        }

        //当视频开始播放,每次进入下一帧的时候,回调这个方法(就是播放时,会不停的回调该方法)
        @Override
        public void onNewFrame() {
            super.onNewFrame();
            //获取当前视频的播放时间位置
            int currentPosition = (int) vr_video.getCurrentPosition();
            //设置seekBar的进度条
            seek_bar.setProgress(currentPosition);
            //显示播放的进度数字
            tv_progress.setText("播放进度:"+ String.format("%.2f",currentPosition/1000.f));

        }

        //当视频播放结束后的回调
        @Override
        public void onCompletion() {
            super.onCompletion();
            //让视频回到0点
            vr_video.seekTo(0);
            //视频停止
            vr_video.pauseVideo();
            //让进度条也设置到0点
            seek_bar.setProgress(0);

            //播放完成后,重新设置标签,标签true代表着视频处于暂停的状态.
            isPaused = false ;
        }

        //设置一个视频播放状态的标签
        private boolean isPaused  = true;

        //重写点击视图的方法,是视频被点击时,播放或者暂停
        @Override
        public void onClick() {
            super.onClick();
            //根据标签,判断当前视频的状态,做对应的逻辑处理
            //false是不是代表视频正处于暂停状态,
            if(isPaused){
                //视频暂停
                vr_video.pauseVideo();
            }
            //true是不是代表视频正在播放的状态.
            else{
                //视频播放
                vr_video.playVideo();
            }
            //对标签进行一次操作后,取反
            isPaused =!isPaused;
        }

    }

}
