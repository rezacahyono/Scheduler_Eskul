package com.rcyono.schedulereskul.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rcyono.schedulereskul.db.entity.FavoriteEvent;

import java.util.List;

@Dao
interface FavoriteEventDao {

    @Query("SELECT * FROM favoriteevent ORDER BY id ASC")
    LiveData<List<FavoriteEvent>> getFavEvents();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavEvent(FavoriteEvent favoriteEvent);

    @Delete
    void deleteFavEvent(FavoriteEvent favoriteEvent);

}
