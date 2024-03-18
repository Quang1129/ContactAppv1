package com.example.contactapp;

//Data access object (DAO)
//
//        The following code defines a DAO called UserDao.
//        UserDao provides the methods that the rest of the app uses to interact with data in the user table.

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();


    @Insert
    void insert(Contact... contacts);

    @Delete
    void delete(Contact... contacts);


}
