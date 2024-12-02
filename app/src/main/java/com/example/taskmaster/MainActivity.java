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

import com.example.taskmaster.model.LoginResponse;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputEditText email = findViewById(R.id.email);
        TextInputEditText senha = findViewById(R.id.senha);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);




        TextView enter = findViewById(R.id.enter);
        TextView register = findViewById(R.id.register);

        enter.setOnClickListener(v -> {
        User user = new User(email.getText().toString(),  senha.getText().toString());
        email.setText("");
        senha.setText("");
            apiService.login(user).enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();

                        if (loginResponse.getStatus() == 1) {
                            Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        Log.i("Alisson", "Requisição bem-sucedida: " + response.body().getStatus());
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
                public void onFailure(Call<LoginResponse> call, Throwable throwable) {

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
            });
        });

        register.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });


    }
}