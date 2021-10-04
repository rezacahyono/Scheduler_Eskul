package com.rcyono.schedulereskul.ui.detailevent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.db.entity.FavoriteEvent;
import com.rcyono.schedulereskul.model.event.Event;
import com.rcyono.schedulereskul.utils.ViewModelFactory;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailEventFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView tvTitle, tvDesc;
    private ImageView ivEvent;
    private ImageButton btnFav;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton fabAdd;

    private Event event;
    private DetailEventViewModel detailEventViewModel;
    private int isFav = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_event, container, false);
        toolbar = view.findViewById(R.id.main_toolbar);
        tvTitle = view.findViewById(R.id.tv_title_event);
        tvDesc = view.findViewById(R.id.tv_desc);
        ivEvent = view.findViewById(R.id.iv_event);
        btnFav = view.findViewById(R.id.btn_delete);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        btnFav.setVisibility(View.VISIBLE);
        btnFav.setImageResource(R.drawable.ic_favorite_border);

        btnFav.setOnClickListener(this);

        if (getParentFragment() != null) {
            bottomAppBar = getParentFragment().requireActivity().findViewById(R.id.layout_bottom_app_bar);
            fabAdd = getParentFragment().requireActivity().findViewById(R.id.fab_add);
            bottomAppBar.setVisibility(View.GONE);
            fabAdd.setVisibility(View.GONE);
        }

        detailEventViewModel = obtainViewModel(requireActivity());

        event = DetailEventFragmentArgs.fromBundle(getArguments()).getEvent();
        if (event != null) {
            Glide.with(view.getContext())
                    .load(event.getImagePath())
                    .into(ivEvent);

            tvTitle.setText(event.getTitle());
            tvDesc.setText(event.getDesc());

            detailEventViewModel.checkIsFav(Integer.parseInt(event.getId())).observe(requireActivity(), value -> {
                setDrawableFav(value);
                isFav = value;
                Log.d("TAG", "onViewCreated: " + value);
            });
        }

    }

    @NonNull
    private static DetailEventViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(DetailEventViewModel.class);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_delete) {
            FavoriteEvent favoriteEvent = new FavoriteEvent();
            favoriteEvent.setId(Integer.parseInt(event.getId()));
            favoriteEvent.setImagePath(event.getImagePath());
            favoriteEvent.setTitle(event.getTitle());
            favoriteEvent.setDesc(event.getDesc());
            String titleDialog;
            if (isFav == 1) {
                detailEventViewModel.delete(favoriteEvent);
                titleDialog = "Success delete from Favorite";
            } else {
                detailEventViewModel.insertEvent(favoriteEvent);
                titleDialog = "Success Add to favorite";
            }
            SweetAlertDialog alertDialog = new SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setTitleText(titleDialog);
            alertDialog.setContentText("next to be continue");
            alertDialog.show();
            alertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setVisibility(View.GONE);
        }
    }

    private void setDrawableFav(int isFav) {
        if (isFav >= 1) {
            btnFav.setImageResource(R.drawable.ic_favorite);
        } else {
            btnFav.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bottomAppBar.setVisibility(View.VISIBLE);
        fabAdd.setVisibility(View.VISIBLE);
    }

}