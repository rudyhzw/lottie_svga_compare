package com.example.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseAnimationActivity extends AppCompatActivity {

    Spinner mSpinnerSimple;
    LinearLayout activity_main;
    TextView tv_info;
    TextView tv_show_text;
    Timer scheduleTimer;
    ArrayList<String> samples = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        mSpinnerSimple = findViewById(R.id.spinner_simple);
        changeSpinner(mSpinnerSimple);
        activity_main = findViewById(R.id.activity_main);
        tv_info = findViewById(R.id.tv_info);
        tv_show_text = findViewById(R.id.tv_show_text);

        startTimer(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        scheduleTimer.cancel();
        cleanAndRemove();
    }


    public void changeSpinner(View v) {

        mSpinnerSimple.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String s = adapterView.getItemAtPosition(position).toString();
                Log.i("ld", "ld------" + s);
                changToList(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void changToList(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        int count = activity_main.getChildCount() - 1;
        int s_count = Integer.parseInt(s);

        if (s_count == count) {
            return;
        } else if (s_count > count) {
            for (int i = 0; i < s_count - count; i++) {
                addNowView();
            }
        } else {
            for (int i = 0; i < count - s_count; i++) {
                removeView();
            }
        }


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

                                        Log.d("ld", all);
                                        tv_info.setText(all);
                                    }
                                });
                    }
                },
                100L, 7000L);
    }

    public void cleanAndRemove() {
        int now_index = activity_main.getChildCount() - 1;
        for (int i = 0; i < now_index; i++) {
            removeView();
        }
    }

    abstract void addNowView();

    abstract void removeView();

    abstract String randomSample(int position);

    abstract void setTextViewText();


}
