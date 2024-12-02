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

import com.example.taskmaster.model.Task;
import com.example.taskmaster.retrofit.ApiService;
import com.example.taskmaster.retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {
    ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView salvar = findViewById(R.id.tvSalvar);
        TextInputEditText title = findViewById(R.id.titulo);
        TextInputEditText categoria = findViewById(R.id.categoria);
        TextInputEditText prioridade = findViewById(R.id.prioridade);
        TextInputEditText description = findViewById(R.id.descricao);


        salvar.setOnClickListener(v -> {
        task = new Task(title.getText().toString(),description.getText().toString());
            createTask();
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    public void createTask(){
        apiService.createTask(task).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
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
            public void onFailure(Call<String> call, Throwable throwable) {
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
    }
}