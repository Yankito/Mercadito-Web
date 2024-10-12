package com.example.mercadito_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadito_android.ui.API.ApiClient;
import com.example.mercadito_android.ui.API.ApiService;
import com.example.mercadito_android.ui.catalog.ProductAdapter;
import com.example.mercadito_android.ui.models.Order;
import com.example.mercadito_android.ui.models.Product;
import com.example.mercadito_android.ui.shoppingCart.ProductCartAdapter;
import com.example.mercadito_android.ui.shoppingCart.ShoppingCart;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private ProductCartAdapter cartAdapter;
    private Button btnBuy;
    private static TextView textViewTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        textViewTotal = findViewById(R.id.textViewTotal);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        updateTotal();
        cartAdapter = new ProductCartAdapter();
        recyclerViewCart.setAdapter(cartAdapter);
        btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(v -> {
            performPurchase();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void updateTotal() {
        int total=0;
        for (Product product : ShoppingCart.getInstance().getCartProducts()) {
            total += product.getPrice();
        }
        textViewTotal.setText("Total: " + formatPrice(total));
    }

    public static String formatPrice(int price) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CL"));
        symbols.setCurrencySymbol("$");
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        String pattern = "$#,##0";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        return decimalFormat.format(price);
    }

    private void performPurchase() {
        if(ShoppingCart.getInstance().getCartSize()==0){
            Intent intent = new Intent(CartActivity.this, CatalogActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Long accountId = CatalogActivity.getAccountId();
        List<Long> productIds = ShoppingCart.getInstance().getCartProductIds();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        String timestamp = sdf.format(new Date());
        Order order = new Order(accountId, productIds, timestamp);

        ApiService apiService = ApiClient.getOrder().create(ApiService.class);
        Call<Void> call = apiService.purchase(order);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CartActivity.this, "Compra realizada exitósamente", Toast.LENGTH_SHORT).show();
                    ShoppingCart.getInstance().deleteCart();
                    Intent intent = new Intent(CartActivity.this, CatalogActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CartActivity.this, "Error al realizar la compra", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("CartActivity", "Error en la compra: " + t.getMessage());
                Toast.makeText(CartActivity.this, "Error en la compra", Toast.LENGTH_SHORT).show();
            }
        });
    }
}