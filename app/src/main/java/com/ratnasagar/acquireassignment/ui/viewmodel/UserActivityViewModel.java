package com.ratnasagar.acquireassignment.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ratnasagar.acquireassignment.model.Photo;
import com.ratnasagar.acquireassignment.model.User;
import com.ratnasagar.acquireassignment.repository.DataRepository;
import com.ratnasagar.acquireassignment.repository.UserRepository;

import java.util.List;

public class UserActivityViewModel extends ViewModel {
    private Context context;

    private MutableLiveData<List<User>> mutableLiveData;

    private UserRepository userRepository;
    public UserActivityViewModel(Context context) {
        this.context = context;
        userRepository= UserRepository.getInstance();
    }

   public MutableLiveData<List<User>> getUserList()
    {
        if (mutableLiveData == null) {
            mutableLiveData=new MutableLiveData<>();
        }
        mutableLiveData = userRepository.getUserList();
        return mutableLiveData;
    }
}
