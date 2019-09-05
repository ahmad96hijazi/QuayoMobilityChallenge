package com.clickround.quayomobilitychallenge.data.local.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.clickround.quayomobilitychallenge.data.local.room.model.Person;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert
    void insert(Person item);

    @Update
    void update(Person item);

    @Delete
    void delete(Person item);

    @Query("SELECT * FROM persons")
    List<Person> persons();
}
