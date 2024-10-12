package com.example.mercadito_android.ui.catalog;

import com.example.mercadito_android.ui.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CatalogProducts {
    private static CatalogProducts instance;
    private List<Product> productList;

    private CatalogProducts() {
        productList = new ArrayList<>();
    }

    public static synchronized CatalogProducts getInstance() {
        if (instance == null) {
            instance = new CatalogProducts();
        }
        return instance;
    }

    public void setProductList(List<Product> productList){
        this.productList = productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> getProducts() {
        return productList;
    }

    public int getProductsSize() {
        return productList.size();
    }
    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public List<Long> getCartProductIds(){
        List<Long> idList = new ArrayList<>();
        for(Product product: productList){
            idList.add(product.getId());
        }
        return idList;
    }

    public void deleteList(){
        productList = new ArrayList<>();
    }
}
