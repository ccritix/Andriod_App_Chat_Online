package com.anonymous.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anonymous.message.MessageRoom;
import com.anonymous.message.R;
import com.anonymous.message.activity.MessageActivity;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder>{

    private Context context;
    private List<MessageRoom> messages;

    public MessageAdapter(Context context, List<MessageRoom> messageRooms){
        this.context = context;
        this.messages = messageRooms;
    }

    @NonNull
    @Override
    public MessageAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_room, parent, false);
        MessageAdapter.MyHolder holder = new MessageAdapter.MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.MyHolder holder, int position) {
        final MessageRoom messageRoom = messages.get(position);
        String message1 = messageRoom.getMessage1();
        String message2 = messageRoom.getMessage2();

        holder.user1.setText(message1);
        holder.user2.setText(message2);
        holder.timeUser1.setText(messageRoom.getTime());
        holder.timeUser2.setText(messageRoom.getTime());
        if (message1.length() == 0){
            holder.user1.setVisibility(View.INVISIBLE);
            holder.timeUser1.setVisibility(View.INVISIBLE);
        }else {
            holder.user1.setVisibility(View.VISIBLE);
            holder.timeUser1.setVisibility(View.VISIBLE);
        }
        if (message2.length() == 0){
            holder.user2.setVisibility(View.INVISIBLE);
            holder.timeUser2.setVisibility(View.INVISIBLE);
        }else {
            holder.user2.setVisibility(View.VISIBLE);
            holder.timeUser2.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder{
        private TextView user1, user2, timeUser1, timeUser2;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            user1 = itemView.findViewById(R.id.user1);
            user2 = itemView.findViewById(R.id.user2);
            timeUser1 = itemView.findViewById(R.id.timeUser1);
            timeUser2 = itemView.findViewById(R.id.timeUser2);
        }
    }
}