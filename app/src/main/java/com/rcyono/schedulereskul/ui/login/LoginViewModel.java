package com.rcyono.schedulereskul.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rcyono.schedulereskul.model.user.User;
import com.rcyono.schedulereskul.model.user.UserResponse;
import com.rcyono.schedulereskul.network.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    private final MutableLiveData<UserResponse> mUserResponse = new MutableLiveData<>();

    public LiveData<UserResponse> getUserResponse() {
        return mUserResponse;
    }

    private final static String TAG = LoginViewModel.class.getSimpleName();

    public LoginViewModel() {
    }

    public void getLogin(User user) {
        mIsLoading.setValue(true);
        Call<UserResponse> client = ApiConfig.getApiService().getUser(user.getUsername(), user.getPassword());
        client.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                mIsLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mUserResponse.setValue(response.body());
                    }
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                mIsLoading.setValue(false);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void registLogin(User user) {
        mIsLoading.setValue(true);
        Call<UserResponse> client = ApiConfig.getApiService().registUser(
                user.getUsername(),
                user.getPassword(),
                user.getEmail());
        client.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                mIsLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mUserResponse.setValue(response.body());

                    }
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                mIsLoading.setValue(false);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
