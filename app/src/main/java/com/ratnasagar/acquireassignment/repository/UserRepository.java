package com.ratnasagar.acquireassignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.ratnasagar.acquireassignment.model.Photo;
import com.ratnasagar.acquireassignment.model.User;
import com.ratnasagar.acquireassignment.retrofit.ApiClient;
import com.ratnasagar.acquireassignment.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private static UserRepository dataRepository;

    public static UserRepository getInstance() {
        if (dataRepository == null) {
            dataRepository = new UserRepository();
        }
        return dataRepository;
    }

    private ApiClient apiClient;

    public UserRepository() {
        apiClient = RetrofitService.cteateService(ApiClient.class);
    }

    public MutableLiveData<List<User>> getUserList() {
        MutableLiveData<List<User>> userData = new MutableLiveData<>();
        apiClient.getUserData().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call,
                                   Response<List<User>> response) {
                if (response.isSuccessful()) {

                    if (response.body()!=null)
                    {
                        userData.setValue(response.body());
                    }
                    else
                    {
                        userData.setValue(null);
                    }
                }
                else
                {
                    userData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

}
