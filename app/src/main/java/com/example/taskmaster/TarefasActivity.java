package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.model.Task;
import com.example.taskmaster.retrofit.ApiService;
import com.example.taskmaster.retrofit.RetrofitClient;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarefasActivity extends AppCompatActivity {
    private final List<Task> taskList = new ArrayList<>();
    private final ActivityResultLauncher<Intent> addTaskLauncher =
            registerForActivityResult(new ActivityResultContract<Intent, Task>() {

                @NonNull
                @Override
                public Intent createIntent(@NonNull Context context, Intent input) {
                    return input;
                }

                @Override
                public Task parseResult(int resultCode, @Nullable Intent intent) {
                    if (resultCode == RESULT_OK && intent != null) {
                        if (intent.hasExtra("editedTask")) {
                            return (Task) intent.getSerializableExtra("editedTask");
                        } else {
                            return (Task) intent.getSerializableExtra("newTask");
                        }
                    }
                    return null;
                }

            }, new ActivityResultCallback<Task>() {
                @Override
                public void onActivityResult(Task task) {

                    Log.i("TAG", "onActivityResult: ");
                }
            });
    ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarefas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tvNovasTarefas = findViewById(R.id.tvNovaTarefa);

        getAllTasks();

        tvNovasTarefas.setOnClickListener(v -> {
            Intent intent = new Intent(this, TaskActivity.class);
            addTaskLauncher.launch(intent);

        });
    }

    public void getAllTasks() {

        apiService.getAllTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {

                    taskList.addAll(response.body());
                    Log.i("MainActivity", "PASSEI AQUI: ");

                    setRecyclerView();
                    Log.i("MainActivity", "OnResposta: " + response);
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
            public void onFailure(Call<List<Task>> call, Throwable throwable) {
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


    public void setRecyclerView() {
        Log.i("MainActivity", "PASSEI AQUI: ");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.i("MainActivity", "setRecyclerView: " + taskList.size());
        for (int i = 0; i < taskList.size(); i++) {
            Log.i("MainActivity", "task: " + taskList.get(i).getTitle());
        }
        taskAdapter = new TaskAdapter(this, taskList);
        recyclerView.setAdapter(taskAdapter);
    }
}