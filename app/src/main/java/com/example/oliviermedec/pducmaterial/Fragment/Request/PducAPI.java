package com.example.oliviermedec.pducmaterial.Fragment.Request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oliviermedec on 22/03/2017.
 */

public class PducAPI {
    public static final String BASE_URL = "https://apipduceu.herokuapp.com";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
