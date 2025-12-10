package com.example.tagihan123.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Query;

import com.example.tagihan123.data.model.User;

@Dao
public interface UserDao {

    @Insert
    long insert(User user);
    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getUserById(int id);
}
