package com.example.challenge.Utils;

import com.example.challenge.DataModels.Feed;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("71a2f8af")
    Observable<Feed> getFeed();


}
