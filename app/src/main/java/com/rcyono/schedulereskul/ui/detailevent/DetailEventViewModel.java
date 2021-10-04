package com.rcyono.schedulereskul.ui.detailevent;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rcyono.schedulereskul.db.EventRepository;
import com.rcyono.schedulereskul.db.entity.FavoriteEvent;

import java.util.List;

public class DetailEventViewModel extends ViewModel {
    private final EventRepository eventRepository;
    public DetailEventViewModel(Application application) {
        eventRepository = new EventRepository(application);
    }

    public void insertEvent(FavoriteEvent favoriteEvent) {
        eventRepository.addEvent(favoriteEvent);
    }

    public void delete(FavoriteEvent favoriteEvent) {
        eventRepository.deleteEvent(favoriteEvent );
    }

    public LiveData<Integer> checkIsFav(int id) {
        return eventRepository.checkIsFavorite(id);
    }
}
