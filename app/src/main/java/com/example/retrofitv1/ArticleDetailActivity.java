package com.example.retrofitv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retrofitv1.models.Article;
import com.example.retrofitv1.models.ArticleListResponse;
import com.example.retrofitv1.models.ArticleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleDetailActivity extends AppCompatActivity {
    static final String TAG = "ArticleDetailActivity";
    ImageView imageView;
    TextView title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        String articleID = getIntent().getStringExtra("articleID");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://110.74.194.124:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ArticleService service = retrofit.create(ArticleService.class);

        Call<ArticleResponse> responseCall = service.getArticleByID(articleID);
        responseCall.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                Log.d(TAG, "onResponse: "+ response.body().getArticle().toString());
                setDataToUI(response.body().getArticle());
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }

    private void setDataToUI(Article article) {
        Glide.with(this).load(article.getImage()).into(imageView);
        title.setText(article.getTitle());
        description.setText(article.getDescription());
    }
}