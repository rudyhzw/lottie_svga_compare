package com.example.myapplication.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class BaseAnimationView extends RelativeLayout implements SelfPlayControl {
    private String sJson;
    public Context context;

    public BaseAnimationView(Context context, String sJson) {
        super(context);
        this.sJson = sJson;
        this.context = context;
        init();
    }

    public BaseAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public String getJSon() {
        return sJson;
    }

    public abstract void init();

    public abstract void initSelf();

    public abstract void playSelf();

    public abstract void startAndPlaySelfOne();

    public abstract void startAndPlaySelfCircle();

    public abstract void cleanSelf();


}
