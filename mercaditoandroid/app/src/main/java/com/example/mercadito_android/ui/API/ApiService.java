package com.example.mercadito_android.ui.API;

import com.example.mercadito_android.ui.models.Account;
import com.example.mercadito_android.ui.models.Order;
import com.example.mercadito_android.ui.models.Product;
import com.example.mercadito_android.ui.models.Profile;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("account/login")
    Call<Map<String, Long>> login(@Body LoginRequest loginRequest);

    @POST("account/register")
    Call<Account> register(@Body RegisterRequest registerRequest);

    @GET("product")
    Call<List<Product>> getProducts();

    @GET("purchase/oneproduct")
    Call<Product> getProductById(@Query("id") Long productId);

    @GET("profile")
    Call<Profile> getProfile(@Query("accountId") Long accountId);

    @POST("purchase")
    Call<Void> purchase(@Body Order order);

    @GET("purchase")
    Call<List<Order>> getOrders(@Query("accountId") Long accountId);





}
