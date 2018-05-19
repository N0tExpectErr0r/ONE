package com.nullptr.one.util;

import com.nullptr.one.bean.Article;
import com.nullptr.one.bean.ArticleDetail;
import com.nullptr.one.bean.Author;
import com.nullptr.one.bean.Movie;
import com.nullptr.one.bean.MovieDetail;
import com.nullptr.one.bean.MovieInfo;
import com.nullptr.one.bean.Music;
import com.nullptr.one.bean.MusicDetail;
import com.nullptr.one.bean.Singer;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/12
 * @DESCRIPTION 解析Json工具类
 */
public class JsonUtil {

    //Json解析为文章列表
    public static List<Article> parseJsonToArticleList(String json) {
        List<Article> articleList = null;
        try {
            //最外层 获取文章对象
            JSONObject jsonObject = new JSONObject(json);
            articleList = new ArrayList<>();
            //第二层 文章列表解析
            JSONArray articleListArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < articleListArray.length(); i++) {
                JSONObject articleObject = articleListArray.getJSONObject(i);

                Article article = new Article();
                article.setId(articleObject.getString("id"));
                article.setItemId(articleObject.getString("item_id"));
                article.setTitle(articleObject.getString("title"));
                article.setForward(articleObject.getString("forward"));
                String imgUrl = articleObject.getString("img_url");
                article.setImage(ImageUtil.getImageBitmap(imgUrl));
                article.setDate(articleObject.getString("last_update_date"));
                article.setLikeCount(articleObject.getInt("like_count"));

                //第三层 作者解析
                JSONObject authorObject = articleObject.getJSONObject("author");

                Author author = new Author();
                author.setName(authorObject.getString("user_name"));
                author.setSummary(authorObject.getString("summary"));

                article.setAuthor(author);
                articleList.add(article);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articleList;
    }

    //Json解析为文章详情
    public static ArticleDetail parseJsonToArticleDetail(String json) {
        ArticleDetail article = null;

        try {
            //第一层
            JSONObject jsonObject = new JSONObject(json);
            //第二层
            article = new ArticleDetail();
            JSONObject articleObject = jsonObject.getJSONObject("data");

            article.setContentId(articleObject.getString("content_id"));
            article.setTitle(articleObject.getString("hp_title"));
            article.setAuthorName(articleObject.getString("hp_author"));
            article.setAuthorDesc(articleObject.getString("auth_it"));
            article.setContent(articleObject.getString("hp_content"));
            article.setDate(articleObject.getString("last_update_date"));
            article.setCopyright(articleObject.getString("copyright"));
            article.setCommentCount(articleObject.getInt("commentnum"));
            article.setPreviousId(articleObject.getString("previous_id"));
            article.setNextId(articleObject.getString("next_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return article;
    }

    //Json解析为音乐列表
    public static List<Music> parseJsonToMusicList(String json) {
        List<Music> musicList = null;
        try {
            //最外层 获取音乐对象
            JSONObject jsonObject = new JSONObject(json);
            musicList = new ArrayList<>();
            //第二层 音乐列表解析
            JSONArray musicListArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < musicListArray.length(); i++) {
                JSONObject musicObject = musicListArray.getJSONObject(i);

                Music music = new Music();
                music.setId(musicObject.getString("id"));
                music.setItemId(musicObject.getString("item_id"));
                music.setTitle(musicObject.getString("title"));
                music.setForward(musicObject.getString("forward"));
                String imgUrl = musicObject.getString("img_url");
                music.setImage(ImageUtil.getImageBitmap(imgUrl));

                //第三层 歌手解析
                JSONObject singerObject = musicObject.getJSONObject("author");

                Singer singer = new Singer();
                singer.setName(singerObject.getString("user_name"));
                singer.setDesc(singerObject.getString("desc"));

                music.setSinger(singer);
                musicList.add(music);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return musicList;
    }

    //Json解析为音乐详情
    public static MusicDetail parseJsonToMusicDetail(String json) {
        MusicDetail music = null;

        try {
            //第一层
            JSONObject jsonObject = new JSONObject(json);
            //第二层
            music = new MusicDetail();
            JSONObject musicObject = jsonObject.getJSONObject("data");

            music.setId(musicObject.getString("id"));
            music.setTitle(musicObject.getString("title"));
            String coverUrl = musicObject.getString("cover");
            music.setCover(ImageUtil.getImageBitmap(coverUrl));
            music.setStorySummary(musicObject.getString("story_summary"));
            music.setStory(musicObject.getString("story"));
            music.setStoryTitle(musicObject.getString("story_title"));
            music.setLyric(musicObject.getString("lyric"));
            music.setDate(musicObject.getString("last_update_date"));
            music.setInfo(musicObject.getString("info"));
            music.setCommentNum(musicObject.getInt("commentnum"));
            //第三层
            JSONObject authorObject = musicObject.getJSONObject("story_author");
            Author author = new Author();
            author.setName(authorObject.getString("user_name"));
            author.setSummary(authorObject.getString("summary"));

            music.setAuthor(author);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return music;
    }

    //Json解析为影视列表
    public static List<Movie> parseJsonToMovieList(String json) {
        List<Movie> movieList = null;
        try {
            //最外层 获取影视对象
            JSONObject jsonObject = new JSONObject(json);
            movieList = new ArrayList<>();
            //第二层 影视列表解析
            JSONArray movieListArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < movieListArray.length(); i++) {
                JSONObject movieObject = movieListArray.getJSONObject(i);

                Movie movie = new Movie();
                movie.setId(movieObject.getString("id"));
                movie.setItemId(movieObject.getString("item_id"));
                movie.setTitle(movieObject.getString("title"));
                movie.setForward(movieObject.getString("forward"));
                String imgUrl = movieObject.getString("img_url");
                movie.setImage(ImageUtil.getImageBitmap(imgUrl));
                movie.setSubTitle(movieObject.getString("subtitle"));
                movie.setDate(movieObject.getString("last_update_date"));

                //第三层 作者解析
                JSONObject authorObject = movieObject.getJSONObject("author");

                Author author = new Author();
                author.setName(authorObject.getString("user_name"));
                author.setSummary(authorObject.getString("summary"));

                movie.setAuthor(author);
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    //Json解析为影视详情
    public static MovieDetail parseJsonToMovieDetail(String json) {
        MovieDetail movieDetail = null;

        try {
            //第一层
            JSONObject jsonObject = new JSONObject(json);
            //第二层
            JSONObject newJsonObject = jsonObject.getJSONObject("data");
            //第三层
            movieDetail = new MovieDetail();
            JSONObject movieObject = newJsonObject.getJSONArray("data").getJSONObject(0);
            movieDetail.setMovieId(movieObject.getString("movie_id"));
            movieDetail.setTitle(movieObject.getString("title"));
            movieDetail.setContent(movieObject.getString("content"));
            //第四层
            JSONObject authorObject = movieObject.getJSONObject("user");
            Author author = new Author();
            author.setName(authorObject.getString("user_name"));
            author.setSummary(authorObject.getString("summary"));
            movieDetail.setAuthor(author);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieDetail;
    }

    //Json解析为影视信息
    public static MovieInfo parseJsonToMovieInfo(String json) {
        MovieInfo movieInfo = null;

        try {
            //第一层
            JSONObject jsonObject = new JSONObject(json);
            //第二层
            movieInfo = new MovieInfo();
            JSONObject movieObject = jsonObject.getJSONObject("data");
            movieInfo.setMovieTitle(movieObject.getString("title"));
            movieInfo.setInfo(movieObject.getString("info"));
            movieInfo.setMovieId(movieObject.getString("id"));
            movieInfo.setStory(movieObject.getString("officialstory"));
            String imgUrl = movieObject.getString("detailcover");
            movieInfo.setCover(ImageUtil.getImageBitmap(imgUrl));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieInfo;
    }
}
