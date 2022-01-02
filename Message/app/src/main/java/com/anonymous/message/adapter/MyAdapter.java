package com.anonymous.message.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.message.R;
import com.anonymous.message.Setting;
import com.anonymous.message.activity.MessageActivity;
import com.anonymous.message.activity.RoomChatActivity;
import com.anonymous.message.User;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

    private Context context;
    private List<User> Users;
    private MessageActivity messageActivity;
    private Setting setting;

    public void setMessageActivity(MessageActivity messageActivity) {
        this.messageActivity = messageActivity;
    }

    public MyAdapter(Context context, List<User> Users){
        this.context = context;
        this.Users = Users;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_layout, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        final User user = Users.get(position);
        setting = new Setting(context);
        holder.tvName.setText(user.getUsername());
        //Log.e("TAG", "MyAdapter " + messageActivity.userService);
        if (messageActivity.userService.length() != 0){
            if (user.getUsername().equals(messageActivity.userService)){
                holder.tvNum.setVisibility(View.VISIBLE);
            }else {
                holder.tvNum.setVisibility(View.INVISIBLE);
            }
        }else {
            holder.tvNum.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getUsername().equals(messageActivity.userService)){
                    holder.tvNum.setVisibility(View.INVISIBLE);
                    messageActivity.userService = "";
                    setting.setUserNewMessage("");
                }

                Intent intent = new Intent(context, RoomChatActivity.class);
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("name", user.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Users.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvNum;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvNum = itemView.findViewById(R.id.tvNum);

        }
    }
}
