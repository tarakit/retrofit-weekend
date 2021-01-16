package com.example.retrofitv1.models;

import com.example.retrofitv1.models.Article;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleListResponse {

    @SerializedName("data")
    List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }
}
