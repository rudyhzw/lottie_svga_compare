package com.example.lottietest.api;

import android.content.Context;

import com.example.lottietest.animation.SelfLottieAnimitionTestView;

import java.util.List;

public interface StartLottieControlImp {

    public void init(Context context, List<String> jsonList, SelfLottieAnimitionTestView view, int executeMode , LottieCallBack lottieCallBack);

    public void play();

    public void end();

    public int getNowPlayJsonListPosition();

    public void pause();

    public void stop();

    public void controlOneMode(int executeMode);
}
