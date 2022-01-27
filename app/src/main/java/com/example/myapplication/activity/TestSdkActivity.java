package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lottietest.animation.SelfLottieAnimitionTestView;
import com.example.lottietest.api.LottieCallBack;
import com.example.lottietest.config.Constants;
import com.example.lottietest.manager.StartLottieManager;
import com.example.myapplication.R;
import com.example.myapplication.animation.SelfLottieAnimitionView;
import com.example.myapplication.animation.SelfLottieDrawableView;
import com.example.myapplication.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class TestSdkActivity extends AppCompatActivity {


    LinearLayout activity_main;
    TextView tv_info;
    TextView tv_name;
    LinearLayout ll_container;
    private SelfLottieAnimitionTestView selfLottieAnimitionView;


    public final static int PAGE_1 = 0;
    public final static int PAGE_2 = 1;
    public final static int PAGE_3 = 2;
    public final static int PAGE_4 = 3;
    public final static int PAGE_5 = 4;
    public final static int PAGE_6 = 5;

    private int page = 0;
    private List<String> pageAllList;
    private List<String> nowPageList;
    private StartLottieManager manager;


    //修改数据的地方，对应添加文件夹在assets下
    public List<List<String>> createAllList() {
        List<List<String>> allList = new ArrayList<>();

        ArrayList<String> samples_1 = new ArrayList();
        samples_1.add("2d1/speaking_introduce.json");
        samples_1.add("2d1/speaking_point 2.json");
        samples_1.add("2d1/speaking_tell_2.json");
        samples_1.add("2d1/speaking_touchchin.json");
        samples_1.add("2d1/waving_1.json");

        ArrayList<String> samples_2 = new ArrayList();
        samples_2.add("2d2/_speaking_point的副本.json");
        samples_2.add("2d2/_speaking_tell_1的副本.json");
        samples_2.add("2d2/_speaking_tell_2的副本.json");
        samples_2.add("2d2/_waiting的副本.json");
        samples_2.add("2d2/_waving_1的副本.json");

        ArrayList<String> samples_3 = new ArrayList();
        samples_3.add("2d3/2_speaking_point的副本.json");
        samples_3.add("2d3/2_speaking_tell_1的副本.json");
        samples_3.add("2d3/2_waiting的副本.json");
        samples_3.add("2d3/2_waving_1的副本.json");
        samples_3.add("2d3/2_waving_3的副本.json");

        ArrayList<String> samples_4 = new ArrayList();
        samples_4.add("2d4/speaking_tell的副本.json");
        samples_4.add("2d4/wating的副本.json");
        samples_4.add("2d4/waving_1的副本.json");
        samples_4.add("2d4/waving_2的副本.json");
        samples_4.add("2d4/waving_3的副本.json");

        ArrayList<String> samples_5 = new ArrayList();
        samples_5.add("2d5/speaking_introduce的副本.json");
        samples_5.add("2d5/speaking_point的副本.json");
        samples_5.add("2d5/speaking_text的副本.json");
        samples_5.add("2d5/waiting的副本.json");
        samples_5.add("2d5/waving的副本.json");

        ArrayList<String> samples_6 = new ArrayList();
        samples_6.add("2d6/*#bodytwisting.json");
        samples_6.add("2d6/*#compliment_state.json");
        samples_6.add("2d6/*#longing.json");
        samples_6.add("2d6/*#not_convinced.json");
        samples_6.add("2d6/*#proud_state.json");
        samples_6.add("2d6/*#puzzle_state.json");
        samples_6.add("2d6/*#sad_face.json");
        samples_6.add("2d6/*#scared_face.json");
        samples_6.add("2d6/*#show_love.json");
        samples_6.add("2d6/*#shy_state.json");
        samples_6.add("2d6/*#speaking_ask.json");
        samples_6.add("2d6/*#speaking_circle.json");
        samples_6.add("2d6/*#speaking_introduce.json");
        samples_6.add("2d6/*#speaking_point 2.json");
        samples_6.add("2d6/*#speaking_tell_1.json");
        samples_6.add("2d6/*#speaking_tell_2.json");
        samples_6.add("2d6/*#speaking_touchchin.json");
        samples_6.add("2d6/*#support.json");
        samples_6.add("2d6/*#waiting.json");
        samples_6.add("2d6/*#waving_1.json");
        samples_6.add("2d6/*#waving_2.json");
        samples_6.add("2d6/*#waving_3.json");

        allList.add(samples_1);
        allList.add(samples_2);
        allList.add(samples_3);
        allList.add(samples_4);
        allList.add(samples_5);
        allList.add(samples_6);

        return allList;
    }


    public static void startTestSdkActivity(Context context, int page) {
        Intent intent = new Intent(context, TestSdkActivity.class);
        intent.putExtra("page", page);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sdk);
        activity_main = findViewById(R.id.activity_main);
        tv_info = findViewById(R.id.tv_info);
        tv_name = findViewById(R.id.tv_name);
        ll_container = findViewById(R.id.ll_container);
        page = getIntent().getIntExtra("page", 0);

        //创造数据
        pageAllList = createAllList().get(page);
        nowPageList = pageAllList;


        //进入sdk
        selfLottieAnimitionView = new SelfLottieAnimitionTestView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( DensityUtil.dip2px(this, 200),  DensityUtil.dip2px(this, 200));
        layoutParams.setMargins(DensityUtil.dip2px(this, 50),DensityUtil.dip2px(this, 20),0,0);
        selfLottieAnimitionView.setLayoutParams(layoutParams);
        selfLottieAnimitionView.setBackgroundColor(0xffffddee);
        activity_main.addView(selfLottieAnimitionView);

        //在这调用sdk，然后传入list，拿到输出view
        manager = new StartLottieManager();
        manager.init(this, nowPageList, selfLottieAnimitionView, Constants.MODE_ORDER_CIRCLE, new LottieCallBack() {
            @Override
            public void getNowListPosition(int position) {
                //设置ui
                tv_name.setText(nowPageList.get(position));
            }
        });
        manager.play();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.end();
    }


}
