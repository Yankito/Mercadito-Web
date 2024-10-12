package com.example.mercadito_android.ui.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadito_android.CartActivity;
import com.example.mercadito_android.CatalogActivity;
import com.example.mercadito_android.R;
import com.example.mercadito_android.ui.models.Product;
import com.example.mercadito_android.ui.models.Profile;
import com.example.mercadito_android.ui.shoppingCart.ShoppingCart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//Clase para manejar los elementos gráficos de cada producto en el catálogo
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private CatalogProducts catalogProducts = CatalogProducts.getInstance();
    private ShoppingCart shoppingCart = ShoppingCart.getInstance();

    public ProductAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = catalogProducts.getProducts().get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return catalogProducts.getProductsSize();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textTitle;
        TextView textPrice;
        Button buttonAddToCart;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textTitle = itemView.findViewById(R.id.textTitle);
            textPrice = itemView.findViewById(R.id.textPrice);
            buttonAddToCart = itemView.findViewById(R.id.buttonAddToCart);
        }

        //Muestra los valores correspondientes de cada producto
        void bind(Product product) {
            textTitle.setText(product.getTitle());
            textPrice.setText("Precio: " + CartActivity.formatPrice(product.getPrice()));

            Picasso.get()
                    .load(product.getImage())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(imageProduct);

            buttonAddToCart.setOnClickListener(v -> {
                addToCart(product);
                Toast.makeText(v.getContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
            });
        }
    }


    private void addToCart(Product product) {
        shoppingCart.addProduct(product);
        CatalogActivity.updateCart();
    }

}