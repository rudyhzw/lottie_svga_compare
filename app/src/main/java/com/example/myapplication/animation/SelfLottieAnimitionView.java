package com.example.myapplication.animation;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.RenderMode;
import com.example.myapplication.R;
import com.example.myapplication.utils.DensityUtil;

public class SelfLottieAnimitionView extends BaseAnimationView {


    private LottieAnimationView lottie_start;
    private SelfLottieSetInfoCallback callback;
    private boolean isAccelerate = true;

    public SelfLottieAnimitionView(Context context, String sJson) {
        super(context, sJson);
    }

    public void setSelfSetInfoCallback(SelfLottieSetInfoCallback callback) {
        this.callback = callback;
    }

    @Override
    public void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_load_self, this);
        lottie_start = findViewById(R.id.lottie_start);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lottie_start.getLayoutParams();
        params.width = DensityUtil.getScreenWidth(context);
        params.height = DensityUtil.getScreenWidth(context);
        params.gravity = Gravity.CENTER_VERTICAL;
        lottie_start.setLayoutParams(params);

        startAndPlaySelfOne();

        findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAndPlaySelfOne();
                callback.oneLottieStart(lottie_start);
            }
        });

        findViewById(R.id.btn_test).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAndPlaySelfCircle();
                callback.circleLottieStart(lottie_start);
            }
        });

        Button btn_accelerate = findViewById(R.id.btn_accelerate);
        btn_accelerate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isAccelerate = !isAccelerate;
                if (isAccelerate) {
                    btn_accelerate.setText("不加速");
                } else {
                    btn_accelerate.setText("加速");
                }
                callback.changeAccelerate(lottie_start, isAccelerate);
            }
        });


    }


    //初始化动画
    @Override
    public void initSelf() {
        cleanSelf();
        lottie_start.setAnimation(getJSon());
    }

    //基础播放动画
    @Override
    public void playSelf() {
        lottie_start.playAnimation();
    }

    //单次播放动画
    @Override
    public void startAndPlaySelfOne() {
        initSelf();
        lottie_start.setRepeatCount(0);
        playSelf();
    }

    //循环播放动画
    @Override
    public void startAndPlaySelfCircle() {
        initSelf();
        lottie_start.setRepeatCount(LottieDrawable.INFINITE);
        playSelf();
    }

    //清除动画
    @Override
    public void cleanSelf() {
        lottie_start.removeAllAnimatorListeners();
        lottie_start.cancelAnimation();
        lottie_start.clearAnimation();
    }

}
