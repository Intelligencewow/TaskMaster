package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import com.example.taskmaster.model.Task;

public class TarefasActivity extends AppCompatActivity {

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

        tvNovasTarefas.setOnClickListener(v -> {
            Intent intent = new Intent(this, TaskActivity.class);
            addTaskLauncher.launch(intent);

        });
    }
}