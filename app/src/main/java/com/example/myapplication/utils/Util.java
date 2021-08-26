package com.example.myapplication.utils;

import static android.content.ContentValues.TAG;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import androidx.core.util.Pair;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.PerformanceTracker;

import java.util.List;

public class Util {
    public static void test( LottieAnimationView view){}

//    public static void test( LottieAnimationView view){
//        // 开启性能检测，最好判断环境，例如在测试环境才开启
//// 这里需要注意，该设置必须在设置LottieComposition之后，源码是最好的老师，不懂就去看看，这个后面源码分析就不作分析了
////        PerformanceTracker performanceTracker = mPetActionCore.getPerformanceTracker();
////        if (performanceTracker != null) {
////            mPetActionCore.setPerformanceTrackingEnabled(true);
////        }
//
//// 通过该方法可以打印每个图层渲染所需要的时间
//// 注意，该方法必须在执行动画之后调用
//        PerformanceTracker performanceTracker = view.getPerformanceTracker();
//        if (performanceTracker != null) {
//            List<Pair<String, Float>> sortedRenderTimes = performanceTracker.getSortedRenderTimes();
//            Log.d(TAG, "各图层渲染时间:");
//            for (int i = 0; i < sortedRenderTimes.size(); i++) {
//                Pair<String, Float> layer = sortedRenderTimes.get(i);
//                Log.d(TAG, String.format("\t\t%30s:%.2f", layer.first, layer.second));
//            }
//        }
//
//// 这里传授一个动画帧率是否稳定的黑科技，就是打印动画执行进度，如果打印的内容比平时少，说明就是丢帧了
//// 也可以通过Choreographer，Lottie内部就是通过它刷新的
//        view.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.d(TAG, "ActionType:"  + " onAnimationUpdate--->" + animation.getAnimatedFraction());
//            }
//        });
//    }


}
