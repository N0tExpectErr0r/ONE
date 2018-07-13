package com.nullptr.one.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.R;
import com.nullptr.one.main.view.MainActivity;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/13
 * @DESCRIPTION
 */
public class NotificationUtil {
    private final static int ARTICLE_ID = 1;
    private final static int MUSIC_ID = 2;
    private final static int MOVIE_ID = 3;
    private static NotificationManager mManager;

    public static void showArticleNotification(String title,String content){
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_article";
            NotificationChannel channel = mManager.getNotificationChannel(channelId);
            if (channel == null) {
                channel = new NotificationChannel(channelId, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("description");
                mManager.createNotificationChannel(channel);
            }

            builder = new NotificationCompat.Builder(ContextApplication.getContext(),channelId)
                    .setContentTitle("ONE · 一个")
                    .setContentText("最新文章推荐")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setStyle(style)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
        }else {
            builder = new NotificationCompat.Builder(ContextApplication.getContext())
                    .setContentTitle("ONE · 一个")
                    .setContentText("最新文章推荐")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setStyle(style)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
        }

        mManager.notify(ARTICLE_ID, builder.build());
    }

    public static void showMusicNotification(String title,String content){
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_music";
            NotificationChannel channel = mManager.getNotificationChannel(channelId);
            if (channel == null) {
                channel = new NotificationChannel(channelId, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("description");
                mManager.createNotificationChannel(channel);
            }

            builder = new NotificationCompat.Builder(ContextApplication.getContext(),channelId)
                    .setContentTitle("ONE · 一个")
                    .setContentText("最新乐评推荐")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setStyle(style)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
        }else {
            builder = new NotificationCompat.Builder(ContextApplication.getContext())
                    .setContentTitle("ONE · 一个")
                    .setContentText("最新乐评推荐")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setStyle(style)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
        }

        mManager.notify(MUSIC_ID, builder.build());
    }

    public static void showMovieNotification(String title,String content){
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_movie";
            NotificationChannel channel = mManager.getNotificationChannel(channelId);
            if (channel == null) {
                channel = new NotificationChannel(channelId, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("description");
                mManager.createNotificationChannel(channel);
            }

            builder = new NotificationCompat.Builder(ContextApplication.getContext(),channelId)
                    .setContentTitle("ONE · 一个")
                    .setContentText("最新影评推荐")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setStyle(style)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
        }else {
            builder = new NotificationCompat.Builder(ContextApplication.getContext())
                    .setContentTitle("ONE · 一个")
                    .setContentText("最新影评推荐")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .setStyle(style)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
        }

        mManager.notify(MOVIE_ID, builder.build());
    }
}
