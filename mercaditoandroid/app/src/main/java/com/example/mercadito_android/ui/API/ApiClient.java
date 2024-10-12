package com.example.mercadito_android.ui.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofitIdentity = null;
    private static Retrofit retrofitCatalog = null;
    private static Retrofit retrofitOrder = null;

    public static Retrofit getIdentity() {
        if (retrofitIdentity == null) {
            retrofitIdentity = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.3:8081/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitIdentity;
    }

    public static Retrofit getCatalog() {
        if (retrofitCatalog == null) {
            retrofitCatalog = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.3:8082/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitCatalog;
    }

    public static Retrofit getOrder() {
        if (retrofitOrder == null) {
            retrofitOrder = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.3:8083/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitOrder;
    }


}