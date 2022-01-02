package com.anonymous.message.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.anonymous.message.MessageRoom;

import java.util.List;

@Dao
public interface MessageDAO {

    @Query("select * from MessageRoom")
    List<MessageRoom> getAll();

    @Insert
    long insert(MessageRoom messageRoom);

    @Delete
    void delete(MessageRoom messageRoom);

    @Update
    void update(MessageRoom messageRoom);

    @Query("DELETE FROM MessageRoom where user2 = :user2")
    void removeAllTable(String user2);
}
