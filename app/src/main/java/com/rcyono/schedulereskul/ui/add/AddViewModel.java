package com.rcyono.schedulereskul.ui.add;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rcyono.schedulereskul.model.schedule.Schedule;
import com.rcyono.schedulereskul.model.schedule.ScheduleResponse;
import com.rcyono.schedulereskul.model.type.TypeResponse;
import com.rcyono.schedulereskul.network.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddViewModel extends ViewModel {

    private final static String TAG = AddViewModel.class.getSimpleName();

    private final MutableLiveData<TypeResponse> mTypeResponse = new MutableLiveData<>();
    public LiveData<TypeResponse> getType() {
        return mTypeResponse;
    }

    private final MutableLiveData<ScheduleResponse> mScheduleResponse = new MutableLiveData<>();
    public LiveData<ScheduleResponse> addSchedule() {
        return mScheduleResponse;
    }

    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public AddViewModel() {
        getTypeEskul();
    }

    private void getTypeEskul() {
        mIsLoading.setValue(true);
        Call<TypeResponse> client = ApiConfig.getApiService().getTypeEskul();
        client.enqueue(new Callback<TypeResponse>() {
            @Override
            public void onResponse(@NonNull Call<TypeResponse> call, @NonNull Response<TypeResponse> response) {
                mIsLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mTypeResponse.setValue(response.body());
                    }
                }else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeResponse> call, @NonNull Throwable t) {
                mIsLoading.setValue(false);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void addSchedule(Schedule schedule) {
        mIsLoading.setValue(true);
        Call<ScheduleResponse> client = ApiConfig.getApiService().addSchedule(
                schedule.getIdUser(),
                schedule.getTitleType(),
                schedule.getDesc(),
                schedule.getPlace(),
                schedule.getDate(),
                schedule.getTimeStart(),
                schedule.getTimeEnd()
        );
        client.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleResponse> call, @NonNull Response<ScheduleResponse> response) {
                mIsLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mScheduleResponse.setValue(response.body());
                    }
                }else {
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

    public void updateSchedule(Schedule schedule) {
        mIsLoading.setValue(true);
        Call<ScheduleResponse> client = ApiConfig.getApiService().updateSchedule(
                schedule.getId(),
                schedule.getIdUser(),
                schedule.getTitleType(),
                schedule.getDesc(),
                schedule.getPlace(),
                schedule.getDate(),
                schedule.getTimeStart(),
                schedule.getTimeEnd()
        );
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

    public void deleteSchedule(String id) {
        mIsLoading.setValue(true);
        Call<ScheduleResponse> client = ApiConfig.getApiService().deleteSchedule(id);
        client.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScheduleResponse> call, @NonNull Response<ScheduleResponse> response) {
                mIsLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mScheduleResponse.setValue(response.body());
                    }
                }
                else {
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
