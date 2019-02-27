package com.leo.libcputype;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Create by MaZaizhong
 * on 2019/2/26
 * at 下午2:13
 * so文件下载保存工具
 */
public final class SoUtil {
    private SoUtil() {
    }


    /**
     * 判断 so 文件是否存在
     *
     * @return fileName so 文件名（包括.so 扩展名）
     */
    public static boolean isSoFileExists(Context context, @NonNull String fileName) {
        File dir = context.getDir("libs", Context.MODE_PRIVATE);
        File soFile = new File(dir, fileName);
        return soFile.exists() && soFile.isFile() && soFile.length() > 0;
    }

    /**
     * 下载文件到/data/data/PackageName/app_libs下面
     *
     * @param context  上下文
     * @param urlPath  下载链接
     * @param fileName so 文件名（包括.so 扩展名）
     * @return
     */
    public static File downloadSoToLib(Context context, @NonNull String urlPath, @NonNull String fileName) {
        FileOutputStream outStream = null;
        File soFile = null;
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        try {
            //使用该地址创建一个 URL 对象
            URL url = new URL(urlPath);
            //使用创建的URL对象的openConnection()方法创建一个HttpURLConnection对象
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为 GET 请求
            httpURLConnection.setRequestMethod("GET");
            //使用输入流
            httpURLConnection.setDoInput(true);
            //GET 方式，不需要使用输出流
            httpURLConnection.setDoOutput(false);
            //设置超时
            httpURLConnection.setConnectTimeout(30 * 1000);
            httpURLConnection.setReadTimeout(30 * 1000);
            //连接
            httpURLConnection.connect();
            //还有很多参数设置 请自行查阅
            //连接后，创建一个输入流来读取response
            if (httpURLConnection.getResponseCode() == 200) {
                is = httpURLConnection.getInputStream();
                if (is != null) {
                    File dir = context.getDir("libs", Context.MODE_PRIVATE);
                    soFile = new File(dir, fileName);
                    outStream = new FileOutputStream(soFile);
                    byte[] buf = new byte[1024];
                    int ch = -1;
                    while ((ch = is.read(buf)) > 0) {
                        outStream.write(buf, 0, ch);
                    }
                    outStream.flush();
                }
            }
            return soFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpURLConnection) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
}
