package com.nullptr.one.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Style;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.R;
import com.nullptr.one.main.view.MainActivity;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/14
 * @DESCRIPTION 由于之前生成通知的重复部分很多，做一个Notification工厂来解决
 */
public class UpdateNotificationFactory {
    public static final int TYPE_ARTICLE = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_MOVIE = 2;
    private static NotificationManager mManager;

    public static Notification createNotification(int type,String title, String content){
        Bitmap icon = BitmapFactory.decodeResource(ContextApplication.getContext().getResources(),R.mipmap.ic_launcher);
        if (mManager == null) {
            mManager = (NotificationManager) ContextApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Intent intent = new Intent(ContextApplication.getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ContextApplication.getContext(), 0, intent, 0);

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle()
                .setBigContentTitle(title)
                .bigText(content);

        NotificationCompat.Builder builder;

        String channelId;
        String contentText;

        switch (type){
            case TYPE_ARTICLE:
                channelId = "channel_article";
                contentText = "最新文章推荐";
                break;
            case TYPE_MUSIC:
                channelId = "channel_article";
                contentText = "最新乐评推荐";
                break;
            case TYPE_MOVIE:
                channelId = "channel_article";
                contentText = "最新影评推荐";
                break;
            default:
                throw new IllegalArgumentException("Wrong type to create Notification");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = mManager.getNotificationChannel(channelId);
            if (channel == null) {
                channel = new NotificationChannel(channelId, "Notification Channel", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Notification Channel");
                mManager.createNotificationChannel(channel);
            }

            builder = configBuilder(new NotificationCompat.Builder(ContextApplication.getContext(),channelId),
                    contentText,style,pendingIntent);

        }else {
            builder = configBuilder(new NotificationCompat.Builder(ContextApplication.getContext()),
                    contentText,style,pendingIntent);
        }

        return builder.build();
    }

    private static NotificationCompat.Builder configBuilder(NotificationCompat.Builder builder,String contentText,Style style,PendingIntent pendingIntent){
        Bitmap icon = BitmapFactory.decodeResource(ContextApplication.getContext().getResources(),R.mipmap.ic_launcher);

        builder.setContentTitle("ONE · 一个")
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setStyle(style)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);
        return builder;
    }
}
