package com.rcyono.schedulereskul.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.user.User;
import com.rcyono.schedulereskul.preferences.AppPreferences;
import com.rcyono.schedulereskul.ui.MainActivity;


public class SettingFragment extends Fragment {
    private Toolbar toolbar;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton fabAdd;

    private ImageButton btnLanguage;
    private SwitchCompat swDarkMode;
    private TextView btnLogout;
    private AppPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        toolbar = view.findViewById(R.id.main_toolbar);
        btnLanguage = view.findViewById(R.id.btn_language);
        swDarkMode = view.findViewById(R.id.switch_dark_mode);
        btnLogout = view.findViewById(R.id.btn_logout);
        toolbar.setTitle(R.string.setting);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        preferences = new AppPreferences(requireActivity());

        if (getParentFragment() != null) {
            bottomAppBar = getParentFragment().requireActivity().findViewById(R.id.layout_bottom_app_bar);
            fabAdd = getParentFragment().requireActivity().findViewById(R.id.fab_add);
            bottomAppBar.setVisibility(View.GONE);
            fabAdd.setVisibility(View.GONE);
        }

        swDarkMode.setChecked(preferences.getDarkMode());

        btnLogout.setOnClickListener(v -> {
            User user = new User();
            user.setId("");
            user.setUsername("");
            user.setEmail("");
            user.setImagePath("");
            preferences.setUser(user);
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
        });

        btnLanguage.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        });

        swDarkMode.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            preferences.setDarkMode(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bottomAppBar.setVisibility(View.VISIBLE);
        fabAdd.setVisibility(View.VISIBLE);
    }
}