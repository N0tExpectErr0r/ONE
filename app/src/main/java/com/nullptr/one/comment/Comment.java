package com.nullptr.one.comment;

import com.google.gson.annotations.SerializedName;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/18
 * @DESCRIPTION
 */

public class Comment {

    private String quote;
    @SerializedName("content")
    private String comment;
    private User user;
    @SerializedName("touser")
    private User toUser;

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user.getName();
    }

    public void setUser(String user) {
        this.user = new User();
        this.user.setName(user);
    }

    public String getToUser() {
        if (toUser == null) {
            return null;
        } else {
            return toUser.getName();
        }
    }

    public void setToUser(String toUser) {
        this.toUser.setName(toUser);
    }

    public static class User {

        @SerializedName("user_name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

