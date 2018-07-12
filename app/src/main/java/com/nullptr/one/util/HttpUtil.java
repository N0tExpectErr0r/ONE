package com.nullptr.one.util;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/10
 * @DESCRIPTION Http工具类
 */
public class HttpUtil {

    private static ExecutorService sThreadPool = Executors.newFixedThreadPool(10);

    public static void sendHttpRequest(final String url, final OnRequestListener listener) {
        sThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    listener.onStart();
                    String address = url;
                    String chromeUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-agent",chromeUserAgent);
                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    if (listener != null) {
                        //回调OnResponse方法
                        listener.onResponse(response.toString());
                        listener.onFinish();
                    }
                } catch (ProtocolException e) {
                    listener.onError("网络出现错误");
                } catch (MalformedURLException e) {
                    listener.onError("网络出现错误");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
    }



}
