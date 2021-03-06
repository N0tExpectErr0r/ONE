package com.nullptr.one.music.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.music.detail.IMusicDetail.MusicDetailModel;
import com.nullptr.one.music.detail.IMusicDetail.OnMusicDetailListener;
import com.nullptr.one.music.detail.MusicDbSchema.MusicTable;
import com.nullptr.one.music.detail.MusicDbSchema.MusicTable.Cols;
import com.nullptr.one.net.HttpListener;
import com.nullptr.one.net.Request;
import com.nullptr.one.net.RequestExecutor;
import com.nullptr.one.net.Response;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.UrlUtil;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicDetailModel接口实现类
 */
public class MusicDetailModelImpl implements MusicDetailModel {

    private SQLiteDatabase mDatabase;   //本地缓存音乐详情的数据库

    MusicDetailModelImpl() {
        mDatabase = new MusicBaseHelper(ContextApplication.getContext()).getWritableDatabase();
    }

    @Override
    public void getMusicDetail(final OnMusicDetailListener onMusicDetailListener, final String itemId) {
        final Cursor cursor = mDatabase.query(MusicTable.NAME, null, "item_id = ?",
                new String[]{itemId}, null, null, null);
        if (cursor.getCount() > 1) {
            //如果已经有数据了，直接读取
            getFromLocal(cursor, onMusicDetailListener);
        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            cursor.close();
            getFromNet(itemId, onMusicDetailListener);
        }
    }

    private void getFromLocal(final Cursor cursor, final OnMusicDetailListener onMusicDetailListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cursor.moveToFirst();

                onMusicDetailListener.onStart();

                MusicDetail music = new MusicDetail();

                music.setId(cursor.getString(cursor.getColumnIndex(Cols.ITEM_ID)));
                music.setTitle(cursor.getString(cursor.getColumnIndex(Cols.TITLE)));
                music.setStorySummary(
                        cursor.getString(cursor.getColumnIndex(Cols.STORY_SUMMARY)));
                music.setCoverURL(cursor.getString(cursor.getColumnIndex(Cols.COVER_URL)));
                music.setStoryTitle(cursor.getString(cursor.getColumnIndex(Cols.STORY_TITLE)));
                music.setStory(cursor.getString(cursor.getColumnIndex(Cols.STORY)));
                music.setLyric(cursor.getString(cursor.getColumnIndex(Cols.LYRIC)));
                music.setInfo(cursor.getString(cursor.getColumnIndex(Cols.INFO)));
                music.setDate(cursor.getString(cursor.getColumnIndex(Cols.DATE)));
                Author author = new Author();
                author.setName(cursor.getString(cursor.getColumnIndex(Cols.AUTHOR_NAME)));
                author.setDesc("");
                music.setAuthor(author);
                cursor.close();

                onMusicDetailListener.onSuccess(music);
                onMusicDetailListener.onFinish();
            }
        }).start();
    }

    private void getFromNet(final String itemId, final OnMusicDetailListener onMusicDetailListener) {

        Request request = new Request(UrlUtil.getMusicDetailUrl(itemId));
        RequestExecutor.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(Response response) {
                //将该音乐详情存入数据库
                MusicDetail musicDetail = JsonUtil.parseJsonToMusicDetail(response.getResult());
                mDatabase.insert(MusicTable.NAME, null, getContentValues(musicDetail));
                onMusicDetailListener.onSuccess(musicDetail);
            }

            @Override
            public void onError(String errorMsg) {
                onMusicDetailListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onMusicDetailListener.onStart();
            }

            @Override
            public void onFinish() {
                onMusicDetailListener.onFinish();
            }
        });
    }

    private ContentValues getContentValues(MusicDetail music) {
        ContentValues values = new ContentValues();
        values.put(Cols.ITEM_ID, music.getId());
        values.put(Cols.TITLE, music.getTitle());
        values.put(Cols.STORY_SUMMARY, music.getStorySummary());
        values.put(Cols.COVER_URL, music.getCoverURL());
        values.put(Cols.STORY_TITLE, music.getStoryTitle());
        values.put(Cols.STORY, music.getStory());
        values.put(Cols.LYRIC, music.getLyric());
        values.put(Cols.INFO, music.getInfo());
        values.put(Cols.DATE, music.getDate());
        values.put(Cols.AUTHOR_NAME, music.getAuthor().getName());

        return values;
    }
}
