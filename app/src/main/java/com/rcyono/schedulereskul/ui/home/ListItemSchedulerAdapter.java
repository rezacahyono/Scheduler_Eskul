package com.rcyono.schedulereskul.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.schedule.Schedule;

import java.util.ArrayList;

public class ListItemSchedulerAdapter extends RecyclerView.Adapter<ListItemSchedulerAdapter.ListItemSchedulerViewHoler> {
    private final ArrayList<Schedule> listScheduler = new ArrayList<>();

    public void setAdapter(ArrayList<Schedule> schedulers) {
        if (schedulers.size() > 0) listScheduler.clear();
        listScheduler.addAll(schedulers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListItemSchedulerViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_scheduler, parent, false);
        return new ListItemSchedulerViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemSchedulerViewHoler holder, int position) {
        holder.bind(listScheduler.get(position));
        holder.itemView.setOnClickListener(v -> {
            HomeFragmentDirections.ActionHomeFragmentToAddSchedulerFragment toAddSchedulerFragment = HomeFragmentDirections.actionHomeFragmentToAddSchedulerFragment();
            toAddSchedulerFragment.setScheduler(listScheduler.get(position));
            Navigation.findNavController(holder.itemView).navigate(toAddSchedulerFragment);
        });
    }

    @Override
    public int getItemCount() {
        return listScheduler.size();
    }

    public static class ListItemSchedulerViewHoler extends RecyclerView.ViewHolder {
        private final TextView tvType, tvDesc, tvPlace, tvDate, tvTime;
        private final ImageView ivIcon;

        public ListItemSchedulerViewHoler(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_title_type);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvPlace = itemView.findViewById(R.id.tv_place);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivIcon = itemView.findViewById(R.id.iv_icon_type);
        }

        public void bind(Schedule scheduler) {
            Glide.with(itemView.getContext())
                    .load(scheduler.getIconType())
                    .into(ivIcon);
            tvType.setText(scheduler.getTitleType());
            tvDesc.setText(scheduler.getDesc());
            tvPlace.setText(scheduler.getPlace());
            tvDate.setText(scheduler.getDate());
            String time = scheduler.getTimeStart() + "-" + scheduler.getTimeEnd();
            tvTime.setText(time);
        }
    }
}
