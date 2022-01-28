package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.airbnb.lottie.RenderMode;
import com.example.myapplication.R;
import com.example.myapplication.animation.SelfLottieAnimitionView;
import com.example.myapplication.animation.SelfLottieDrawableView;
import com.example.myapplication.animation.SelfLottieSetInfoCallback;
import com.example.myapplication.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TestLottieActivity extends AppCompatActivity {


    LinearLayout activity_main;
    TextView tv_info;
    TextView tv_name;
    Timer scheduleTimer;
    LinearLayout ll_container;
    private SelfLottieAnimitionView selfLottieAnimitionView;
    private SelfLottieDrawableView selfLottieDrawableView;

    public final static int PAGE_1 = 0;
    public final static int PAGE_2 = 1;
    public final static int PAGE_3 = 2;
    public final static int PAGE_4 = 3;
    public final static int PAGE_5 = 4;
    public final static int PAGE_6 = 5;
    public final static int PAGE_7 = 6;
    public final static int PAGE_8 = 7;
    public final static int PAGE_9 = 8;
    public final static int PAGE_10 = 9;

    private int page = 0;
    private List<String> pageAllList;
    private List<String> nowPageList;

    private LottieComposition now_composition;
    Map<String, LottieComposition> map = new HashMap<>();

    //修改数据的地方，对应添加文件夹在assets下
    public List<List<String>> createAllList() {
        List<List<String>> allList = new ArrayList<>();

        ArrayList<String> samples_6 = new ArrayList();
        samples_6.add("lion_standard/###longing.json");
        samples_6.add("lion_standard/###speaking_circle.json");
        samples_6.add("lion_standard/###speaking_introduce.json");


        ArrayList<String> samples_7 = new ArrayList();
        samples_7.add("lion_lay_mid/###longing -TM.json");
        samples_7.add("lion_lay_mid/###speaking_circle-TM.json");
        samples_7.add("lion_lay_mid/###speaking_introduce-TM.json");

        ArrayList<String> samples_8 = new ArrayList();
        samples_8.add("lion_lay_mini/###longing -TS.json");
        samples_8.add("lion_lay_mini/###speaking_circle-TS.json");
        samples_8.add("lion_lay_mini/###speaking_introduce-TS.json");

        ArrayList<String> samples_9 = new ArrayList();
        samples_9.add("lion_fps_mid/###longing-KM.json");
        samples_9.add("lion_fps_mid/###speaking_circle-KM.json");
        samples_9.add("lion_fps_mid/###speaking_introduce-KM.json");

        ArrayList<String> samples_10 = new ArrayList();
        samples_10.add("lion_fps_mini/###longing-TKS.json");
        samples_10.add("lion_fps_mini/###speaking_circle-TKS.json");
        samples_10.add("lion_fps_mini/###speaking_introduce-TKS.json");

        ArrayList<String> samples_1 = new ArrayList();
        samples_1.add("dingdang_standard/*#bodytwisting.json");
        samples_1.add("dingdang_standard/*#compliment_state.json");
        samples_1.add("dingdang_standard/*#speaking_circle.json");
        samples_1.add("dingdang_standard/*#speaking_tell_1.json");


        ArrayList<String> samples_2 = new ArrayList();
        samples_2.add("dingdang_lay_mid/*#bodytwisting-TZ.json");
        samples_2.add("dingdang_lay_mid/*#compliment_state-TZ.json");
        samples_2.add("dingdang_lay_mid/*#speaking_circle -TZ.json");
        samples_2.add("dingdang_lay_mid/*#speaking_tell_1-TZ.json");


        ArrayList<String> samples_3 = new ArrayList();
        samples_3.add("dingdang_lay_mini/*#bodytwisting-TX.json");
        samples_3.add("dingdang_lay_mini/*#compliment_state-TX.json");
        samples_3.add("dingdang_lay_mini/*#speaking_circle -TX.json");
        samples_3.add("dingdang_lay_mini/*#speaking_tell_1-TX.json");


        ArrayList<String> samples_4 = new ArrayList();
        samples_4.add("dingdang_fps_mid/*#bodytwisting-KM.json");
        samples_4.add("dingdang_fps_mid/*#compliment_state-KM.json");
        samples_4.add("dingdang_fps_mid/*#speaking_circle-KM.json");
        samples_4.add("dingdang_fps_mid/*#speaking_tell_1-KM.json");


        ArrayList<String> samples_5 = new ArrayList();
        samples_5.add("dingdang_fps_mini/*#bodytwisting-KS.json");
        samples_5.add("dingdang_fps_mini/*#compliment_state-KS.json");
        samples_5.add("dingdang_fps_mini/*#speaking_circle-KS.json");
        samples_5.add("dingdang_fps_mini/*#speaking_tell_1-KS.json");


        allList.add(samples_1);
        allList.add(samples_2);
        allList.add(samples_3);
        allList.add(samples_4);
        allList.add(samples_5);
        allList.add(samples_6);
        allList.add(samples_7);
        allList.add(samples_8);
        allList.add(samples_9);
        allList.add(samples_10);

        return allList;
    }


    public static void startTestLottieActivity(Context context, int page) {
        Intent intent = new Intent(context, TestLottieActivity.class);
        intent.putExtra("page", page);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        activity_main = findViewById(R.id.activity_main);
        tv_info = findViewById(R.id.tv_info);
        tv_name = findViewById(R.id.tv_name);
        ll_container = findViewById(R.id.ll_container);
        page = getIntent().getIntExtra("page", 0);

        //创造数据
        pageAllList = createAllList().get(page);
        nowPageList = pageAllList;
        //解析json
//        initCompositoin();

        //解析第一个composition
        getComposition(nowPageList.get(0));

        //添加checkBox
        for (int i = 0; i < pageAllList.size(); i++) {
            addCheckBoxView(pageAllList.get(i));
        }

        //添加lottie
//        addNowView();
//        addNowDrawableView();

        //查看性能
        startTimer(this);
    }

    public void addCheckBoxView(String boxText) {
        CheckBox checkBox = new CheckBox(TestLottieActivity.this);
        checkBox.setText(boxText);
        checkBox.setChecked(true);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    addList(boxText);
                } else {
                    removeList(boxText);
                }

                if (nowPageList.size() > 0) {
                    if (selfLottieAnimitionView != null) {
                        selfLottieAnimitionView.setData(nowPageList);
                    } else {
                        getComposition(nowPageList.get(0));
                    }

                }
            }
        });
        ll_container.addView(checkBox);
    }

    //添加list
    public void addList(String boxText) {
        nowPageList.add(boxText);
    }

    //删除list里的数据
    public void removeList(String boxText) {
        if (TextUtils.isEmpty(boxText)) return;
        int index = -1;
        for (int i = 0; i < nowPageList.size(); i++) {
            if (nowPageList.get(i).equals(boxText)) {
                index = i;
            }
        }

        if (index != -1) {
            nowPageList.remove(index);
        }
    }


    public void addNowView() {

        selfLottieAnimitionView = new SelfLottieAnimitionView(TestLottieActivity.this, nowPageList, map, now_composition);
        selfLottieAnimitionView.setSelfSetInfoCallback(new SelfLottieSetInfoCallback() {
            @Override
            public void setNowJsonName(String name) {
                tv_name.setText(name);
            }

            @Override
            public void startAnimation(int position) {
                if (position == nowPageList.size() - 1) {
                    getComposition(nowPageList.get(0));
                } else {
                    getComposition(nowPageList.get(position + 1));
                }
            }
        });

        activity_main.addView(selfLottieAnimitionView);
    }


//    public void addNowDrawableView() {
//        selfLottieDrawableView = new SelfLottieDrawableView(TestLottieActivity.this, nowPageList, map);
//        selfLottieDrawableView.setSelfSetInfoCallback(new SelfLottieSetInfoCallback() {
//            @Override
//            public void setNowJsonName(String name) {
//                tv_name.setText(name);
//            }
//
//            @Override
//            public void startAnimation(int position) {
//            }
//        });
//
//        activity_main.addView(selfLottieDrawableView);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scheduleTimer.cancel();
    }


    public void startTimer(Context context) {
        if (scheduleTimer == null) scheduleTimer = new Timer();
        else scheduleTimer.cancel();
        scheduleTimer.schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                        String all = ""
                                + "\n应用包名：" + DeviceUtils.getTopPacakge(context)
                                + "\n应用名称：" + DeviceUtils.getAppName(context)
                                + "\n应用版本：" + DeviceUtils.getVersionName(context)
                                + "\n应用PID：" + DeviceUtils.getAppNameByPID(context)
                                + "\n手机屏幕：" + DeviceUtils.getDeviceWidth(context) + "*" + DeviceUtils.getDeviceHeight(context)
                                + "\n手机厂商：" + DeviceUtils.getDeviceBrand()
                                + "\n手机型号:" + DeviceUtils.getSystemModel()
                                + "\n产品名：" + DeviceUtils.getDeviceProduct()
                                + "\nandroid sdk：" + DeviceUtils.getDeviceSDK()
//                                + "\n机器cpu型号：" + DeviceUtils.getCpuName()
                                + "\n机器cpu型号：" + DeviceUtils.getSupportABI()
                                + "\nRam大小：" + DeviceUtils.getRamSize(context)
                                + "\n手机系统版本：" + DeviceUtils.getSystemVersion()
                                + "\n手机最大内存：" + DeviceUtils.getLargeMemorySize(context)
                                + "\n系统内存大小：" + DeviceUtils.getMemorySize(context)
                                + "\n手机额定RAM大小:" + DeviceUtils.getStandardRamSize(context) + "GB"
                                + "\n手机性能等级：" + DeviceUtils.getPerformanceLevel(context)

                                + "\n" + DeviceUtils.getMemoryInfo(context)
                                + "\ncpu最大使用频率：" + DeviceUtils.getMaxCpuFreq()
                                + "\ncpu最小使用频率：" + DeviceUtils.getMinCpuFreq()
                                + "\ncpu当前使用频率：" + DeviceUtils.getCurCpuFreq()
                                + "\n应用cpu占有率：" + DeviceUtils.getProcessCpuUsage();
//                                + "\n应用cpu占有率：" + DeviceUtils.getProcessCpuRate()
//                                + "\ncpu总的使用率：" + DeviceUtils.getCPURateDesc_All()
//                                + "\n单cpu的使用率：" + DeviceUtils.getCPURateDesc();

                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {

                                        Log.d("TestLottie", all);
                                        tv_info.setText(all);
                                    }
                                });
                    }
                },
                100L, 7000L);
    }


    //统一的解析json
    private void initCompositoin() {
        if (nowPageList == null) return;
        for (int i = 0; i < nowPageList.size(); i++) {
            getComposition(nowPageList.get(i));
        }
    }


    //一条一条解析
    private void getComposition(String json) {

        long time = System.currentTimeMillis();
        LottieComposition.Factory.fromAssetFileName(this, json, new OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                map.put(json, composition);
                //设置composition
                now_composition = composition;
                Log.e("lottieTest", "--lottie--" + json + "--真正解析成LottieComposition耗时：" + (System.currentTimeMillis() - time));

                if (selfLottieAnimitionView == null) {
                    addNowView();
                } else {
                    selfLottieAnimitionView.setNowLottieComposition(now_composition);
                }
            }
        });


    }


    //创造旧的数据
    private List<List<String>> createOldList() {
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
}
