package com.rcyono.schedulereskul.ui.bottommenunav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rcyono.schedulereskul.R;


public class BottomNavFragment extends Fragment {
    private BottomNavigationView navView;
    private FloatingActionButton fabAdd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = inflater.inflate(R.layout.fragment_bottom_nav, container, false);
        navView = view.findViewById(R.id.bottom_navigation_view);
        fabAdd = view.findViewById(R.id.fab_add);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navView.setBackground(null);
        navView.getMenu().getItem(2).setEnabled(false);
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragment_container);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);


        fabAdd.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_bottomNavFragment_to_addSchedulerFragment));
    }
}