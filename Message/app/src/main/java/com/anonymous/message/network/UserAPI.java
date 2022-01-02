package com.anonymous.message.network;

import androidx.cardview.widget.CardView;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
    @GET("get-user.php")
    Call<Load> getUser();

    @POST("get-message.php")
    @FormUrlEncoded
    Call<LoadMesssage> getMessage(@Field("user1") String user1, @Field("user2") String user2);

    @POST("add-user.php")
    @FormUrlEncoded
    Call<Add> addUser(@Field("username") String username, @Field("password") String password, @Field("phone") String phone);

    @POST("add-message.php")
    @FormUrlEncoded
    Call<Add> addMessage(@Field("username1") String username1, @Field("username2") String username2, @Field("message1") String message1, @Field("message2") String message2, @Field("time") String time);

    @POST("login.php")
    @FormUrlEncoded
    Call<Data> login(@Field("username") String username, @Field("password") String password);

    @POST("create_room.php")
    @FormUrlEncoded
    Call<Result> createRoom(@Field("username") String username);

    @POST("update_user.php")
    @FormUrlEncoded
    Call<Add> updateUser(@Field("username") String username, @Field("oldpass") String oldpass, @Field("newpass") String newpass);

    @POST("check_new_message.php")
    @FormUrlEncoded
    Call<Length> checkLength(@Field("user1") String user1, @Field("user2") String user2);
}
