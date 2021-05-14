package com.ratnasagar.acquireassignment.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ratnasagar.acquireassignment.model.Photo;
import com.ratnasagar.acquireassignment.repository.DataRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private Context context;

    private MutableLiveData<List<Photo>> mutableLiveData;

    private DataRepository dataRepository;
    public MainActivityViewModel(Context context) {
        this.context = context;
        dataRepository=DataRepository.getInstance();
    }

   public MutableLiveData<List<Photo>> getUserList()
    {
        if (mutableLiveData == null) {
            mutableLiveData=new MutableLiveData<>();
        }
        mutableLiveData = dataRepository.getUserList();
        return mutableLiveData;
    }
}
