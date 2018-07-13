package com.nullptr.one.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import com.nullptr.one.article.list.IArticleList.ArticleListPresenter;
import com.nullptr.one.article.list.IArticleList.ArticleListView;
import com.nullptr.one.article.list.presenter.ArticleListPresenterImpl;
import com.nullptr.one.bean.Article;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.bean.Music;
import com.nullptr.one.movie.list.IMovieList.MovieListPresenter;
import com.nullptr.one.movie.list.IMovieList.MovieListView;
import com.nullptr.one.movie.list.presenter.MovieListPresenterImpl;
import com.nullptr.one.music.list.IMusicList.MusicListPresenter;
import com.nullptr.one.music.list.IMusicList.MusicListView;
import com.nullptr.one.music.list.presenter.MusicListPresenterImpl;
import com.nullptr.one.util.NotificationUtil;
import java.util.List;

public class AutoUpdateService extends Service implements MusicListView,ArticleListView,MovieListView {
    private MusicListPresenter mMusicListPresenter;
    private MovieListPresenter mMovieListPresenter;
    private ArticleListPresenter mArticleListPresenter;

    public AutoUpdateService() {
        mMusicListPresenter = new MusicListPresenterImpl(this);
        mMovieListPresenter = new MovieListPresenterImpl(this);
        mArticleListPresenter = new ArticleListPresenterImpl(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isStart = intent.getBooleanExtra("isStart",false);
        if (!isStart) {
            //不是启动MainActivity时启动的服务才调用
            mArticleListPresenter.updateList();
            mMovieListPresenter.updateList();
            mMusicListPresenter.updateList();
        }
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int loopTime = 8*60*60*1000;   //每8小时刷新一次
        long triggerAtTime = SystemClock.elapsedRealtime()+loopTime;
        Intent newIntent = new Intent(this,AutoUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,newIntent,0);
        manager.cancel(pendingIntent);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void setArticleList(List<Article> articleList) {
        Article firstArticle = articleList.get(0);
        NotificationUtil.showArticleNotification(firstArticle.getTitle(), firstArticle.getForward());
    }

    @Override
    public void addArticleList(List<Article> articleList) {

    }

    @Override
    public void setMovieList(List<Movie> movieList) {
        Movie firstMovie = movieList.get(0);
        NotificationUtil.showMovieNotification(firstMovie.getTitle(), firstMovie.getForward());
    }

    @Override
    public void addMovieList(List<Movie> movieList) {

    }

    @Override
    public void setMusicList(List<Music> musicList) {
        Music firstMusic = musicList.get(0);
        NotificationUtil.showMusicNotification(firstMusic.getTitle(),firstMusic.getForward());
    }

    @Override
    public void addMusicList(List<Music> musicList) {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
