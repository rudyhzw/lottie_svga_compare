package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.animation.SelfLottieAnimitionView;
import com.example.myapplication.animation.SelfLottieSetInfoCallback;
import com.example.myapplication.animation.SelfSvgaAnimitionView;
import com.example.myapplication.animation.SelfSvgaSetInfoCallback;
import com.opensource.svgaplayer.SVGAImageView;

public class CommenAnimationActivity extends BaseAnimationActivity {
    public final static int TAG_LOTTIE = 1;
    public final static int TAG_SVGA = 2;
    public final static String TAG_TYPE = "tag_type";
    private int tag_type = 0;

    public static void startCommenAnimationActivity(Context context, int tag) {
        Intent intent = new Intent(context, CommenAnimationActivity.class);
        intent.putExtra(TAG_TYPE, tag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag_type = getIntent().getIntExtra(TAG_TYPE, 0);
        setTextViewText();
    }

    @Override
    void addNowView() {
        int now_index = activity_main.getChildCount() - 1;

        switch (tag_type) {
            case TAG_LOTTIE:
                SelfLottieAnimitionView view_lottie = new SelfLottieAnimitionView(this, randomSample(now_index));
                view_lottie.setSelfSetInfoCallback(new SelfLottieSetInfoCallback() {
                    @Override
                    public void oneLottieStart(LottieAnimationView view) {

                    }

                    @Override
                    public void circleLottieStart(LottieAnimationView view) {

                    }
                });
                activity_main.addView(view_lottie);
                break;

            case TAG_SVGA:
                SelfSvgaAnimitionView view_svga = new SelfSvgaAnimitionView(this, randomSample(now_index));
                view_svga.setSelfSetInfoCallback(new SelfSvgaSetInfoCallback() {
                    @Override
                    public void oneSvgaStart(SVGAImageView view) {

                    }

                    @Override
                    public void circleSvgaStart(SVGAImageView view) {

                    }

                });
                activity_main.addView(view_svga);
                break;
        }
    }

    @Override
    void removeView() {
        int now_index = activity_main.getChildCount() - 1;
        if (now_index == 0) return;

        switch (tag_type) {
            case TAG_LOTTIE:
                SelfLottieAnimitionView view_lottie = (SelfLottieAnimitionView) activity_main.getChildAt(now_index);
                view_lottie.cleanSelf();
                activity_main.removeView(view_lottie);
                break;

            case TAG_SVGA:
                SelfSvgaAnimitionView view_svga = (SelfSvgaAnimitionView) activity_main.getChildAt(now_index);
                view_svga.cleanSelf();
                activity_main.removeView(view_svga);

                break;
        }
    }

    @Override
    String randomSample(int position) {

        if (samples.size() == 0) {
            switch (tag_type) {
                case TAG_LOTTIE:
                    samples.add("bodytwisting.json");
                    samples.add("longing.json");
                    samples.add("puzzle_state.json");
                    samples.add("sad_face.json");
                    samples.add("scared_face.json");
                    samples.add("show_love.json");
                    break;
                case TAG_SVGA:
                    samples.add("bodytwisting.svga");
                    samples.add("longing.svga");
                    samples.add("puzzle_state.svga");
                    samples.add("sad_face.svga");
                    samples.add("scared_face.svga");
                    samples.add("show_love.svga");
                    break;
            }
        }

        if (position > samples.size()) {
            return samples.get(0);
        } else {
            return samples.get(position);
        }

    }

    @Override
    void setTextViewText() {
        switch (tag_type) {
            case TAG_LOTTIE:
                tv_show_text.setText("选择lottie同时播放的个数：");
                break;
            case TAG_SVGA:
                tv_show_text.setText("选择svga同时播放的个数：");
                break;
        }
    }
}
