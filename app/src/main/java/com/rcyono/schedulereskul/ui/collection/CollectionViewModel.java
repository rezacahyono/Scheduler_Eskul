package com.rcyono.schedulereskul.ui.collection;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rcyono.schedulereskul.model.event.EventResponse;
import com.rcyono.schedulereskul.network.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionViewModel extends ViewModel {

    private final static String TAG = CollectionViewModel.class.getSimpleName();
    private final MutableLiveData<EventResponse> mEventResponse = new MutableLiveData<>();

    public LiveData<EventResponse> getEvent() {
        return mEventResponse;
    }

    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public CollectionViewModel() {
        getEventResponse();
    }

    private void getEventResponse() {
        mIsLoading.setValue(true);
        Call<EventResponse> client = ApiConfig.getApiService().getEvent();
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                mIsLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mEventResponse.setValue(response.body());
                    }
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                mIsLoading.setValue(false);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
