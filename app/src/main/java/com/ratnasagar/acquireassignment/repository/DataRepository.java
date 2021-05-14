package com.ratnasagar.acquireassignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.ratnasagar.acquireassignment.model.Photo;
import com.ratnasagar.acquireassignment.retrofit.ApiClient;
import com.ratnasagar.acquireassignment.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static DataRepository dataRepository;

    public static DataRepository getInstance() {
        if (dataRepository == null) {
            dataRepository = new DataRepository();
        }
        return dataRepository;
    }

    private ApiClient apiClient;

    public DataRepository() {
        apiClient = RetrofitService.cteateService(ApiClient.class);
    }

    public MutableLiveData<List<Photo>> getUserList() {
        MutableLiveData<List<Photo>> newsData = new MutableLiveData<>();
        apiClient.getPhotoData().enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call,
                                   Response<List<Photo>> response) {
                if (response.isSuccessful()) {

                    if (response.body()!=null)
                    {
                        newsData.setValue(response.body());
                    }
                    else
                    {
                        newsData.setValue(null);
                    }
                }
                else
                {
                    newsData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }

}
