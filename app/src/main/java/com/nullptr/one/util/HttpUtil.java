package com.nullptr.one.util;

import com.nullptr.one.bean.URLData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/10
 * @DESCRIPTION Http工具类
 */
public class HttpUtil {

    public static void sendHttpRequest(final String urlKey, final String param,
            final OnRequestListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    listener.onStart();
                    URLData data = URLManager.getURL(urlKey);
                    if (data == null) {
                        throw new Exception("urlKey不正确，找不到url");
                    }
                    String address = data.getUrl();
                    if (param != null) {
                        address = address.replace("{id}", param);   //替换网址
                    }

                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(data.getNetType());

                    //设置连接超时的时间
                    connection.setConnectTimeout(data.getExpires());
                    connection.setReadTimeout(data.getExpires());

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
        }).start();
    }


}
