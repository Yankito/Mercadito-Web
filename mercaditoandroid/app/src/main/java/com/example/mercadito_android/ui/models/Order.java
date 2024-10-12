package com.example.mercadito_android.ui.models;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private Long accountId;
    private List<Long> productsIds;
    private String timestamp;

    public Order(Long accountId, List<Long> productsIds, String timestamp) {
        this.accountId = accountId;
        this.productsIds = productsIds;
        this.timestamp = timestamp;
    }

    public List<Long> getProductsIds() {
        return productsIds;
    }

    public void setProductsIds(List<Long> productsIds) {
        this.productsIds = productsIds;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
