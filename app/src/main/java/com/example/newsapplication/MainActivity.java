package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.newsapplication.api.Appclient;
import com.example.newsapplication.api.apiinterface;
import com.example.newsapplication.models.Article;
import com.example.newsapplication.models.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY="c1af1d82e3ff49fea7312ecb89f2b6a0";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson();



    }
    public void LoadJson()
    {
        apiinterface apiinterface = Appclient.getAppclient().create(apiinterface.class);
        String country = Utils.getCountry();
        Call<news> call = apiinterface.getNews("US",API_KEY);
        call.enqueue(new Callback<news>() {
            @Override
            public void onResponse(Call<news> call, Response<news> response) {

                if(response.isSuccessful() && response.body().getArticle() != null)
                {
                    if(!articles.isEmpty())
                    {
                        articles.clear();
                    }
                    articles = response.body().getArticle();

                    adapter = new Adapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    initListner();


                }else
                {
                    Toast.makeText(MainActivity.this, "No Result",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<news> call, Throwable t) {

            }
        });
    }

    private void initListner(){
        adapter.setOnItemClickListener(new Adapter.OnItemClickListner() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent= new Intent(MainActivity.this,newsdetails.class);
                Article article = articles.get(position);
                intent.putExtra("url",article.getUrl());
                intent.putExtra("title",article.getTitle());
                intent.putExtra("img",article.getUrlToImage());
                intent.putExtra("date",article.getPublishedAt());
                intent.putExtra("source",article.getSource().getName() );
                intent.putExtra("author",article.getAuthor());
                startActivity(intent);


            }
        });


    }



}
