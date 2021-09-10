package com.example.myapplication.animation;

import com.airbnb.lottie.LottieAnimationView;

public interface SelfLottieSetInfoCallback {

    void oneLottieStart(LottieAnimationView view);

    void circleLottieStart(LottieAnimationView view);

    void changeAccelerate(LottieAnimationView view,boolean isAccelerate);

}
