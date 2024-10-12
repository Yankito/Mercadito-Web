package com.example.mercadito_android.ui.orderHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadito_android.CartActivity;
import com.example.mercadito_android.CatalogActivity;
import com.example.mercadito_android.R;
import com.example.mercadito_android.ui.models.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

//Muestra el id del producto en el cada orden
public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ViewHolder> {

    private List<Product> products;

    public ProductOrderAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product productId = products.get(position);
        holder.bind(productId);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProductIds(List<Product> newProduct) {
        products.clear();
        products.addAll(newProduct);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textPrice;
        ImageView imageProduct;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textTitle = itemView.findViewById(R.id.textTitle);
            textPrice = itemView.findViewById(R.id.textPrice);
        }

        void bind(Product product) {
            textTitle.setText(product.getTitle());
            textPrice.setText("Precio: " + CartActivity.formatPrice(product.getPrice()));

            Picasso.get()
                    .load(product.getImage())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(imageProduct);
        }


    }
}
