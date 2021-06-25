package com.capstone.JobR.category.consult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsultViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConsultViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("고민/상담 페이지");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

