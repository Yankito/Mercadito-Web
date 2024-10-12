package com.example.mercadito_android.ui.shoppingCart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Locale;

//Maneja elementos gráficos de un producto en el carro de compras
public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {

    private ShoppingCart shoppingCart = ShoppingCart.getInstance();

    public ProductCartAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = shoppingCart.getCartProducts().get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return shoppingCart.getCartSize();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textTitle;
        TextView textPrice;
        Button buttonRemoveFromCart;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProductCart);
            textTitle = itemView.findViewById(R.id.textTitleCart);
            textPrice = itemView.findViewById(R.id.textPriceCart);
            buttonRemoveFromCart = itemView.findViewById(R.id.buttonRemoveFromCart);
        }

        void bind(Product product) {
            textTitle.setText(product.getTitle());
            textPrice.setText("Precio: " + formatPrice(product.getPrice()));

            Picasso.get()
                    .load(product.getImage())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(imageProduct);

            buttonRemoveFromCart.setOnClickListener(v -> {
                removeFromCart(product);
            });
        }
        private void removeFromCart(Product product) {
            shoppingCart.removeProduct(product);
            notifyDataSetChanged();
            CartActivity.updateTotal();
            CatalogActivity.updateCart();
        }
    }

    private String formatPrice(int price) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "CL"));
        symbols.setCurrencySymbol("$");
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        String pattern = "$#,##0";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        return decimalFormat.format(price);
    }


}