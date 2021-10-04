package com.rcyono.schedulereskul.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rcyono.schedulereskul.db.entity.FavoriteEvent;

@Database(entities = {FavoriteEvent.class}, version = 1, exportSchema = false)
public abstract class FavoriteRoomDatabase extends RoomDatabase {
    public abstract FavoriteEventDao favoriteEventDao();

    private static volatile FavoriteRoomDatabase INSTANCE;

    public static FavoriteRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteRoomDatabase.class, "favorite_event_db")
                        .build();
            }
        }
        return INSTANCE;
    }
}
