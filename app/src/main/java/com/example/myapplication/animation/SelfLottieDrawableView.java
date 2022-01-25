package com.example.myapplication.animation;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.RenderMode;
import com.example.myapplication.R;
import com.example.myapplication.utils.DensityUtil;

import java.util.List;
import java.util.Map;

public class SelfLottieDrawableView extends RelativeLayout {

    private View view_start;
    private SelfLottieSetInfoCallback callback;
    private boolean isAccelerate = true;
    private List<String> lists;
    private boolean isNext = false;
    private boolean isNextCircle = false;
    private Context context;
    private int position = 0;

    private LottieDrawable lottie_start;
    private  Map<String,LottieComposition> compositionMap;

    public SelfLottieDrawableView(Context context, List<String> lists, Map<String,LottieComposition> compositionMap) {
        super(context);
        this.context = context;
        this.lists = lists;
        this.compositionMap = compositionMap;
        init();
    }


    public void setData(List<String> lists) {
        this.lists = lists;
        position = 0;
    }

    public void setSelfSetInfoCallback(SelfLottieSetInfoCallback callback) {
        this.callback = callback;
    }

    public void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_try_drawable_self, this);
        view_start = findViewById(R.id.view_start);

//        View view = new View(context);
//        view.setBackground(new LottieDrawable());

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view_start.getLayoutParams();
        params.width = DensityUtil.getScreenWidth(context);
        params.height = DensityUtil.getScreenWidth(context);
        params.gravity = Gravity.CENTER_VERTICAL;
        view_start.setLayoutParams(params);
//        lottie_start.setRenderMode(RenderMode.HARDWARE);
        lottie_start = new LottieDrawable();

        findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists == null || lists.size() == 0) return;
                isNext = false;
                isNextCircle = false;

                showLocalLottieEffects(lists.get(position), position, false);

            }
        });

        findViewById(R.id.btn_test).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists == null || lists.size() == 0) return;
                isNext = false;
                isNextCircle = false;

                showLocalLottieEffects(lists.get(0), 0, true);
            }
        });


        findViewById(R.id.btn_next).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lists == null || lists.size() == 0) return;
                isNext = true;
                isNextCircle = false;

                showLocalLottieEffects(lists.get(0), 0, false);
            }
        });


        Button btn_next_circle = findViewById(R.id.btn_next_circle);
        btn_next_circle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lists == null || lists.size() == 0) return;
                isNext = false;
                isNextCircle = true;

                showLocalLottieEffects(lists.get(0), 0, false);
            }
        });

        view_start.setBackgroundDrawable(lottie_start);
    }

    //清除动画
    public void cleanSelf() {
        lottie_start.removeAllAnimatorListeners();
        lottie_start.cancelAnimation();
//        lottie_start.clearAnimation();
    }


    private long start_time = 0;
    private long init_time = 0;
    private long compositon_time = 0;
    private long end_time = 0;
    /**
     * 从本地查找lottie动画
     * //     * @param interactCode 特效name
     */
    private void showLocalLottieEffects(String name, int now_position, boolean isCircle) {
        if (lists == null || lists.size() == 0) return;
        position = now_position;
        callback.setNowJsonName(name);
        try {

            init_time = System.currentTimeMillis();

//            LottieComposition composition = LottieComposition.Factory.fromFileSync(context, name);
            cleanSelf();
            lottie_start.setProgress(0);
            lottie_start.setComposition(compositionMap.get(name));//进行解析


            compositon_time = System.currentTimeMillis();
            Log.e("ld","----lottie----setComposition--解析的时间--"+ (compositon_time - init_time));

            if (isCircle) {
                lottie_start.setRepeatCount(LottieDrawable.INFINITE);
            } else {
                lottie_start.setRepeatCount(0);
            }
            lottie_start.playAnimation();
            view_start.setVisibility(View.VISIBLE);
            lottie_start.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                    start_time = System.currentTimeMillis();
                    Log.e("ld", "----lottie----onAnimationStart----" + System.currentTimeMillis() + "-------初始化到开始做动画-------" + (start_time - init_time));
                    Log.e("ld", "----lottie----onAnimationStart----" + System.currentTimeMillis() + "-------解析完到开始做动画-------" + (start_time - compositon_time));
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    end_time =  System.currentTimeMillis();
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
        } catch (Exception e) {
            Log.e("TestLottie", "启动动画 :出错");
        }
    }


}

