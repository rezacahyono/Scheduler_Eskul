package com.rcyono.schedulereskul.ui.favorite;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rcyono.schedulereskul.db.EventRepository;
import com.rcyono.schedulereskul.db.entity.FavoriteEvent;

import java.util.List;

public class FavoriteViewModel extends ViewModel {
    private final EventRepository eventRepository;
    public FavoriteViewModel(Application application) {
        eventRepository = new EventRepository(application);
    }

    public LiveData<List<FavoriteEvent>> getEventFromDb() {
        return eventRepository.getEventFromDb();
    }
}
