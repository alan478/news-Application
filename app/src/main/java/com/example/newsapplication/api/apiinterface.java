package com.example.newsapplication.api;

import com.example.newsapplication.models.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apiinterface {


    @GET("top-headlines")
    Call<news> getNews(
      @Query("country") String country,
      @Query("apiKey") String apiKey




    );
}





