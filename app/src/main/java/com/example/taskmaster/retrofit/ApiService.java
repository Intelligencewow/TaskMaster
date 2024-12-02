package com.example.taskmaster.retrofit;
import com.example.taskmaster.model.LoginResponse;
import com.example.taskmaster.model.Task;
import com.example.taskmaster.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("users/create")
    Call<User> createUser(@Body User user);

    @POST("users/login")
    Call<LoginResponse> login(@Body User loginRequest);

    @GET("users/1")
    Call<User> getUser();

    @GET("tasks/all")
    Call<List<Task>> getAllTasks();
}
