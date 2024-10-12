package com.example.mercadito_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mercadito_android.ui.API.ApiClient;
import com.example.mercadito_android.ui.API.ApiService;
import com.example.mercadito_android.ui.API.RegisterRequest;
import com.example.mercadito_android.ui.models.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();

                if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    registerAccount(email, password, firstName, lastName);
                }
            }
        });
    }

    private void registerAccount(String email, String password, String firstName, String lastName) {
        ApiService apiService = ApiClient.getIdentity().create(ApiService.class);
        RegisterRequest registerRequest = new RegisterRequest(email, password, firstName, lastName);

        Call<Account> call = apiService.register(registerRequest);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registro fallido.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
