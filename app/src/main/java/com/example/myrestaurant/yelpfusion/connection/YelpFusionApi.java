package com.example.myrestaurant.yelpfusion.connection;

import com.example.myrestaurant.yelpfusion.models.AutoComplete;
import com.example.myrestaurant.yelpfusion.models.Business;
import com.example.myrestaurant.yelpfusion.models.Reviews;
import com.example.myrestaurant.yelpfusion.models.SearchResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Ranga on 2/22/2017.
 */

public interface YelpFusionApi {
    @GET("/v3/businesses/search")
    Call<SearchResponse> getBusinessSearch(@QueryMap Map<String, String> params);

    @GET("/v3/businesses/search/phone")
    Call<SearchResponse> getPhoneSearch(@Query("phone") String phone);

    @GET("/v3/Transactions/{transaction_type}/search")
    Call<SearchResponse> getTransactionSearch(@Path("transaction_type") String transactionType, @QueryMap Map<String, String> params);

    @GET("/v3/businesses/{id}")
    Call<Business> getBusiness(@Path("id") String id);

    @GET("/v3/businesses/{id}/reviews")
    Call<Reviews> getBusinessReviews(@Path("id") String id, @Query("locale") String locale);

    @GET("/v3/autocomplete")
    Call<AutoComplete> getAutocomplete(@QueryMap Map<String, String> params);

}

