package com.example.retrofitv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.retrofitv1.models.Article;
import com.example.retrofitv1.models.ArticleListResponse;
import com.example.retrofitv1.models.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArticleAdapter adapter;
    ArticleService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ArticleAdapter(this, new ArrayList<>(), new ArticleAdapter.OnArticleClicked() {
            @Override
            public void onClicked(String articleID) {
                Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                intent.putExtra("articleID", articleID);
                startActivity(intent);
            }

            @Override
            public void onLongClicked(Article article) {
                Call<Void> call = service.deleteArticle(article.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        fetchDataFromAPI();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
//                article.setTitle("New Title");
//                Call<Void> call = service.updateArticle(article, article.getId());
//                call.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        Toast.makeText(MainActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//
//                    }
//                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://110.74.194.124:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        service = retrofit.create(ArticleService.class);
        fetchDataFromAPI();
    }

    private void fetchDataFromAPI() {
        Call<ArticleListResponse> responseCall = service.getAllArticles();
        responseCall.enqueue(new Callback<ArticleListResponse>() {
            @Override
            public void onResponse(Call<ArticleListResponse> call, Response<ArticleListResponse> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onResponse: "+ response.body().getArticleList().size());
                setDatatoRecyclerView(response.body().getArticleList());
            }

            @Override
            public void onFailure(Call<ArticleListResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
           Call<ArticleResponse> responseCall = service.addArticle(new Article("NEW POST", "NEW DETAIL", "https://m.media-amazon.com/images/M/MV5BOGE4NzU1YTAtNzA3Mi00ZTA2LTg2YmYtMDJmMThiMjlkYjg2XkEyXkFqcGdeQXVyNTgzMDMzMTg@._V1_.jpg"));
           handleOnAddedArticle(responseCall);
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleOnAddedArticle(Call<ArticleResponse> responseCall) {
        responseCall.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                Log.d("TAG", "addArticle: "+ response.body().getArticle().toString());
                fetchDataFromAPI();
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }
        });
    }

    private void setDatatoRecyclerView(List<Article> articleList) {
        adapter.setData(articleList);
    }
}