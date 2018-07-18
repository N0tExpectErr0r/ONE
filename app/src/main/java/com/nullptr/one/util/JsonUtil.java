package com.nullptr.one.util;

import com.google.gson.Gson;
import com.nullptr.one.article.detail.ArticleDetail;
import com.nullptr.one.article.detail.ArticleDetailObject;
import com.nullptr.one.article.list.Article;
import com.nullptr.one.article.list.ArticleObject;
import com.nullptr.one.comment.Comment;
import com.nullptr.one.comment.CommentObject;
import com.nullptr.one.image.ImageDetail;
import com.nullptr.one.image.ImageIdObject;
import com.nullptr.one.image.ImageObject;
import com.nullptr.one.movie.detail.MovieDetail;
import com.nullptr.one.movie.detail.MovieDetailObject;
import com.nullptr.one.movie.detail.MovieInfo;
import com.nullptr.one.movie.detail.MovieInfoObject;
import com.nullptr.one.movie.list.Movie;
import com.nullptr.one.movie.list.MovieObject;
import com.nullptr.one.music.detail.MusicDetail;
import com.nullptr.one.music.detail.MusicDetailObject;
import com.nullptr.one.music.list.Music;
import com.nullptr.one.music.list.MusicObject;
import java.util.List;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 解析Json工具类
 */
public class JsonUtil {

    //Json解析为文章列表
    public static List<Article> parseJsonToArticleList(String json) {
        Gson gson = new Gson();
        ArticleObject articleObject = gson.fromJson(json, ArticleObject.class);
        return articleObject.getArticleList();
    }

    //Json解析为文章详情
    public static ArticleDetail parseJsonToArticleDetail(String json) {
        Gson gson = new Gson();
        ArticleDetailObject articleDetailObject = gson.fromJson(json, ArticleDetailObject.class);
        return articleDetailObject.getArticleDetail();

    }

    //Json解析为音乐列表
    public static List<Music> parseJsonToMusicList(String json) {
        Gson gson = new Gson();
        MusicObject musicObject = gson.fromJson(json, MusicObject.class);
        return musicObject.getMusicList();
    }

    //Json解析为音乐详情
    public static MusicDetail parseJsonToMusicDetail(String json) {
        Gson gson = new Gson();
        MusicDetailObject musicDetailObject = gson.fromJson(json, MusicDetailObject.class);
        return musicDetailObject.getMusicDetail();
    }

    //Json解析为影视列表
    public static List<Movie> parseJsonToMovieList(String json) {
        Gson gson = new Gson();
        MovieObject movieObject = gson.fromJson(json, MovieObject.class);
        return movieObject.getMovieList();
    }

    //Json解析为影视详情
    public static MovieDetail parseJsonToMovieDetail(String json) {
        Gson gson = new Gson();
        MovieDetailObject movieDetailObject = gson.fromJson(json, MovieDetailObject.class);
        return movieDetailObject.getMovieDetailList().getMovieDetail();
    }

    //Json解析为影视信息
    public static MovieInfo parseJsonToMovieInfo(String json) {
        Gson gson = new Gson();
        MovieInfoObject movieInfoObject = gson.fromJson(json, MovieInfoObject.class);
        return movieInfoObject.getMovieInfo();
    }

    //Json解析为ImageIdList
    public static List<String> parseJsonToImageList(String json) {
        Gson gson = new Gson();
        ImageIdObject imageIdObject = gson.fromJson(json, ImageIdObject.class);
        return imageIdObject.getIdList();
    }

    //Json解析为ImageDetail
    public static ImageDetail parseJsonToImageDetail(String json) {
        Gson gson = new Gson();
        ImageObject imageObject = gson.fromJson(json, ImageObject.class);
        return imageObject.getImageDetail();
    }

    //Json解析为Comment
    public static List<Comment> parseJsonToCommentList(String json) {
        Gson gson = new Gson();
        CommentObject commentObject = gson.fromJson(json, CommentObject.class);
        return commentObject.getCommentData().getCommentList();
    }
}
