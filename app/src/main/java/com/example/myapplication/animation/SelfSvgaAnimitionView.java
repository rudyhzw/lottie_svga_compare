package com.example.myapplication.animation;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;

import com.example.myapplication.R;
import com.example.myapplication.utils.DensityUtil;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

public class SelfSvgaAnimitionView extends BaseAnimationView {


    private SVGAImageView svga_start;
    private SelfSvgaSetInfoCallback callback;

    public SelfSvgaAnimitionView(Context context, String sJson) {
        super(context, sJson);
    }


    public void setSelfSetInfoCallback(SelfSvgaSetInfoCallback callback) {
        this.callback = callback;
    }

    @Override
    public void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_load_svga_self, this);
        svga_start = findViewById(R.id.svga_start);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) svga_start.getLayoutParams();
        params.width = DensityUtil.getScreenWidth(context);
        params.height = DensityUtil.getScreenWidth(context);
        params.gravity = Gravity.CENTER_VERTICAL;
        svga_start.setLayoutParams(params);


        startAndPlaySelfOne();

        findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAndPlaySelfOne();
                callback.oneSvgaStart(svga_start);
            }
        });

        findViewById(R.id.btn_test).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAndPlaySelfCircle();
                callback.circleSvgaStart(svga_start);
            }
        });


    }


    //初始化动画
    @Override
    public void initSelf() {
        svga_start.clearAnimation();

        new SVGAParser(context).decodeFromAssets(getJSon(), new SVGAParser.ParseCompletion() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                if (svga_start != null) {
                    svga_start.setVideoItem(videoItem);
                    svga_start.stepToFrame(0, true);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    //基础播放动画
    @Override
    public void playSelf() {
        svga_start.startAnimation();
    }

    //单次播放动画
    @Override
    public void startAndPlaySelfOne() {
        initSelf();
        svga_start.setLoops(1);
        playSelf();
    }

    //循环播放动画
    @Override
    public void startAndPlaySelfCircle() {
        initSelf();
        svga_start.setLoops(100);
        playSelf();
    }

    //清除动画
    @Override
    public void cleanSelf() {
        svga_start.clearAnimation();
    }

}
