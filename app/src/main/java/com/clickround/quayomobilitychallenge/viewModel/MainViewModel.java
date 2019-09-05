package com.clickround.quayomobilitychallenge.viewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.clickround.quayomobilitychallenge.data.local.room.AppDatabase;
import com.clickround.quayomobilitychallenge.data.local.room.RoomHelper;
import com.clickround.quayomobilitychallenge.data.local.room.model.Person;
import com.clickround.quayomobilitychallenge.view.callback.QueryListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final AppDatabase appDatabase;

    public MainViewModel(Context context) {
        appDatabase = RoomHelper.getInstance(context).getDatabase();
    }

    public void getPersons(@NotNull QueryListener<List<Person>> listener) {
        listener.onQueryComplete(appDatabase.personDao().persons());
    }
}
