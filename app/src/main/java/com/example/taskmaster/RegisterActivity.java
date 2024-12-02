package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskmaster.model.User;
import com.example.taskmaster.retrofit.ApiService;
import com.example.taskmaster.retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        TextView register = findViewById(R.id.register);
        TextInputEditText username = findViewById(R.id.username);
        TextInputEditText email = findViewById(R.id.email);
        TextInputEditText password = findViewById(R.id.password);

        User user = new User(username.getText().toString(), email.getText().toString(), password.getText().toString());



        register.setOnClickListener(v -> apiService.createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    Log.i("Alisson", "Requisição bem-sucedida: " + response.body());
                } else {
                    Log.e("Alisson", "Erro na resposta: Código HTTP " + response.code());
                    try {
                        Log.e("Alisson", "Erro na resposta: Corpo " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("Alisson", "Erro ao ler o corpo da resposta: ", e);
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

                if (throwable instanceof ConnectException) {
                    Log.e("Alisson", "Conexão falhou. Verifique se o servidor está ativo e acessível.");
                } else if (throwable instanceof SocketTimeoutException) {
                    Log.e("Alisson", "Conexão expirou. O servidor pode estar demorando para responder.");
                } else if (throwable instanceof UnknownHostException) {
                    Log.e("Alisson", "Host desconhecido. Verifique o endereço IP ou URL.");
                } else {
                    Log.e("Alisson", "Erro inesperado: ", throwable);
                }

                Log.i("Alisson", "Detalhes do erro: ", throwable);

            }
        }));



        register.setOnClickListener(v -> {


            Log.i("Alisson", "onCreate: " + username.getText());
            Log.i("Alisson", "onCreate: " + email.getText());
            Log.i("Alisson", "onCreate: " + password.getText());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        });
    }
}