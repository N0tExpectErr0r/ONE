package com.nullptr.one.net;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION
 */
public class RequestTask implements Runnable {

    private Request request;

    private HttpListener httpListener;

    public RequestTask(Request request, HttpListener httpListener) {
        this.request = request;
        this.httpListener = httpListener;
    }

    @Override
    public void run() {

        Exception exception = null;
        int responseCode = -1;
        Response response = null;

        //1. 建立连接
        httpListener.onStart();
        String urlStr = request.getUrl();
        RequestMethod method = request.getMethod();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //2. 发送数据
            connection.setRequestMethod(request.getMethod().toString());
            switch (request.getMethod()){
                case GET:
                    break;
                case POST:
                    connection.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.connect();
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    for (KeyValue keyValue : request.getKeyValues()) {
                        String param= keyValue.getKey()+URLEncoder.encode(keyValue.getValue().toString(),"UTF-8");
                        outputStream.writeBytes(param);
                    }
                    outputStream.flush();
                    outputStream.close();
                    break;
            }
            InputStream in = connection.getInputStream();
            responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            //3. 读取响应
            StringBuilder responseText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseText.append(line);
            }
            response = new Response(responseCode,responseText.toString(),exception,request);

        } catch (IOException e) {
            exception = e;
            response = new Response(responseCode,"",exception,request);
        }

        //发送响应数据到主线程
        Message message = new Message(response, httpListener);
        Poster.getInstance().post(message);
    }
}
