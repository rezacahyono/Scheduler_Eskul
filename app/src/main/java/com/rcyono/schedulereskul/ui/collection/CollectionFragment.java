package com.rcyono.schedulereskul.ui.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.ui.home.HomeViewModel;


public class CollectionFragment extends Fragment {
    private RecyclerView rvEvent;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    private ListItemEventAdapter listItemEventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        rvEvent = view.findViewById(R.id.rv_event);
        toolbar = view.findViewById(R.id.main_toolbar);
        progressBar = view.findViewById(R.id.progress_bar);
        toolbar.setTitle("Add Schedule");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setActionBar(toolbar);

        listItemEventAdapter = new ListItemEventAdapter(0);

        HomeViewModel collectionViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        collectionViewModel.getEvent().observe(requireActivity(), event -> {
            listItemEventAdapter.setAdapter(event.getImage());
        });

        collectionViewModel.isLoading().observe(requireActivity(), load -> {
            if (load) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        setupRecylerEvent();
    }

    private void setupRecylerEvent() {
        rvEvent.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvEvent.setHasFixedSize(true);
        rvEvent.setAdapter(listItemEventAdapter);
    }

}