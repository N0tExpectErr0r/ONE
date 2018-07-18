package com.nullptr.one.music.list;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nullptr.one.ContextApplication;
import com.nullptr.one.music.list.IMusicList.MusicListModel;
import com.nullptr.one.music.list.IMusicList.OnMoreMusicListener;
import com.nullptr.one.music.list.IMusicList.OnMusicListListener;
import com.nullptr.one.music.list.MusicListDbSchema.MusicListTable;
import com.nullptr.one.music.list.MusicListDbSchema.MusicListTable.Cols;
import com.nullptr.one.net.HttpListener;
import com.nullptr.one.net.Request;
import com.nullptr.one.net.RequestExecutor;
import com.nullptr.one.net.Response;
import com.nullptr.one.util.JsonUtil;
import com.nullptr.one.util.UrlUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Model层
 *
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/13
 * @DESCRIPTION MusicModel实现类
 */
public class MusicListModelImpl implements MusicListModel {

    private SQLiteDatabase mDatabase;   //本地缓存音乐列表的数据库

    public MusicListModelImpl() {
        mDatabase = new MusicListBaseHelper(ContextApplication.getContext()).getWritableDatabase();
    }


    @Override
    public void getList(final OnMusicListListener onMusicListListener) {
        final Cursor cursor = mDatabase.query(MusicListTable.NAME, null, null, null,
                null, null, null);
        if (cursor.getCount() > 0) {
            //如果数据库已经有数据
            getListFromLocal(cursor, onMusicListListener);
        } else {
            //如果数据库没有数据库，向服务器申请数据并存入数据库
            cursor.close();
            getListFromNet(onMusicListListener);
        }
    }

    private void getListFromLocal(final Cursor cursor, final OnMusicListListener onMusicListListener) {
        //耗时操作，在新线程
        cursor.moveToFirst();
        List<Music> musicList = new ArrayList<>();
        while (!cursor.isLast()) {
            musicList.add(getMusic(cursor));
            cursor.moveToNext();
        }
        musicList.add(getMusic(cursor));
        cursor.close();
        onMusicListListener.onSuccess(musicList);
        onMusicListListener.onFinish();
    }

    private Music getMusic(Cursor cursor) {
        Music music = new Music();
        music.setId(cursor.getString(cursor.getColumnIndex(Cols.ID)));
        music.setItemId(cursor.getString(cursor.getColumnIndex(Cols.ITEM_ID)));
        music.setTitle(cursor.getString(cursor.getColumnIndex(Cols.TITLE)));
        music.setForward(cursor.getString(cursor.getColumnIndex(Cols.FORWARD)));
        music.setImageURL(cursor.getString(cursor.getColumnIndex(Cols.IMG_URL)));
        Singer singer = new Singer();
        singer.setName(cursor.getString(cursor.getColumnIndex(Cols.SINGER_NAME)));
        singer.setDesc(cursor.getString(cursor.getColumnIndex(Cols.SINGER_DESC)));
        music.setSinger(singer);
        return music;
    }

    @Override
    public void getListFromNet(final OnMusicListListener onMusicListListener) {
        Request request = new Request(UrlUtil.getMusicListUrl("0"));
        RequestExecutor.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(Response response) {
                List<Music> musicList = JsonUtil.parseJsonToMusicList(response.getResult());
                mDatabase.delete(MusicListTable.NAME, null, null);
                for (Music music : musicList) {
                    mDatabase.insert(MusicListTable.NAME, null, getContentValues(music));
                }
                onMusicListListener.onSuccess(musicList);
            }

            @Override
            public void onError(String errorMsg) {
                onMusicListListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
                onMusicListListener.onStart();
            }

            @Override
            public void onFinish() {
                onMusicListListener.onFinish();
            }
        });
    }

    private ContentValues getContentValues(Music music) {
        ContentValues values = new ContentValues();
        values.put(Cols.ID, music.getId());
        values.put(Cols.ITEM_ID, music.getItemId());
        values.put(Cols.TITLE, music.getTitle());
        values.put(Cols.FORWARD, music.getForward());
        values.put(Cols.IMG_URL, music.getImageURL());
        values.put(Cols.SINGER_NAME, music.getSinger().getName());
        values.put(Cols.SINGER_DESC, music.getSinger().getDesc());

        return values;
    }

    @Override
    public void getMore(final OnMoreMusicListener onMoreMusicListener, String lastId) {

        Request request = new Request(UrlUtil.getMusicListUrl(lastId));
        RequestExecutor.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(Response response) {
                List<Music> musicList = JsonUtil.parseJsonToMusicList(response.getResult());
                onMoreMusicListener.onMoreSuccess(musicList);
            }

            @Override
            public void onError(String errorMsg) {
                onMoreMusicListener.onFail(errorMsg);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }
        });
    }
}
