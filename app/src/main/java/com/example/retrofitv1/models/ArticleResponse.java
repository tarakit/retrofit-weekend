package com.example.retrofitv1.models;

import com.google.gson.annotations.SerializedName;

public class ArticleResponse {

    @SerializedName("data")
    Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
