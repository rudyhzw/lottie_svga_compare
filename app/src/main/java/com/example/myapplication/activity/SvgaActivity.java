package com.example.myapplication.activity;

import com.example.myapplication.animation.SelfSvgaAnimitionView;
import com.example.myapplication.animation.SelfSvgaSetInfoCallback;
import com.opensource.svgaplayer.SVGAImageView;

/*
 *可单独使用
 */
public class SvgaActivity extends BaseAnimationActivity {


    public void addNowView() {
        int now_index = activity_main.getChildCount() - 1;
        SelfSvgaAnimitionView view = new SelfSvgaAnimitionView(SvgaActivity.this, randomSample(now_index));
        view.setSelfSetInfoCallback(new SelfSvgaSetInfoCallback() {
            @Override
            public void oneSvgaStart(SVGAImageView view) {

            }

            @Override
            public void circleSvgaStart(SVGAImageView view) {

            }

        });
        activity_main.addView(view);
    }

    public void removeView() {
        int now_index = activity_main.getChildCount() - 1;
        if (now_index == 0) return;

        SelfSvgaAnimitionView view = (SelfSvgaAnimitionView) activity_main.getChildAt(now_index);
        view.cleanSelf();
        activity_main.removeView(view);
    }


    public String randomSample(int position) {
        if (samples.size() == 0) {
            samples.add("bodytwisting.svga");
            samples.add("longing.svga");
            samples.add("puzzle_state.svga");
            samples.add("sad_face.svga");
            samples.add("scared_face.svga");
            samples.add("show_love.svga");
        }

        return samples.get(position);
    }

    @Override
    void setTextViewText() {
        tv_show_text.setText("选择svga同时播放的个数：");
    }
}
