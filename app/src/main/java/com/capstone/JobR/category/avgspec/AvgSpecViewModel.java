package com.capstone.JobR.category.avgspec;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AvgSpecViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AvgSpecViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("평균스펙 제공 페이지");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
