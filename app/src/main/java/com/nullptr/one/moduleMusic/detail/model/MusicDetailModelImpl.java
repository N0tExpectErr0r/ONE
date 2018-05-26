package com.nullptr.one.moduleMusic.detail.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.bean.Author;
import com.nullptr.one.bean.MusicDetail;
import com.nullptr.one.moduleMusic.detail.IMusicDetail.MusicDetailModel;
import com.nullptr.one.moduleMusic.detail.IMusicDetail.OnMusicDetailListener;
import com.nullptr.one.moduleMusic.detail.db.MusicBaseHelper;
import com.nullptr.one.moduleMusic.detail.db.MusicDbSchema.MusicTable;
import com.nullptr.one.moduleMusic.detail.db.MusicDbSchema.MusicTable.Cols;
import com.nullptr.one.util.HttpUtil;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.OnRequestListener;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicDetailModel接口实现类
 */
public class MusicDetailModelImpl implements MusicDetailModel {

    private SQLiteDatabase mDatabase;   //本地缓存音乐详情的数据库

    public MusicDetailModelImpl() {
        mDatabase = new MusicBaseHelper(ContextApplication.getContext()).getWritableDatabase();
    }

    private static ContentValues getContentValues(MusicDetail music) {
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

    @Override
    public void getMusicDetail(final OnMusicDetailListener onMusicDetailListener,
            final String itemId) {
        final Cursor cursor = mDatabase.query(MusicTable.NAME, null, "item_id = ?",
                new String[]{itemId}, null, null, null);
        if (cursor.getCount() > 1) {
            //如果已经有数据了，直接读取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cursor.moveToFirst();

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

                    onMusicDetailListener.onSuccess(music);
                    onMusicDetailListener.onFinish();
                }
            }).start();
        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            StringBuilder url = new StringBuilder();
            url.append("http://v3.wufazhuce.com:8000/api/music/detail/")
                    .append(itemId)
                    .append("?version=3.5.0&platform=android");
            HttpUtil.sendHttpRequest(url.toString(), new OnRequestListener() {
                @Override
                public void onResponse(String response) {
                    //将该音乐详情存入数据库
                    MusicDetail musicDetail = JsonUtil.parseJsonToMusicDetail(response);
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
    }
}
