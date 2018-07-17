package com.nullptr.one.util;

import com.nullptr.one.net.Request;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/17
 * @DESCRIPTION
 */
public class UrlUtil {

    public static String getArticleListUrl(String id){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/reading/more/")
                .append(id)
                .append("?channel=wdj&version=4.0.2&platform=android");
        return url.toString();
    }

    public static String getArticleDetailUrl(String itemId){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/essay/")
                .append(itemId)
                .append("?platform=android");
        return url.toString();
    }

    public static String getCommentListUrl(String commentType,String itemId){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/comment/praiseandtime/")
                .append(commentType)
                .append("/" + itemId)
                .append("/0?&platform=android");
        return url.toString();
    }

    public static String getImageIdUrl(String id){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/hp/idlist/")
                .append(id)
                .append("?version=3.5.0&platform=android");
        return url.toString();
    }

    public static String getImageDetailUrl(String imageId){
        StringBuilder imageUrl = new StringBuilder();
        imageUrl.append("http://v3.wufazhuce.com:8000/api/hp/detail/")
                .append(imageId)
                .append("?version=3.5.0&platform=android");
        return imageUrl.toString();
    }

    public static String getMovieDetailUrl(String itemId){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/movie/")
                .append(itemId)
                .append("/story/1/0?platform=android");
        return url.toString();
    }

    public static String getMovieInfoUrl(String itemId){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/movie/detail/")
                .append(itemId)
                .append("?platform=android");
        return url.toString();
    }

    public static String getMovieListUrl(String id){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/movie/more/")
                .append(id)
                .append("?platform=android");
        return url.toString();
    }

    public static String getMusicDetailUrl(String itemId){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/music/detail/")
                .append(itemId)
                .append("?version=3.5.0&platform=android");
        return url.toString();
    }

    public static String getMusicListUrl(String id){
        StringBuilder url = new StringBuilder();
        url.append("http://v3.wufazhuce.com:8000/api/channel/music/more/")
                .append(id)
                .append("?platform=android");
        return url.toString();
    }
}
