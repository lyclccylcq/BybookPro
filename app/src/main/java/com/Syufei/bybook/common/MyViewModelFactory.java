package com.Syufei.bybook.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Bundle data;//通过bundle传递数据
    public MyViewModelFactory(){}
    public MyViewModelFactory(Bundle data){this.data=data;}
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (BaseViewModel.class.isAssignableFrom(modelClass)){
            return (T) new BaseViewModel(data);
        }
        throw new RuntimeException("unknown class: "+modelClass.getName());
    }
}
