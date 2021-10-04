package com.rcyono.schedulereskul.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.user.User;
import com.rcyono.schedulereskul.preferences.AppPreferences;

public class ProfileFragment extends Fragment {
    private Toolbar toolbar;
    private User user;
    private ImageView ivAvatar;
    private TextView tvName, tvEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = view.findViewById(R.id.main_toolbar);
        toolbar.setTitle("Profile");
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setActionBar(toolbar);
        AppPreferences preferences = new AppPreferences(requireActivity());
        if (preferences.getUser() != null) {
            user = preferences.getUser();

            Glide.with(view)
                    .load(user.getImagePath())
                    .into(ivAvatar);
            tvName.setText(user.getUsername());
            tvEmail.setText(user.getEmail());
        }

    }
}