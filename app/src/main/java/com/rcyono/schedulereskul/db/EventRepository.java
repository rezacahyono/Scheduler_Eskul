package com.rcyono.schedulereskul.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rcyono.schedulereskul.db.entity.FavoriteEvent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    private final FavoriteEventDao favoriteEventDao;
    private final ExecutorService executorService;

    public EventRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        FavoriteRoomDatabase db = FavoriteRoomDatabase.getInstance(application);
        favoriteEventDao = db.favoriteEventDao();
    }

    public LiveData<List<FavoriteEvent>> getEventFromDb() {
        return favoriteEventDao.getFavEvents();
    }

    public void addEvent(FavoriteEvent favoriteEvent) {
        executorService.execute(() -> {
            favoriteEventDao.insertFavEvent(favoriteEvent);
        });
    }

    public void deleteEvent(FavoriteEvent favoriteEvent) {
        executorService.execute(() -> {
            favoriteEventDao.deleteFavEvent(favoriteEvent);
        });
    }

    public LiveData<Integer> checkIsFavorite(int id) {
        return favoriteEventDao.checkIsFav(id);
    }
}
