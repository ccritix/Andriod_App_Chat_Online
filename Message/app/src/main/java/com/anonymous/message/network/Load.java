package com.anonymous.message.network;
import com.anonymous.message.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Load extends Result {
    @SerializedName("data")
    public List<User> users;
}
