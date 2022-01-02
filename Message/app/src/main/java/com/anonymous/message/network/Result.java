package com.anonymous.message.network;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("status")
    public boolean success;
    public String message;
}
