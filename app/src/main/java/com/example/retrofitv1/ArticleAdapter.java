package com.example.retrofitv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitv1.models.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    Context context;
    List<Article> articles;
    OnArticleClicked onArticleClicked;

    public ArticleAdapter(Context context, List<Article> articles, OnArticleClicked onArticleClicked) {
        this.context = context;
        this.articles = articles;
        this.onArticleClicked = onArticleClicked;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context)
                .inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        Glide.with(context)
                .load(article.getImage())
                .into(holder.image);

        holder.image.setOnClickListener(view -> {
            onArticleClicked.onClicked(article.getId());
        });

        holder.image.setOnLongClickListener(view -> {
            onArticleClicked.onLongClicked(article);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setData(List<Article> articleList) {
        this.articles = articleList;
        notifyDataSetChanged();
    }

    public void newArticle(Article article){
        this.articles.add(article);
        notifyItemChanged(0);
    }

    class ArticleViewHolder extends  RecyclerView.ViewHolder{

        ImageView image;
        TextView title;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }
    }

    public interface OnArticleClicked{
        void onClicked(String articleID);
        void onLongClicked(Article article);
    }
}














