package com.example.myrestaurant.yelpfusion.connection;

import com.example.myrestaurant.yelpfusion.connection.interceptors.ApiKeyInterceptor;
import com.example.myrestaurant.yelpfusion.exception.ErrorHandlingInterceptor;
import com.example.myrestaurant.yelpfusion.models.ApiKey;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Ranga on 2/22/2017.
 */

public class YelpFusionApiFactory {
    private static final String HOST = "https://api.yelp.com";

    private OkHttpClient httpClient;
    private ApiKey apiKey;

    public YelpFusionApiFactory() {}

    public YelpFusionApi createAPI(String apiKey) throws IOException {
        this.apiKey = new ApiKey(apiKey, "Bearer");
        return getYelpFusionApi();
    }

    private YelpFusionApi getYelpFusionApi() {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor(apiKey))
                .addInterceptor(new ErrorHandlingInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getAPIBaseUrl())
                .addConverterFactory(getJacksonFactory())
                .client(this.httpClient)
                .build();
        return retrofit.create(YelpFusionApi.class);
    }

    private static JacksonConverterFactory getJacksonFactory(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return JacksonConverterFactory.create(mapper);
    }

    public String getAPIBaseUrl() {
        return HOST;
    }

}

