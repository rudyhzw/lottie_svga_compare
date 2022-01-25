package com.example.myapplication.utils;


import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：1.0
 * 创建日期：16/10/27
 * 描    述: 文件工具类
 * 类    型:
 * 修订历史：
 * ================================================
 */

public class FileSizeUtil {
    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath) {
        long blockSize = 0;
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            Log.e("TestLottie",e.toString());
        }
        return blockSize;
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            Log.e("TestLottie",e.toString());
            Log.e("TestLottie","获取文件大小,获取失败!");
        }
        return formatFileSize(blockSize);
    }


    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            file.createNewFile();
            Log.d("TestLottie","获取文件大小,文件不存在!");
        }

        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File[] flist = f.listFiles();
        if (flist == null) return 0;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String formatFileSize(double fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        try {
            if (fileS < 1024) {
                fileSizeString = df.format(fileS) + "B";
            } else if (fileS < 1048576) {
                fileSizeString = df.format(fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                fileSizeString = df.format(fileS / 1048576) + "MB";
            } else {
                fileSizeString = df.format(fileS / 1073741824) + "GB";
            }
        } catch (Exception e) {

        }
        return fileSizeString;
    }

    public static String formatFileSizeMB(double fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0MB";
        if (fileS < 1048576) {
            return wrongSize;
        }
        try {
            fileSizeString = df.format(fileS / 1048576) + "MB";
        } catch (Exception e) {

        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    public static double formatFileSize(double fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        try {
            switch (sizeType) {
                case SIZETYPE_B:
                    fileSizeLong = Double.parseDouble(df.format(fileS));
                    break;
                case SIZETYPE_KB:
                    fileSizeLong = Double.parseDouble(df.format(fileS / 1024));
                    break;
                case SIZETYPE_MB:
                    fileSizeLong = Double.parseDouble(df.format(fileS / 1048576));
                    break;
                case SIZETYPE_GB:
                    fileSizeLong = Double.parseDouble(df.format(fileS / 1073741824));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {

        }
        return fileSizeLong;
    }
}
