package com.clickround.quayomobilitychallenge.data.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.clickround.quayomobilitychallenge.data.local.room.dao.PersonDao;
import com.clickround.quayomobilitychallenge.data.local.room.model.Person;

import org.jetbrains.annotations.NotNull;

@Database(entities =
        {
                Person.class,
        }, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PersonDao personDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NotNull SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
}
