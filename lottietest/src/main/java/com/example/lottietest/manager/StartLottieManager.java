package com.example.lottietest.manager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.example.lottietest.animation.SelfLottieAnimitionTestView;
import com.example.lottietest.api.LottieCallBack;
import com.example.lottietest.api.StartLottieControlImp;
import com.example.lottietest.config.Constants;
import com.example.lottietest.listener.SelfLottieSetInfoCallback;

import java.util.List;

public class StartLottieManager implements StartLottieControlImp {

    private Context mContext;
    private SelfLottieAnimitionTestView mSelfLottieAnimitionTestView;
    private List<String> mJsonList;

    private int nowPlayingPosition = 0;
    private LottieComposition mLottieComposition;
    private int mExecuteMode;

    private LottieCallBack mLottieCallBack;


    @Override
    public void init(Context context, List<String> jsonList, SelfLottieAnimitionTestView view, int executeMode, LottieCallBack lottieCallBack) {
        this.mSelfLottieAnimitionTestView = view;
        this.mJsonList = jsonList;
        this.mContext = context;
        this.mExecuteMode = executeMode;
        this.mLottieCallBack = lottieCallBack;
    }


    @Override
    public void end() {
        nowPlayingPosition = 0;
        mLottieComposition = null;
        mSelfLottieAnimitionTestView.clearAnimation();
        mSelfLottieAnimitionTestView.removeAllViews();
    }

    @Override
    public int getNowPlayJsonListPosition() {
        return nowPlayingPosition;
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void controlOneMode(int executeMode) {

    }

    @Override
    public void play() {
        if (mJsonList == null || mJsonList.size() == 0) return;
        getComposition(mJsonList.get(0));
    }


    private void setLottieView() {
        mSelfLottieAnimitionTestView.setData(mJsonList);
        mSelfLottieAnimitionTestView.setNowLottieComposition(mLottieComposition);
        switch (mExecuteMode) {
            case Constants.MODE_ONE:
                mSelfLottieAnimitionTestView.setMode(nowPlayingPosition, false, false, false);
                break;
            case Constants.MODE_ONE_CIRCLE:
                mSelfLottieAnimitionTestView.setMode(nowPlayingPosition, true, false, false);
                break;
            case Constants.MODE_ORDER:
                mSelfLottieAnimitionTestView.setMode(nowPlayingPosition, false, true, false);
                break;
            default:
                mSelfLottieAnimitionTestView.setMode(nowPlayingPosition, false, false, true);
                break;
        }

        mSelfLottieAnimitionTestView.setSelfSetInfoCallback(new SelfLottieSetInfoCallback() {

            @Override
            public void startAnimation(int position) {
                nowPlayingPosition = position;
                if (mLottieCallBack != null) {
                    mLottieCallBack.getNowListPosition(position);
                }
                if (position == mJsonList.size() - 1) {
                    getComposition(mJsonList.get(0));
                } else {
                    getComposition(mJsonList.get(position + 1));
                }
            }
        });
    }


    boolean isFirst = true;

    //依次解析
    private void getComposition(String json) {

        long time = System.currentTimeMillis();


        LottieComposition.Factory.fromAssetFileName(mContext, json, new OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                mLottieComposition = composition;
                Log.e("ld", "----lottie----setComposition----真正解析时间--" + (System.currentTimeMillis() - time));
                if (isFirst) {
                    isFirst = false;
                    setLottieView();
                } else {
                    mSelfLottieAnimitionTestView.setNowLottieComposition(mLottieComposition);
                }
            }
        });
    }

}
