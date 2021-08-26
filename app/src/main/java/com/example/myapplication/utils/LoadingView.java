package com.example.myapplication.utils;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.myapplication.R;
import com.example.myapplication.animation.SelfLottieSetInfoCallback;

/**
 * @author r0006530
 * @desc
 * @date 2020/4/7
 */
public class LoadingView extends RelativeLayout {
  private Context context;
  private LottieAnimationView lottie_start;
  private LottieAnimationView lottie_loop;
  private LottieAnimationView lottie_end;
  private SelfLottieSetInfoCallback callback;
  public LoadingView(Context context) {
    super(context);
    this.context = context;
    init();
  }

  public LoadingView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    init();
  }

  public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    init();
  }

  public void setSelfSetInfoCallback(SelfLottieSetInfoCallback callback){
    this.callback = callback;
  }

  private void init(){
    LayoutInflater.from(context).inflate(R.layout.layout_load, this);
    lottie_start = findViewById(R.id.lottie_start);
    lottie_loop = findViewById(R.id.lottie_loop);
    lottie_end = findViewById(R.id.lottie_end);

    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lottie_start.getLayoutParams();
    params.width = DensityUtil.getScreenWidth(context);
    params.height = DensityUtil.getScreenWidth(context);
    params.gravity= Gravity.CENTER_VERTICAL;
    lottie_start.setLayoutParams(params);

    FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) lottie_loop.getLayoutParams();
    params1.width = DensityUtil.getScreenWidth(context);
    params1.height = DensityUtil.getScreenWidth(context);
    params1.gravity= Gravity.CENTER_VERTICAL;
    lottie_loop.setLayoutParams(params1);

    FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) lottie_end.getLayoutParams();
    params2.width = DensityUtil.getScreenWidth(context);
    params2.height = DensityUtil.getScreenWidth(context);
    params2.gravity= Gravity.CENTER_VERTICAL;
    lottie_end.setLayoutParams(params2);


    initStartLottie();
    initLoopLottie();
    initEndLottie();
    startLottie();

    findViewById(R.id.btn_test).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        endLottie();
      }
    });

    findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startLottie();
      }
    });
  }

  private void initStartLottie() {
    lottie_start.setImageAssetsFolder("enter_lottie_loader");
    lottie_start.setAnimation("speaking_point.json");
//    lottie_start.useHardwareAcceleration(true);
    Util.test(lottie_start);
    lottie_start.addAnimatorListener(new AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        lottie_loop.setVisibility(VISIBLE);
        lottie_loop.playAnimation();
        lottie_start.setVisibility(GONE);
      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
  }
  private void initLoopLottie() {
    lottie_loop.setVisibility(GONE);
    lottie_loop.setImageAssetsFolder("enter_lottie_loader");
    lottie_loop.setAnimation("compliment_state.json");
    Util.test(lottie_loop);
//    lottie_loop.useHardwareAcceleration(true);
    lottie_loop.setRepeatCount(LottieDrawable.INFINITE);
    lottie_loop.addAnimatorListener(new AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        lottie_end.playAnimation();
      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
  }
  private void initEndLottie() {
    lottie_end.setVisibility(GONE);
    lottie_end.setImageAssetsFolder("enter_lottie_loader");
    lottie_end.setAnimation("speaking_ask.json");
    Util.test(lottie_end);
//    lottie_end.useHardwareAcceleration(true);
    lottie_end.addAnimatorListener(new AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        lottie_end.setVisibility(GONE);
      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
  }
  public void startLottie(){
    lottie_start.setVisibility(VISIBLE);
    lottie_start.playAnimation();
  }

  public void endLottie(){
    lottie_start.cancelAnimation();
    lottie_loop.cancelAnimation();
    lottie_start.setVisibility(GONE);
    lottie_loop.setVisibility(GONE);
    lottie_end.setVisibility(VISIBLE);
    lottie_end.playAnimation();
  }
}
