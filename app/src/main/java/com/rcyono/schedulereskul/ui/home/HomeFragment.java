package com.rcyono.schedulereskul.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.schedule.Schedule;
import com.rcyono.schedulereskul.model.user.User;
import com.rcyono.schedulereskul.preferences.AppPreferences;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener{
    private RecyclerView rvSchedule;
    private ImageView ivAvatar;
    private ProgressBar progressBar;
    private ImageButton btnSetting;

    private SliderView sliderEventView;
    private SliderImageEventAdapter sliderImageEventAdapter;

    private ListItemSchedulerAdapter listItemSchedulerAdapter;

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderEventView = view.findViewById(R.id.auto_slider_event);
        rvSchedule = view.findViewById(R.id.rv_schedule);
        ivAvatar = view.findViewById(R.id.iv_avatar);
        progressBar = view.findViewById(R.id.progress_bar);
        btnSetting = view.findViewById(R.id.btn_setting);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sliderImageEventAdapter = new SliderImageEventAdapter();
        listItemSchedulerAdapter = new ListItemSchedulerAdapter();

        AppPreferences preferences = new AppPreferences(requireActivity());
        user = preferences.getUser();

        btnSetting.setOnClickListener(this);

        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        homeViewModel.getEvent().observe(requireActivity(), event -> {
            sliderImageEventAdapter.setAdapter(event.getImage());
            sliderEventView.setSliderAdapter(sliderImageEventAdapter);
        });

        homeViewModel.getAllSchedule(user.getId());
        homeViewModel.getSchedule().observe(requireActivity(), schedule -> {
            if (schedule.getSchedule() != null) {
                Log.d("TAG", "onViewCreated: ");
                listItemSchedulerAdapter.setAdapter((ArrayList<Schedule>) schedule.getSchedule());
            }
        });

        homeViewModel.isLoading().observe(requireActivity(), load -> {
            if (load) {
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }
        });

        setSlideEvent();
        setupRecylerSchedule();
        setAvatar(view);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_setting) {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_settingFragment);
        }
    }

    private void setSlideEvent() {
        sliderEventView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        sliderEventView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderEventView.setIndicatorSelectedColor(getResources().getColor(R.color.blue_main));
        sliderEventView.setIndicatorUnselectedColor(getResources().getColor(R.color.white));
        sliderEventView.startAutoCycle();
        sliderEventView.setOnIndicatorClickListener(position -> sliderEventView.setCurrentPagePosition(position));
    }

    private void setupRecylerSchedule() {
        rvSchedule.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvSchedule.setHasFixedSize(true);
        rvSchedule.setAdapter(listItemSchedulerAdapter);
    }

    private void setAvatar(View view) {
        Glide.with(view.getContext())
                .load(user.getImagePath())
                .into(ivAvatar);
    }

}