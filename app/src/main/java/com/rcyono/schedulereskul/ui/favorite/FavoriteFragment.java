package com.rcyono.schedulereskul.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.event.Event;
import com.rcyono.schedulereskul.ui.collection.ListItemEventAdapter;
import com.rcyono.schedulereskul.utils.ViewModelFactory;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    private Toolbar toolbar;
    private ImageView ivEmptyFav;
    private TextView tvEmptyFav;
    private RecyclerView rvFavorite;
    private ListItemEventAdapter listItemEventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        toolbar = view.findViewById(R.id.main_toolbar);
        ivEmptyFav = view.findViewById(R.id.iv_empty_fav);
        tvEmptyFav = view.findViewById(R.id.tv_title_empty_fav);
        rvFavorite = view.findViewById(R.id.rv_favorite);
        toolbar.setTitle(getString(R.string.favorite));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setActionBar(toolbar);

        listItemEventAdapter = new ListItemEventAdapter(1);

        FavoriteViewModel favoriteViewModel = obtainViewModel(requireActivity());
        favoriteViewModel.getEventFromDb().observe(requireActivity(), eventFav -> {
            ArrayList<Event> arrayList = new ArrayList<>();
            for (int i = 0; i < eventFav.size(); i++) {
                Event event = new Event();
                event.setId(String.valueOf(eventFav.get(i).getId()));
                event.setImagePath(eventFav.get(i).getImagePath());
                event.setTitle(eventFav.get(i).getTitle());
                event.setDesc(eventFav.get(i).getDesc());
                arrayList.add(event);
            }
            listItemEventAdapter.setAdapter(arrayList);
            if (arrayList.size() > 0) {
                rvFavorite.setVisibility(View.VISIBLE);
                ivEmptyFav.setVisibility(View.GONE);
                tvEmptyFav.setVisibility(View.GONE);

            } else {
                rvFavorite.setVisibility(View.GONE);
                ivEmptyFav.setVisibility(View.VISIBLE);
                tvEmptyFav.setVisibility(View.VISIBLE);
            }
        });

        setupRecylerEvent();
    }

    @NonNull
    private static FavoriteViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(FavoriteViewModel.class);
    }

    private void setupRecylerEvent() {
        rvFavorite.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setAdapter(listItemEventAdapter);
    }
}