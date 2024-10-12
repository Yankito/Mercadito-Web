package com.example.mercadito_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mercadito_android.ui.catalog.CatalogProducts;
import com.example.mercadito_android.ui.catalog.ProductAdapter;
import com.example.mercadito_android.ui.models.Product;
import com.example.mercadito_android.ui.API.ApiClient;
import com.example.mercadito_android.ui.API.ApiService;

import com.example.mercadito_android.ui.models.Profile;
import com.example.mercadito_android.ui.shoppingCart.ShoppingCart;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "CatalogActivity";
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private static Profile profile = new Profile();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static TextView textTotalItems;
    private CatalogProducts catalogProducts = CatalogProducts.getInstance();

    public static void setAccount(Long accountId) {
        CatalogActivity.profile.setAccounId(accountId);
    }

    public static Long getAccountId(){
        return CatalogActivity.profile.getAccount().getId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton btnCart = toolbar.findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        textTotalItems = findViewById(R.id.textCartItemCount);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        Log.d(TAG, "onCreate: Activity started");
        recyclerView = findViewById(R.id.recyclerViewCatalog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);

        loadProducts();
        getProfile();
        updateCart();
    }



    private void getProfile() {
        ApiService apiService = ApiClient.getIdentity().create(ApiService.class);
        Call<Profile> call = apiService.getProfile(profile.accountId());

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    profile = response.body();
                    updateProfileUI(profile);
                } else {
                    Log.e(TAG, "Error al obtener perfil: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e(TAG, "Error al obtener perfil: " + t.getMessage());
            }
        });
    }

    private void loadProducts() {
        ApiService apiService = ApiClient.getCatalog().create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    catalogProducts.setProductList(response.body());
                    productAdapter.notifyDataSetChanged();
                    Toast.makeText(CatalogActivity.this, "Productos cargados.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Products loaded successfully.");
                } else {
                    Log.e(TAG, "Failed to load products. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "Failed to load products: " + t.getMessage());
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            Intent intent = new Intent(this, OrderHistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            ShoppingCart shoppingCart = ShoppingCart.getInstance();
            shoppingCart.deleteCart();
            Intent logoutIntent = new Intent(this, LoginActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logoutIntent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void updateCart() {
        textTotalItems.setText(ShoppingCart.getInstance().getCartSize()+"");
    }



    private void updateProfileUI(Profile profile) {
        View headerView = navigationView.getHeaderView(0);
        TextView textProfileName = headerView.findViewById(R.id.textProfileName);
        textProfileName.setText(profile.getName());

        ImageView imageProfile = headerView.findViewById(R.id.imageProfile);
        imageProfile.setImageResource(R.drawable.default_profile_image);
    }

}