package com.rithik.hospitalApp.adapters;


import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.rithik.hospitalApp.R;
import com.rithik.hospitalApp.activities.LOG;
import com.rithik.hospitalApp.activities.Profile;
import com.rithik.hospitalApp.models.Messages;
import com.rithik.hospitalApp.models.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    public static final int MSG_SENDER = 0;
    public static final int MSG_RECIEVER = 1;

    Context context;
    ArrayList<Messages> messages;
    String imageURL;

    public MessageAdapter(Context context, ArrayList<Messages> messages,String imageURL) {
        this.context = context;
        this.messages = messages;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_SENDER){
            View view = LayoutInflater.from(context).inflate(R.layout.container_send_message,parent,false);
            return new MessageAdapter.MessageHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.container_recieve_message,parent,false);
            return new MessageAdapter.MessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        try{
            Messages message = messages.get(position);
            holder.textMessage.setText(message.getMessage());
            holder.textDateTime.setText(message.getSent());
//            Toast.makeText(context, message.getSent(), Toast.LENGTH_SHORT).show();
            if(getItemViewType(position)==MSG_RECIEVER) {
                Glide.with(context).load(imageURL).error(R.drawable.profilepic).placeholder(R.drawable.profilepic).into(holder.chatProfile);
            }
        }catch (Exception e){
            Toast.makeText(context, "ERROR : On Bind in MessageAdapter"+e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder {

        CircleImageView chatProfile;
        TextView textMessage;
        TextView textDateTime;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            chatProfile = itemView.findViewById(R.id.chatProfile);
            textMessage = itemView.findViewById(R.id.textMessage);
            textDateTime = itemView.findViewById(R.id.textDateTime);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getId().equals(FirebaseAuth.getInstance().getUid())){
            return MSG_SENDER;
        }else{
            return MSG_RECIEVER;
        }
    }
}
