package com.example.mercadito_android;

import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadito_android.ui.catalog.CatalogProducts;
import com.example.mercadito_android.ui.models.Order;
import com.example.mercadito_android.ui.models.Product;
import com.example.mercadito_android.ui.orderHistory.ProductOrderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDetailsActivity extends AppCompatActivity {
    private static final String TAG = "OrderDetailsActivity";
    private TextView textOrderDate;
    private TextView textOrderTotalPrice;
    private RecyclerView recyclerViewProducts;
    private ProductOrderAdapter productOrderAdapter;

    private static Order order;

    public static void setOrder(Order order){
        OrderDetailsActivity.order = order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textOrderDate = findViewById(R.id.textOrderDate);
        textOrderTotalPrice = findViewById(R.id.textTotalPrice);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);

        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        productOrderAdapter = new ProductOrderAdapter(new ArrayList<>());
        recyclerViewProducts.setAdapter(productOrderAdapter);

        if (order != null) {
            displayOrderDetails();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void displayOrderDetails() {
        textOrderDate.setText("Fecha de orden: " + order.getTimestamp());

        if (order.getProductsIds() != null) {
            List<Product> productList = new ArrayList<>();
            for (Long id: order.getProductsIds()){
                for (Product product: CatalogProducts.getInstance().getProducts()){
                    if(Objects.equals(product.getId(), id)){
                        productList.add(product);
                        break;
                    }
                }
            }
            textOrderTotalPrice.setText("Precio total: "+CartActivity.formatPrice(calculateTotalPrice(productList)));
            productOrderAdapter.updateProductIds(productList);
        }
    }

    private int calculateTotalPrice(List<Product> productList){
        int price =0;
        for(Product product: productList)
            price+=product.getPrice();
        return  price;
    }
}
