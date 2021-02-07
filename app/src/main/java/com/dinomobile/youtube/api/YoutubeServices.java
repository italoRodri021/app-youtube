package com.dinomobile.youtube.api;

import com.dinomobile.youtube.model.json.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeServices {

    /** URL DA API
     * https://www.googleapis.com/youtube/v3/
     * search?part=snippet
     * &order=date
     * &maxResults=20
     * &key=AIzaSyAyqdd_30UOe811PLTgaWPNjLQuKYPqg-U
     * &channelId=UCEI44xNfQmAukxMf1kW8d5g
     * &q=pesquisa
     */

    @GET("search")
    Call<Result> getMoviesChannel(@Query("part") String part,
                                  @Query("order") String order,
                                  @Query("maxResults") String maxResults,
                                  @Query("key") String key,
                                  @Query("channelId") String channelId,
                                  @Query("q") String q);

}
