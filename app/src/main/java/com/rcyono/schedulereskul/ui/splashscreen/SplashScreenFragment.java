package com.rcyono.schedulereskul.ui.splashscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.preferences.AppPreferences;

@SuppressLint("CustomSplashScreen")
public class SplashScreenFragment extends Fragment {
    private final static Long TIME_SPLASH = 2000L;
    private AppPreferences preferences;
    private boolean isFragmentVisible = false;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences = new AppPreferences(requireActivity());
        if (preferences.getDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (isFragmentVisible) {
                if (!preferences.getUser().getUsername().isEmpty()) {
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_bottomNavFragment);
                }else {
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_signInFragment);
                }
            }
        }, TIME_SPLASH);

    }

    @Override
    public void onResume() {
        super.onResume();
        isFragmentVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentVisible = false;
    }
}