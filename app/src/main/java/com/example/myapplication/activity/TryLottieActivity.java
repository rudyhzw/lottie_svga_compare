package com.example.myapplication.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.example.myapplication.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TryLottieActivity extends AppCompatActivity {
    private LottieDrawable drawable;
    private LottieDrawable mPetLottieDrawable;
    private   View view_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);

        view_root = findViewById(R.id.view_root);

//        drawable = new LottieDrawable();
//        LottieComposition.Factory.fromAssetFileName(this, "2d1/speaking_introduce.json", new OnCompositionLoadedListener() {
//            @Override
//            public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                drawable.setComposition(composition);
//            }
//        });
////        drawable.loop(true);
//        drawable.playAnimation();
//        drawable.addAnimatorListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
////                view_root.setBackground(init("2d1/speaking_point 2.json"));
//                init("2d1/speaking_point 2.json");
//                view_root.invalidateDrawable(drawable);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//
//        view_root.setBackground(drawable);

//        samples_1.add("2d1/speaking_introduce.json");
//        samples_1.add("2d1/speaking_point 2.json");
//        samples_1.add("2d1/speaking_tell_1.json");
//        samples_1.add("2d1/speaking_tell_2.json");
//        samples_1.add("2d1/speaking_touchchin.json");

        ArrayList<String> list = new ArrayList();
        list.add("2d1/speaking_introduce.json");
        list.add("2d1/speaking_point 2.json");
        list.add("2d1/speaking_tell_1.json");
        list.add("2d1/speaking_tell_2.json");
        list.add("2d1/speaking_touchchin.json");


        mPetLottieDrawable = new LottieDrawable();
        testInit(list.get(0));
        mPetLottieDrawable.addAnimatorListener(new Animator.AnimatorListener() {
            int now_position = 0;

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

//                view_root.setBackgroundDrawable(mPetLottieDrawable);

                if (now_position < (list.size() - 1)) {
                    now_position = now_position + 1;
                } else {
                    now_position = 0;
                }
                testInit(list.get(now_position));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        view_root.setBackgroundDrawable(mPetLottieDrawable);


    }


    public static void startTryLottieActivity(Context context, int page) {
        Intent intent = new Intent(context, TryLottieActivity.class);
        intent.putExtra("page", page);
        context.startActivity(intent);
    }


    private void init(String name) {
//        LottieDrawable drawable = new LottieDrawable();

        drawable.clearComposition();
        LottieComposition.Factory.fromAssetFileName(this, name, new OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                drawable.setComposition(composition);
            }
        });
//        drawable.loop(true);
        drawable.playAnimation();

//        return drawable;
    }


    private void testInit(String json) {
        if (mPetLottieDrawable.isAnimating()) {
            mPetLottieDrawable.cancelAnimation();
        }
        mPetLottieDrawable.setProgress(0);
//        mPetLottieDrawable.clearComposition();
        view_root.setVisibility(View.VISIBLE);
        LottieComposition.Factory.fromAssetFileName(this, json, new OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                mPetLottieDrawable.setComposition(composition);
//                mPetLottieDrawable.setImagesAssetsFolder(image);
                mPetLottieDrawable.playAnimation();
//                mPetLottieIv.setVisibility(View.VISIBLE);
//                Logger.d(TAG, "playPetLottieAnimationView === playAnimation show");
            }
        });

    }
}
