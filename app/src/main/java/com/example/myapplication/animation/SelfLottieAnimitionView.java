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

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.airbnb.lottie.RenderMode;
import com.example.myapplication.R;
import com.example.myapplication.utils.DensityUtil;

import java.util.List;
import java.util.Map;

public class SelfLottieAnimitionView extends RelativeLayout {


    private LottieAnimationView lottie_start;
    private SelfLottieSetInfoCallback callback;
    private boolean isAccelerate = true;
    private List<String> lists;
    private boolean isNext = false;
    private boolean isNextCircle = false;
    private Context context;
    private int position = 0;
    private  Map<String,LottieComposition> compositionMap;
    private LottieComposition now_composition;

    private long start_time = 0;
    private long init_time = 0;
    private long compositon_time = 0;
    private long end_time = 0;
    private long temp_time = 0;

    public SelfLottieAnimitionView(Context context, List<String> lists, Map<String,LottieComposition> compositionMap,LottieComposition now_composition) {
        super(context);
        this.context = context;
        this.lists = lists;
        this.compositionMap = compositionMap;
        this.now_composition = now_composition;
        init();
    }

    public void setData(List<String> lists) {
        this.lists = lists;
        position = 0;
    }

    public void setNowLottieComposition(LottieComposition now_composition){
        this.now_composition = now_composition;
    }

    public void setSelfSetInfoCallback(SelfLottieSetInfoCallback callback) {
        this.callback = callback;
    }

    public void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_load_self, this);
        lottie_start = findViewById(R.id.lottie_start);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lottie_start.getLayoutParams();
        if (DensityUtil.getScreenWidth(context) < DensityUtil.getScreenHeight(context)) {
            params.width = DensityUtil.getScreenWidth(context);
            params.height = DensityUtil.getScreenWidth(context);
        } else {
            params.width = DensityUtil.getScreenHeight(context) / 5 * 4 - DensityUtil.dip2px(context, 50);
            params.height = DensityUtil.getScreenHeight(context) / 5 * 4 - DensityUtil.dip2px(context, 50);
        }


        params.gravity = Gravity.RIGHT;

        lottie_start.setLayoutParams(params);
        lottie_start.setRenderMode(RenderMode.HARDWARE);//开硬件加速

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

    }


    //清除动画
    public void cleanSelf() {
        lottie_start.removeAllAnimatorListeners();
        lottie_start.cancelAnimation();
        lottie_start.clearAnimation();
    }



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
//            Log.e("ld","----lottie----setComposition--真正解析的时间--"+ (System.currentTimeMillis() - init_time));

            cleanSelf();
            lottie_start.setProgress(0);
//            Log.e("lottieTest","--lottie--" + name + "--清除上一个view设置耗时："+ (System.currentTimeMillis() - init_time));

//            temp_time = System.currentTimeMillis();
////            lottie_start.setComposition(compositionMap.get(name));//进行解析
//            Log.e("ld","----lottie----setComposition--开始draw的时间--"+ (System.currentTimeMillis() - temp_time));
//            compositon_time = System.currentTimeMillis();
//            Log.e("ld","----lottie----setComposition--解析总的时间--"+ (compositon_time - init_time));
//            lottie_start.setComposition(compositionMap.get(name));//进行解析，生成layView树

            lottie_start.setComposition(now_composition);
            compositon_time = System.currentTimeMillis();
            Log.e("lottieTest", "--lottie--" + name + "--生成layView树耗时：" + (compositon_time - init_time));

            if (isCircle) {
                lottie_start.setRepeatCount(LottieDrawable.INFINITE);
            } else {
                lottie_start.setRepeatCount(0);
            }


            lottie_start.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    start_time = System.currentTimeMillis();
//                    Log.e("lottieTest", "--lottie--" + name + "--初始化到开始做动画耗时：" + (start_time - init_time));
//                    Log.e("lottieTest", "--lottie--" + name + "--解析完到开始做动画耗时：" + (start_time - compositon_time));

                    if (callback != null) {
                        callback.startAnimation(now_position);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    end_time =  System.currentTimeMillis();
                    Log.e("lottieTest", "--lottie--" + name + "--动画开始到动画结束耗时：" + (end_time - start_time));

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

//            LottieComposition.Factory.fromAssetFileName(context, name, new OnCompositionLoadedListener() {
//                @Override
//                public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                    compositon_time = System.currentTimeMillis();
//                    Log.e("ld","----lottie----setComposition--真正解析的时间--"+ (compositon_time - init_time));
//                    lottie_start.setComposition(composition);//进行解析
//                    lottie_start.setVisibility(View.VISIBLE);
//                    lottie_start.playAnimation();
//                }
//            });



            lottie_start.setVisibility(View.VISIBLE);
            lottie_start.playAnimation();

        } catch (Exception e) {
            Log.e("lottieTest", "启动动画 :出错");
        }
    }


}
