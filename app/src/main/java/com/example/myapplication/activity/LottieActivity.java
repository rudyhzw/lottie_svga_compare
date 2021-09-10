package com.example.myapplication.activity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.animation.SelfLottieAnimitionView;
import com.example.myapplication.animation.SelfLottieSetInfoCallback;

public class LottieActivity extends BaseAnimationActivity {


    public void addNowView() {
        int now_index = activity_main.getChildCount() - 1;
        SelfLottieAnimitionView view = new SelfLottieAnimitionView(LottieActivity.this, randomSample(now_index));
        view.setSelfSetInfoCallback(new SelfLottieSetInfoCallback() {
            @Override
            public void oneLottieStart(LottieAnimationView view) {

            }

            @Override
            public void circleLottieStart(LottieAnimationView view) {

            }

            @Override
            public void changeAccelerate(LottieAnimationView view, boolean isAccelerate) {

            }
        });
        activity_main.addView(view);
    }

    public void removeView() {
        int now_index = activity_main.getChildCount() - 1;
        if (now_index == 0) return;

        SelfLottieAnimitionView view = (SelfLottieAnimitionView) activity_main.getChildAt(now_index);
        view.cleanSelf();
        activity_main.removeView(view);
    }


    public String randomSample(int position) {
        if (samples.size() == 0) {
            samples.add("bodytwisting.json");
            samples.add("longing.json");
            samples.add("puzzle_state.json");
            samples.add("sad_face.json");
            samples.add("scared_face.json");
            samples.add("show_love.json");

        }
//        String name = samples.get((int) Math.floor(Math.random() * samples.size()));
//        Log.d("ld","----name---"+name);
        return samples.get(position);
    }

    @Override
    void setTextViewText() {
        tv_show_text.setText("选择lottie同时播放的个数：");
    }
}
//    private void loadUrls(String url) {
//        LottieComposition.Factory
//                .fromJson(getResources(), json, new OnCompositionLoadedListener() {
//                    @Override
//                    public void onCompositionLoaded(LottieComposition composition) {
//                        animation_view_network.setComposition(composition);
//                        animation_view_network.playAnimation();
//                    }
//                });
//    }


//    private void loadUrl(String url) {
//        Log.d("url_ld", url);
//        Toast.makeText(this, url, Toast.LENGTH_LONG);
//        Request request;
//        try {
//            request = new Request.Builder()
//                    .url(url)
//                    .build();
//        } catch (IllegalArgumentException e) {
//
//            return;
//        }
//
//        if (client == null) {
//
//            client = new OkHttpClient();
//        }
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                if (!response.isSuccessful()) {
//                }
//
//                try {
//                    JSONObject json = new JSONObject(response.body().string());
//                    Log.d("json_ld", json.toString());
//                    Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_LONG);
//                    LottieComposition.Factory.fromJsonString(json.toString(), new OnCompositionLoadedListener() {
//                        @Override
//                        public void onCompositionLoaded(LottieComposition composition) {
//                            animation_view_network.setComposition(composition);
//                            animation_view_network.playAnimation();
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        });
//    }

//    private void setComposition(LottieComposition composition) {
//
//        animation_view_network.setProgress(0);//设置当前进度
//
//        animation_view_network.loop(true);//动画是否循环播放
//
//        animation_view_network.setComposition(composition);
//
//        animation_view_network.playAnimation();//开始动画
//
//    }


//获取Lottie组件
//        animation_view_network = (LottieAnimationView) findViewById(R.id.animation_view_network);
//        btn_clean = findViewById(R.id.btn_clean);
//        animation_view_network.setAnimationFromUrl("http://cdn.trojx.me/blog_raw/lottie_data_edit.json");
//        animation_view_network.playAnimation();
//        Util.test(animation_view_network);
//
//        btn_clean.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                animation_view_network.clearAnimation();
//                animation_view_network.setAnimationFromUrl("https://emotion-formal-1251316161.cos.ap-guangzhou.myqcloud.com/miniprogram/waving_1.json");
//                ;
////                animation_view_network.useHardwareAcceleration(true);
////                animation_view_network.setRepeatCount(LottieDrawable.INFINITE);
//                animation_view_network.playAnimation();
//
//            }
//        });

//    LottieAnimationView animation_view_network;//Lottie组件
//    Button btn_clean;

//    private OkHttpClient client;//Okhttp客户端
//    String online_json = "http://cdn.trojx.me/blog_raw/lottie_data_edit.json";
