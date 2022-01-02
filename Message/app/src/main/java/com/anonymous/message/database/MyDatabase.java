package com.anonymous.message.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.anonymous.message.MessageRoom;
import com.anonymous.message.User;

@Database(entities = {User.class, MessageRoom.class}, version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDAO getUserDAO();

    public abstract MessageDAO getMessageDAO();
}
