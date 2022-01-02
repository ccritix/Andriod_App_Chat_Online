package com.anonymous.message.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.anonymous.message.User;
import java.util.List;

@Dao
public interface UserDAO {

    @Query("select * from User")
    List<User> getAll();

    @Insert
    long insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("DELETE FROM User")
    void nukeTable();

    @Query("SELECT phone FROM USER WHERE username = :username")
    String getPhone(String username);
}