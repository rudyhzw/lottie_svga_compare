package com.example.lottietest.api;

import com.airbnb.lottie.LottieComposition;

import java.util.List;
import java.util.Map;

public interface LottieIncomeDataImp {

    //下载url数据
    public void downLoadUrl(String url);

    //下载list数据
    public void downLoadList(List<String> urlList);

    //解析json
    public LottieComposition parseJsonToLottieComposition(String json);

    //解析多条json
    public Map<String, LottieComposition> parseJsonToLottieComposition(List<String> json);

}
