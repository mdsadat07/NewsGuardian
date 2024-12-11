package com.example.news_guardian;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GuardianApiService {
    @GET("search")
    Call<GuardianResponse> getArticles(
        @Query("api-key") String apiKey,
        @Query("q") String query
    );
}
