package com.anonymous.message.network;

import com.anonymous.message.MessageRoom;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadMesssage extends Result{
    @SerializedName("data")
    public List<MessageRoom> messageRooms;
}
