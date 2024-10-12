package com.example.mercadito_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadito_android.ui.models.Order;
import com.example.mercadito_android.ui.API.ApiClient;
import com.example.mercadito_android.ui.API.ApiService;
import com.example.mercadito_android.ui.orderHistory.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity implements OrderAdapter.OnOrderClickListener {

    private static final String TAG = "OrderHistoryActivity";
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        orderAdapter.setListener(this);
        recyclerView.setAdapter(orderAdapter);

        loadOrders();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadOrders() {
        ApiService apiService = ApiClient.getOrder().create(ApiService.class);
        Long accountId = CatalogActivity.getAccountId();
        Call<List<Order>> call = apiService.getOrders(accountId);

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderList.addAll(response.body());
                    orderAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Failed to load orders. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e(TAG, "Failed to load orders: " + t.getMessage());
            }
        });
    }

    @Override
    public void onOrderClick(Order order) {
        showOrderDetails(order);
    }

    private void showOrderDetails(Order order) {
        OrderDetailsActivity.setOrder(order);
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        startActivity(intent);
    }
}