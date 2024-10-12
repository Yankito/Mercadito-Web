package com.example.mercadito_android.ui.shoppingCart;


import android.view.View;
import android.widget.TextView;

import com.example.mercadito_android.R;
import com.example.mercadito_android.ui.models.Product;

import java.util.ArrayList;
import java.util.List;

//Maneja el carro de compras en la sesión
public class ShoppingCart {
    private static ShoppingCart instance;
    private List<Product> cartItems;

    private ShoppingCart() {
        cartItems = new ArrayList<>();
    }

    public static synchronized ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void addProduct(Product product) {
        cartItems.add(product);
    }

    public List<Product> getCartProducts() {
        return cartItems;
    }

    public int getCartSize() {
        return cartItems.size();
    }
    public void removeProduct(Product product) {
        cartItems.remove(product);
    }

    public List<Long> getCartProductIds(){
        List<Long> idList = new ArrayList<>();
        for(Product product: cartItems){
            idList.add(product.getId());
        }
        return idList;
    }

    public void deleteCart(){
        cartItems = new ArrayList<>();
    }
}
