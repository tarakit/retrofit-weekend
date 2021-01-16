package com.example.retrofitv1;

import com.example.retrofitv1.models.Article;
import com.example.retrofitv1.models.ArticleListResponse;
import com.example.retrofitv1.models.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ArticleService {

    @GET("/api/articles")
    Call<ArticleListResponse> getAllArticles();

    @GET("/api/articles/{id}")
    Call<ArticleResponse> getArticleByID(@Path("id") String articleID);

    @POST("/api/articles")
    Call<ArticleResponse> addArticle(@Body Article article);

    @PATCH("/api/articles/{id}")
    Call<Void> updateArticle(@Body Article article, @Path("id") String id);

    @DELETE("/api/articles/{id}")
    Call<Void> deleteArticle(@Path("id") String id);

}
