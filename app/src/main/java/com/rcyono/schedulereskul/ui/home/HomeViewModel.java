package com.rcyono.schedulereskul.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rcyono.schedulereskul.model.event.EventResponse;
import com.rcyono.schedulereskul.model.schedule.ScheduleResponse;
import com.rcyono.schedulereskul.network.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private final static String TAG = HomeViewModel.class.getSimpleName();

    private final MutableLiveData<EventResponse> mEventResponse = new MutableLiveData<>();

    public LiveData<EventResponse> getEvent() {
        return mEventResponse;
    }

    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    private final MutableLiveData<ScheduleResponse> mScheduleResponse = new MutableLiveData<>();

    public LiveData<ScheduleResponse> getSchedule() {
        return mScheduleResponse;
    }

    public HomeViewModel() {
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

    public void getAllSchedule(String id) {
        mIsLoading.setValue(true);
        Call<ScheduleResponse> client = ApiConfig.getApiService().getAllSchedule(id);
        client.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleResponse> call, @NonNull Response<ScheduleResponse> response) {
                mIsLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mScheduleResponse.setValue(response.body());
                    }
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ScheduleResponse> call, @NonNull Throwable t) {
                mIsLoading.setValue(false);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
