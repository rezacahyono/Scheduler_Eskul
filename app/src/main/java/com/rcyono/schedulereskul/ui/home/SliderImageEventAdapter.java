package com.rcyono.schedulereskul.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rcyono.schedulereskul.R;
import com.rcyono.schedulereskul.model.event.Event;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderImageEventAdapter extends SliderViewAdapter<SliderImageEventAdapter.SliderImageEventViewHolder> {
    private final ArrayList<Event> listSliderEvent = new ArrayList<Event>();

    public void setAdapter(List<Event> listEvent) {
        if (listEvent.size() > 0) listSliderEvent.clear();
        listSliderEvent.addAll(listEvent);
        notifyDataSetChanged();
    }

    @Override
    public SliderImageEventViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_event, parent, false);
        return new SliderImageEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderImageEventViewHolder viewHolder, int position) {
        viewHolder.bind(listSliderEvent.get(position));
    }

    @Override
    public int getCount() {
        return listSliderEvent.size();
    }

    public static class SliderImageEventViewHolder extends SliderViewAdapter.ViewHolder {
        private final ImageView ivEvent;

        private SliderImageEventViewHolder(View itemView) {
            super(itemView);
            ivEvent = itemView.findViewById(R.id.iv_slider_event);
        }

        private void bind(Event event) {
            Glide.with(itemView.getContext())
                    .load(event.getImagePath())
                    .placeholder(android.R.color.darker_gray)
                    .into(ivEvent);
        }
    }
}