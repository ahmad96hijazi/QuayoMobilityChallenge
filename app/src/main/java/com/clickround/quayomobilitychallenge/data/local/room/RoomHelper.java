package com.clickround.quayomobilitychallenge.data.local.room;

import android.content.Context;

import androidx.room.Room;

public class RoomHelper {
    private static RoomHelper ourInstance;

    public static RoomHelper getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new RoomHelper(context);
        return ourInstance;
    }

    private RoomHelper(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "quayomobilitychallenge.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    private final AppDatabase database;

    public AppDatabase getDatabase() {
        return database;
    }
}
