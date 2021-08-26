package com.example.myapplication.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 描    述:获取设备的一些信息
 */
public class DeviceUtils {

    //设备性能等级
    public static final int DEVICE_PERFORMANCE_LEVEL_POOR = 0;//极差
    public static final int DEVICE_PERFORMANCE_LEVEL_LOW = 1;//较低
    public static final int DEVICE_PERFORMANCE_LEVEL_NORMAL = 2;//正常

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                device_id = tm.getDeviceId();
            }

            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean isPadMode() {
        return false;
    }

    /**
     * 获取设备是否是pad
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDescription(Context context) {
        try {
            return "Android " +
                    getSystemVersion() +
                    "\n型号: " +
                    getDeviceBrand() +
                    " " +
                    getSystemModel() +
                    "\nPAD: " +
                    isPad(context) +
                    "\nRAM: " +
                    getRamSize(context) +
                    "\nSUPPORT ABI: " +
                    getSupportABI()
                    ;
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备支持cpu型号
     */
    public static String getSupportABI() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return Arrays.toString(Build.SUPPORTED_ABIS);
            } else {
                return Build.CPU_ABI;
            }
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 获取设备支持cpu型号
     */
    public static boolean isSupportArmCpu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (String abi : Build.SUPPORTED_ABIS) {
                if (abi != null && (abi.contains("arm") || abi.contains("ARM"))) {
                    return true;
                }
            }
        } else {
            String abi = Build.CPU_ABI;
            return abi != null && (abi.contains("arm") || abi.contains("ARM"));
        }
        return false;
    }

    /**
     * 判断有没有安装该apk
     *
     * @param packageName
     * @param context
     * @return
     */
    public static boolean isApkAvilible(String packageName, Context context) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }

    public static int getMemorySize(Context context) {
        if (context != null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                return activityManager.getMemoryClass();
            }
        }
        return 0;
    }


    /**
     * 获取设备最大内存值
     */
    public static int getLargeMemorySize(Context context) {
        if (context != null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                return activityManager.getLargeMemoryClass();
            }
        }
        return 0;
    }

    public static String getAppVersionName() {
//        if (TextUtils.isEmpty(BuildConfig.HOTFIX_VERSION)) {
//            return BuildConfig.VERSION_NAME;
//            getAppVersionName();
//        } else {
//            return BuildConfig.VERSION_NAME + "." + BuildConfig.HOTFIX_VERSION;
//        }
        return getAppVersionName();
    }

    public static String getRamSize(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            return FileSizeUtil.formatFileSize(mi.totalMem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机性能等级
     * 这里只是根据内存进行简单分级
     *
     * @param context
     * @return DEVICE_PERFORMANCE_LEVEL_POOR 极差
     * DEVICE_PERFORMANCE_LEVEL_LOW 较低
     * DEVICE_PERFORMANCE_LEVEL_NORMAL 正常
     */
    public static String getPerformanceLevel(Context context) {
        long totalMemorySize = 0;
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            totalMemorySize = mi.totalMem;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (totalMemorySize > 0 && totalMemorySize < 1024 * 1024 * 1024 * 2L) { //2GB及以下
            return "极差";
        } else if (totalMemorySize > 0 && totalMemorySize < 1024 * 1024 * 1024 * 3L) {//3GB及以下
            return "较低";
        } else {
            return "正常";
        }
    }

    /**
     * 获取手机额定RAM大小
     *
     * @param context
     * @return
     */
    public static int getStandardRamSize(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            return (int) Math.round(1.0 * mi.totalMem / (1024 * 1024 * 1024.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isVivoX9() {
        return "vivo X9".equals(getSystemModel());
    }


    /**
     * 根据Pid得到进程名
     */
    public static String getAppNameByPID(Context context) {
        int pid = android.os.Process.myPid();

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }


    /**
     * 获取CPU型号
     *
     * @return
     */
    public static String getCpuName() {

        String str1 = "/proc/cpuinfo";
        String str2 = "";

        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2 = localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    return str2.split(":")[1];
                }
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return null;

    }

    // 获取CPU最大频率（单位KHZ）
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",

                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim() + "Hz";
    }


    // 获取CPU最小频率（单位KHZ）
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim() + "Hz";
    }


    // 实时获取CPU当前频率（单位KHZ）

    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim() + "Hz";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //进程CPU占用率,CPU占用率 = (进程T2-进程T1)/(系统T2-系统T1) 的时间片比值
    public static double getProcessCpuUsage() {
        int pid = android.os.Process.myPid();
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();
            String[] toks = load.split(" ");

            double totalCpuTime1 = 0.0;
            int len = toks.length;
            for (int i = 2; i < len; i++) {
                totalCpuTime1 += Double.parseDouble(toks[i]);
            }

            RandomAccessFile reader2 = new RandomAccessFile("/proc/" + pid + "/stat", "r");
            String load2 = reader2.readLine();
            String[] toks2 = load2.split(" ");

            double processCpuTime1 = 0.0;
            double utime = Double.parseDouble(toks2[13]);
            double stime = Double.parseDouble(toks2[14]);
            double cutime = Double.parseDouble(toks2[15]);
            double cstime = Double.parseDouble(toks2[16]);

            processCpuTime1 = utime + stime + cutime + cstime;

            try {
                Thread.sleep(360);
            } catch (Exception e) {
                e.printStackTrace();
            }
            reader.seek(0);
            load = reader.readLine();
            reader.close();
            toks = load.split(" ");
            double totalCpuTime2 = 0.0;
            len = toks.length;
            for (int i = 2; i < len; i++) {
                totalCpuTime2 += Double.parseDouble(toks[i]);
            }
            reader2.seek(0);
            load2 = reader2.readLine();
            String[] toks3 = load2.split(" ");

            double processCpuTime2 = 0.0;
            utime = Double.parseDouble(toks3[13]);
            stime = Double.parseDouble(toks3[14]);
            cutime = Double.parseDouble(toks3[15]);
            cstime = Double.parseDouble(toks3[16]);

            processCpuTime2 = utime + stime + cutime + cstime;
            double usage = (processCpuTime2 - processCpuTime1) * 100.00
                    / (totalCpuTime2 - totalCpuTime1);
            BigDecimal b = new BigDecimal(usage);
            double res = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return 0.0;
    }

    /**
     * get CPU rate
     *
     * @return
     */
    public static int getProcessCpuRate() {

        StringBuilder tv = new StringBuilder();
        int rate = 0;

        try {
            String Result;
            Process p;
            p = Runtime.getRuntime().exec("top -n 1");

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((Result = br.readLine()) != null) {
                if (Result.trim().length() < 1) {
                    continue;
                } else {
                    String[] CPUusr = Result.split("%");
                    tv.append("USER:" + CPUusr[0] + "\n");
                    String[] CPUusage = CPUusr[0].split("User");
                    String[] SYSusage = CPUusr[1].split("System");
                    tv.append("CPU:" + CPUusage[1].trim() + " length:" + CPUusage[1].trim().length() + "\n");
                    tv.append("SYS:" + SYSusage[1].trim() + " length:" + SYSusage[1].trim().length() + "\n");

                    rate = Integer.parseInt(CPUusage[1].trim()) + Integer.parseInt(SYSusage[1].trim());
                    break;
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(rate + "");
        return rate;
    }


    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取内存信息
     */
    public static String getMemoryInfo(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);

        String result =
                "系统总内存:" + (info.totalMem / (1024 * 1024)) + "M" +
                        "\n" + "系统是否处于低内存运行：" + info.lowMemory +
                        "\n" + "系统剩余内存低于" + (info.threshold / (1024 * 1024)) + "M时为低内存运行" +
                        "\n" + "系统剩余内存:" + (info.availMem / (1024 * 1024)) + "M";

        return result;
    }

    /**
     * 获取设备宽度（px）
     */
    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     */
    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取产品名
     */
    public static String getDeviceProduct() {
        return Build.PRODUCT;
    }


    /**
     * 获取手机Android系统SDK
     */
    public static int getDeviceSDK() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 获取application中指定key的meta-data值
     */

    public static String getApplicationMetadata(Context context, String metaDataKey) {
        ApplicationInfo info = null;
        try {
            PackageManager pm = context.getPackageManager();

            info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

            return String.valueOf(info.metaData.get(metaDataKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTopPacakge(Context mContext) {

        try {
            ActivityManager am = (ActivityManager) mContext
                    .getSystemService(mContext.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            return cn.getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 获取cpu总占有率
     */
    public static String getCPURateDesc_All() {
        String path = "/proc/stat";// 系统CPU信息文件
        long totalJiffies[] = new long[2];
        long totalIdle[] = new long[2];
        int firstCPUNum = 0;//设置这个参数，这要是防止两次读取文件获知的CPU数量不同，导致不能计算。这里统一以第一次的CPU数量为基准
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        Pattern pattern = Pattern.compile(" [0-9]+");
        for (int i = 0; i < 2; i++) {
            totalJiffies[i] = 0;
            totalIdle[i] = 0;
            try {
                fileReader = new FileReader(path);
                bufferedReader = new BufferedReader(fileReader, 8192);
                int currentCPUNum = 0;
                String str;
                while ((str = bufferedReader.readLine()) != null && (i == 0 || currentCPUNum < firstCPUNum)) {
                    if (str.toLowerCase().startsWith("cpu")) {
                        currentCPUNum++;
                        int index = 0;
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            try {
                                long tempJiffies = Long.parseLong(matcher.group(0).trim());
                                totalJiffies[i] += tempJiffies;
                                if (index == 3) {//空闲时间为该行第4条栏目
                                    totalIdle[i] += tempJiffies;
                                }
                                index++;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (i == 0) {
                        firstCPUNum = currentCPUNum;
                        try {//暂停50毫秒，等待系统更新信息。
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        double rate = -1;
        if (totalJiffies[0] > 0 && totalJiffies[1] > 0 && totalJiffies[0] != totalJiffies[1]) {
            rate = 1.0 * ((totalJiffies[1] - totalIdle[1]) - (totalJiffies[0] - totalIdle[0])) / (totalJiffies[1] - totalJiffies[0]);
        }

        Log.d("CpuUtils", "zrx---- cpu_rate:" + rate);
        return String.format("cpu:%.2f", rate);
    }


    private static long cpu0_totalJiffies[] = new long[2];
    public static long cpu1_totalJiffies[] = new long[2];
    private static long cpu2_totalJiffies[] = new long[2];
    private static long cpu3_totalJiffies[] = new long[2];
    private static long cpu0_totalIdle[] = new long[2];
    private static long cpu1_totalIdle[] = new long[2];
    private static long cpu2_totalIdle[] = new long[2];
    private static long cpu3_totalIdle[] = new long[2];

    private static double cpu0_rate;
    private static double cpu1_rate;
    private static double cpu2_rate;
    private static double cpu3_rate;

    public static String getCPURateDesc() {
        String path = "/proc/stat";// 系统CPU信息文件
        //int firstCPUNum=0;//设置这个参数，这要是防止两次读取文件获知的CPU数量不同，导致不能计算。这里统一以第一次的CPU数量为基准
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        Pattern pattern = Pattern.compile(" [0-9]+");
        for (int i = 0; i < 2; i++) {
            cpu0_totalJiffies[i] = 0;
            cpu1_totalJiffies[i] = 0;
            cpu2_totalJiffies[i] = 0;
            cpu3_totalJiffies[i] = 0;

            cpu0_totalIdle[i] = 0;
            cpu1_totalIdle[i] = 0;
            cpu2_totalIdle[i] = 0;
            cpu3_totalIdle[i] = 0;
            try {
                fileReader = new FileReader(path);
                bufferedReader = new BufferedReader(fileReader, 8192);
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    if (str.toLowerCase().startsWith("cpu0")) {
                        int index = 0;
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            try {
                                long tempJiffies = Long.parseLong(matcher.group(0).trim());
                                cpu0_totalJiffies[i] += tempJiffies;
                                if (index == 3) {//空闲时间为该行第4条栏目
                                    cpu0_totalIdle[i] += tempJiffies;
                                }
                                index++;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (str.toLowerCase().startsWith("cpu1")) {
                        int index = 0;
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            try {
                                long tempJiffies = Long.parseLong(matcher.group(0).trim());
                                cpu1_totalJiffies[i] += tempJiffies;
                                if (index == 3) {//空闲时间为该行第4条栏目
                                    cpu1_totalIdle[i] += tempJiffies;
                                }
                                index++;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (str.toLowerCase().startsWith("cpu2")) {
                        int index = 0;
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            try {
                                long tempJiffies = Long.parseLong(matcher.group(0).trim());
                                cpu2_totalJiffies[i] += tempJiffies;
                                if (index == 3) {//空闲时间为该行第4条栏目
                                    cpu2_totalIdle[i] += tempJiffies;
                                }
                                index++;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (str.toLowerCase().startsWith("cpu3")) {
                        int index = 0;
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            try {
                                long tempJiffies = Long.parseLong(matcher.group(0).trim());
                                cpu3_totalJiffies[i] += tempJiffies;
                                if (index == 3) {//空闲时间为该行第4条栏目
                                    cpu3_totalIdle[i] += tempJiffies;
                                }
                                index++;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (i == 0) {
                        try {//暂停50毫秒，等待系统更新信息。
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (cpu0_totalJiffies[0] > 0 && cpu0_totalJiffies[1] > 0 && cpu0_totalJiffies[0] != cpu0_totalJiffies[1]) {
            cpu0_rate = 1.0 * ((cpu0_totalJiffies[1] - cpu0_totalIdle[1]) - (cpu0_totalJiffies[0] - cpu0_totalIdle[0])) / (cpu0_totalJiffies[1] - cpu0_totalJiffies[0]);
        }
        if (cpu1_totalJiffies[0] > 0 && cpu1_totalJiffies[1] > 0 && cpu1_totalJiffies[0] != cpu1_totalJiffies[1]) {
            cpu1_rate = 1.0 * ((cpu1_totalJiffies[1] - cpu1_totalIdle[1]) - (cpu1_totalJiffies[0] - cpu1_totalIdle[0])) / (cpu1_totalJiffies[1] - cpu1_totalJiffies[0]);
        }
        if (cpu2_totalJiffies[0] > 0 && cpu2_totalJiffies[1] > 0 && cpu2_totalJiffies[0] != cpu2_totalJiffies[1]) {
            cpu2_rate = 1.0 * ((cpu2_totalJiffies[1] - cpu2_totalIdle[1]) - (cpu2_totalJiffies[0] - cpu2_totalIdle[0])) / (cpu2_totalJiffies[1] - cpu2_totalJiffies[0]);
        }
        if (cpu3_totalJiffies[0] > 0 && cpu3_totalJiffies[1] > 0 && cpu3_totalJiffies[0] != cpu3_totalJiffies[1]) {
            cpu3_rate = 1.0 * ((cpu3_totalJiffies[1] - cpu3_totalIdle[1]) - (cpu3_totalJiffies[0] - cpu3_totalIdle[0])) / (cpu3_totalJiffies[1] - cpu3_totalJiffies[0]);
        }

//          return String.format("cpu:%.2f",rate);
//        Log.d("CpuUtils","zrx---- cpu0_rate:"+cpu0_rate+", cpu1_rate:"+cpu1_rate+", cpu2_rate:"+cpu2_rate+", cpu3_rate:"+cpu3_rate);
        return ("\ncpu0_rate:" + cpu0_rate + ", \ncpu1_rate:" + cpu1_rate + ", \ncpu2_rate:" + cpu2_rate + ", \ncpu3_rate:" + cpu3_rate);
    }

}
