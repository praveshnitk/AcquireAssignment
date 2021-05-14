package com.ratnasagar.acquireassignment.retrofit;

import com.ratnasagar.acquireassignment.model.Photo;
import com.ratnasagar.acquireassignment.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClient {
    @GET("photos")
    Call<List<Photo>> getPhotoData();

    @GET("users")
    Call<List<User>> getUserData();
}