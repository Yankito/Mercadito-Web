package com.example.mercadito_android.ui.models;

public class Profile {
    private Long id;
    private Account account = new Account();
    private String name;
    private String phone;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAccounId(Long accountId){
        this.account.setId(accountId);
    }

    public Long accountId(){
        return this.account.getId();
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
