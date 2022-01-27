package com.example.lottietest.animation;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.RenderMode;
import com.example.lottietest.R;
import com.example.lottietest.listener.SelfLottieSetInfoCallback;
import com.example.lottietest.util.DensityUtil;

import java.util.List;
import java.util.Map;

public class SelfLottieAnimitionTestView extends RelativeLayout {


    private LottieAnimationView lottie_start;
    private SelfLottieSetInfoCallback callback;
    private List<String> lists;
    private boolean isNext = false;
    private boolean isNextCircle = false;
    private Context context;
    private LottieComposition now_composition;

    public SelfLottieAnimitionTestView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void setMode(int position, boolean isNowCircle, boolean isNext, boolean isNextCircle) {
        this.isNext = isNext;
        this.isNextCircle = isNextCircle;

        showLocalLottieEffects(lists.get(position), position, isNowCircle);
    }


    public void setData(List<String> lists) {
        this.lists = lists;
    }

    public void setNowLottieComposition(LottieComposition now_composition) {
        this.now_composition = now_composition;
    }

    public void setSelfSetInfoCallback(SelfLottieSetInfoCallback callback) {
        this.callback = callback;
    }

    public void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_load_self_sdk, this);
        lottie_start = findViewById(R.id.lottie_start);

//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lottie_start.getLayoutParams();
//        if (DensityUtil.getScreenWidth(context) < DensityUtil.getScreenHeight(context)) {
//            params.width = DensityUtil.getScreenWidth(context);
//            params.height = DensityUtil.getScreenWidth(context);
//        } else {
//            params.width = DensityUtil.getScreenHeight(context) / 5 * 4 - DensityUtil.dip2px(context, 50);
//            params.height = DensityUtil.getScreenHeight(context) / 5 * 4 - DensityUtil.dip2px(context, 50);
//        }
//
//        params.gravity = Gravity.RIGHT;
//        lottie_start.setLayoutParams(params);
        lottie_start.setRenderMode(RenderMode.HARDWARE);//开硬件加速
    }


    //清除动画
    public void cleanSelf() {
        lottie_start.removeAllAnimatorListeners();
        lottie_start.cancelAnimation();
        lottie_start.clearAnimation();
    }


    private long start_time = 0;
    private long init_time = 0;
    private long compositon_time = 0;
    private long end_time = 0;
    private long temp_time = 0;

    /**
     * 从本地查找lottie动画
     * //     * @param interactCode 特效name
     */
    private void showLocalLottieEffects(String name, int now_position, boolean isCircle) {
        if (lists == null || lists.size() == 0) return;

        try {
            init_time = System.currentTimeMillis();
            cleanSelf();
            lottie_start.setProgress(0);
            Log.e("ld", "----lottie----setComposition--清除上一个view设置时间--" + (System.currentTimeMillis() - init_time));
            lottie_start.setComposition(now_composition);
            compositon_time = System.currentTimeMillis();
            Log.e("ld", "----lottie----setComposition--进行解析，生成layView树--" + (compositon_time - init_time));

            if (isCircle) {
                lottie_start.setRepeatCount(LottieDrawable.INFINITE);
            } else {
                lottie_start.setRepeatCount(0);
            }


            lottie_start.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    start_time = System.currentTimeMillis();
                    Log.e("ld", "----lottie----onAnimationStart----" + System.currentTimeMillis() + "-------初始化到开始做动画-------" + (start_time - init_time));
                    Log.e("ld", "----lottie----onAnimationStart----" + System.currentTimeMillis() + "-------解析完到开始做动画-------" + (start_time - compositon_time));

                    if (callback != null) {
                        callback.startAnimation(now_position);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    end_time = System.currentTimeMillis();
                    Log.e("ld", "----lottie----onAnimationEnd----" + System.currentTimeMillis() + "-------动画开始到结束的时间-------" + (end_time - start_time));


                    if (lists == null || lists.size() == 0) return;
                    int nextPosition = 0;

                    if (isNext && now_position < lists.size() - 1) {
                        nextPosition = now_position + 1;
                        showLocalLottieEffects(lists.get(nextPosition), nextPosition, false);
                    } else if (isNextCircle) {
                        if (now_position < (lists.size() - 1)) {
                            nextPosition = now_position + 1;
                        } else {
                            nextPosition = 0;
                        }
                        showLocalLottieEffects(lists.get(nextPosition), nextPosition, false);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            lottie_start.setVisibility(View.VISIBLE);
            lottie_start.playAnimation();

        } catch (Exception e) {
            Log.e("TestLottie", "启动动画 :出错");
        }
    }


    private float moveX;
    private float moveY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                moveY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                setTranslationX(getX() + (event.getX() - moveX));
                setTranslationY(getY() + (event.getY() - moveY));
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }


}
