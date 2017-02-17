package com.cubix.gist;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cubix.gist.utils.GistConstants;
import com.cubix.gist.utils.GistUtils;
import com.cubix.gist.views.BaseImageView;
import com.cubix.gist.views.BaseVideoView;

public class GistSplashActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private Handler handler;
    private BaseImageView splashImg;
    private BaseVideoView splashVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gist_splash);

        GistUtils.Log(GistConstants.LOG_D, GistConstants.TAG, "GistSplashActivity");
        //GistUtils.setStatusBarTransparent(this, true);
        //GistUtils.getDebugKeyHash(this);

        splashImg = (BaseImageView) findViewById(R.id.splash_imgView);
        splashVideo = (BaseVideoView) findViewById(R.id.splash_videoView);
    }

    protected void setImageAndSplashTimeOut(int resId, int splashTimeInMilliseconds) {

        splashImg.setImageResource(resId);
        splashVideo.setVisibility(View.GONE);

        handler = new Handler();
        handler.postDelayed(splashRunnable, splashTimeInMilliseconds);
    }

    protected void setVideoSplash(int resId) {

        splashImg.setVisibility(View.GONE);

        //MediaController mediacontroller = new MediaController(this);
        //mediacontroller.setAnchorView(splashVideo);

        // Get the URL from String VideoURL
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + resId);
        //splashVideo.setMediaController(mediacontroller);
        splashVideo.setVideoURI(video);
        splashVideo.setOnCompletionListener(this);
        //splashVideo.start();
    }

    public Runnable splashRunnable = new Runnable() {
        @Override
        public void run() {

            handleStartActivity();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (handler != null && splashRunnable != null) {

            handler.removeCallbacks(splashRunnable);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (splashVideo != null) {
            //stopPosition = splashVideo.getCurrentPosition();
            splashVideo.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (splashVideo != null) {
            //splashVideo.seekTo(stopPosition);
            splashVideo.start();
        }
    }

    protected void handleStartActivity() {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        handleStartActivity();
    }
}
