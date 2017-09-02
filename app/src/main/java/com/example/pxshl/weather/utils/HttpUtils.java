package com.example.pxshl.weather.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 该工具类用来发起网络请求，并接受从网络上返回的数据，再通过接口回调法使得调用方可以处理得到的数据
 */
public class HttpUtils {

    //任务完成后，调用方会回调该接口
    public interface HttpCallBackListener
    {
        //返回的结果可能是字符串或者位图
        void onFinish(Object response);
        void onError(Exception e);
    }

    /*得到从网络上返回的数据，并通过接口处理数据*/
    /*耗时任务，在子线程中处理*/
    public static void sendHttpRequestForString(final String httpAddress,final HttpCallBackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(httpAddress);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);  //防止连接超时
                    connection.setReadTimeout(3000);   //防止连接后的等待超时
                    InputStream in = connection.getInputStream(); //输入字节流
                    //先封装成字符流，再封装成缓冲字符流
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    //String对象本身不可变，StringBuilder可变
                    StringBuilder response = new StringBuilder();
                    String line;
                    while (( line = reader.readLine() ) != null){
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if (listener != null) {
                        listener.onError(e);
                    }
                }finally {          //一定要关闭连接
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendHttpRequestForBitmap(final String url,final HttpCallBackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL picUrl = new URL(url);
                    Bitmap response = BitmapFactory.decodeStream(picUrl.openStream());
                    if (listener != null) {
                        listener.onFinish(response);
                    }
                }catch (Exception e)
                {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }

}
