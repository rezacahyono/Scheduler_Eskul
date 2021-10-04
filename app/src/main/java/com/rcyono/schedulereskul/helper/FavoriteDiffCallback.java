package com.rcyono.schedulereskul.helper;

import androidx.recyclerview.widget.DiffUtil;

import com.rcyono.schedulereskul.db.entity.FavoriteEvent;

import java.util.List;

public class FavoriteDiffCallback extends DiffUtil.Callback {
    private final List<FavoriteEvent> mOldFavoriteList;
    private final List<FavoriteEvent> mNewFavoriteList;

    public FavoriteDiffCallback(List<FavoriteEvent> mOldFavoriteList, List<FavoriteEvent> mNewFavoriteList) {
        this.mOldFavoriteList = mOldFavoriteList;
        this.mNewFavoriteList = mNewFavoriteList;
    }

    @Override
    public int getOldListSize() {
        return mOldFavoriteList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewFavoriteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldFavoriteList.get(oldItemPosition).getId() == mNewFavoriteList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final FavoriteEvent oldEmployee = mOldFavoriteList.get(oldItemPosition);
        final FavoriteEvent  newEmployee = mNewFavoriteList.get(newItemPosition);
        return oldEmployee.getTitle().equals(newEmployee.getTitle()) && oldEmployee.getDesc().equals(newEmployee.getDesc());
    }
}
