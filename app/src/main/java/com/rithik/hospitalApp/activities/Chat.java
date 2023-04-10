package com.rithik.hospitalApp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rithik.hospitalApp.R;
import com.rithik.hospitalApp.adapters.MessageAdapter;
import com.rithik.hospitalApp.adapters.UserAdapter;
import com.rithik.hospitalApp.models.Chats;
import com.rithik.hospitalApp.models.Messages;
import com.rithik.hospitalApp.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    TextView user;
    Intent intent;
    String id;
    CircleImageView chat_profilepic;
    MaterialCardView send_button;
    EditText send_message;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;

    ArrayList<Messages> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.messageView);

        user = findViewById(R.id.user);
        chat_profilepic = findViewById(R.id.chat_profilepic);
        send_button = findViewById(R.id.send_button);
        send_message = findViewById(R.id.send_message);
        intent = getIntent();
        id=intent.getStringExtra("user");

        messages = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        init();
        getMessages();
//        readMessages();

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String message = send_message.getText().toString();
                    if(message.equals("")){
                        Toast.makeText(Chat.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                    }else {
                        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try{
                                    Map<String,User> map = new HashMap<>();
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        User user = userSnapshot.getValue(User.class);
                                        String date = getDateAndTime(System.currentTimeMillis());
                                        //set message at sender side
                                        if(user.getId().equals(FirebaseAuth.getInstance().getUid())) {
                                            try {
                                                ArrayList<Chats> chatsArrayList = user.getChats();
                                                for (int i = 0; i < chatsArrayList.size(); i++) {
                                                    Chats chats = chatsArrayList.get(i);
                                                    if (chats.getId().equals(id)) {
//                                                    Toast.makeText(Chat.this, chats.getId()+" <---- "+user.getId(), Toast.LENGTH_SHORT).show();
                                                        ArrayList<Messages> messages = chats.getMessages();
                                                        Messages newMessage = new Messages(
                                                                FirebaseAuth.getInstance().getUid(),
                                                                message,
                                                                true,
                                                                date
                                                        );
                                                        messages.add(newMessage);
//                                                chats.setLastMessage(message);
//                                                chats.setLastMessageName(FirebaseAuth.getInstance().getUid());
//                                                chats.setLastMessageTime(date);
                                                        chats.setMessages(messages);
                                                        chatsArrayList.set(i, chats);
                                                        user.setChats(chatsArrayList);
//                                                    Toast.makeText(Chat.this, user.getChats().toString(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(Chat.this, "CHAT: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        //set message at receiver side
                                        else if(user.getId().equals(id)) {
                                            try{
                                                ArrayList<Chats> chatsArrayList = user.getChats();
                                                for(int i = 0; i < chatsArrayList.size(); i++) {
                                                    Chats chats = chatsArrayList.get(i);
                                                    if(chats.getId().equals(FirebaseAuth.getInstance().getUid())) {
//                                                Toast.makeText(Chat.this, chats.getId()+" ----> "+user.getId(), Toast.LENGTH_SHORT).show();
                                                        ArrayList<Messages> messages = chats.getMessages();
                                                        Messages newMessage = new Messages(
                                                                FirebaseAuth.getInstance().getUid(),
                                                                message,
                                                                false,
                                                                date
                                                        );
                                                        messages.add(newMessage);
//                                                chats.setLastMessage(message);
//                                                chats.setLastMessageName(FirebaseAuth.getInstance().getUid());
//                                                chats.setLastMessageTime(date);
                                                        chats.setMessages(messages);
                                                        chatsArrayList.set(i, chats);
                                                        user.setChats(chatsArrayList);
//                                                Toast.makeText(Chat.this, user.getChats().toString(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }catch(Exception e) {
                                                Toast.makeText(Chat.this, "CHAT: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        map.put(user.getId(),user);
                                    }
                                    FirebaseDatabase.getInstance().getReference().child("user").setValue(map);
                                    Toast.makeText(Chat.this, "SENT", Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(Chat.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(Chat.this, "ERROR : Send 1"+databaseError.getMessage() , Toast.LENGTH_LONG).show();
                            }
                        });
                        send_message.setText("");
                    }
                }catch (Exception e){
                    Toast.makeText(Chat.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User obj = userSnapshot.getValue(User.class);
                    if(obj.getId().equals(id)){
                        user.setText(obj.getUserName());
                        Glide.with(Chat.this).load(obj.getProfilePic()).error(R.drawable.profilepic).placeholder(R.drawable.profilepic).into(chat_profilepic);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Chat.this, "ERROR : Send 2"+error.getMessage() , Toast.LENGTH_LONG).show();}
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(Chat.this,Contacts.class);
        finish();
//        startActivity(intent1);
    }
    public void init() {
        messageAdapter = new MessageAdapter(Chat.this,messages,"");
        recyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));
        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        recyclerView.setAdapter(messageAdapter);
        Toast.makeText(this, "IN", Toast.LENGTH_SHORT).show();
        // READ MESSAGES
        FirebaseDatabase.getInstance().getReference().child("user").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if(user.getId().equals(FirebaseAuth.getInstance().getUid())){
                            try{
                                ArrayList<Chats> chatsArrayList = user.getChats();
                                for (int i = 0; i < chatsArrayList.size(); i++) {
                                    Chats chats = chatsArrayList.get(i);
                                    if (chats.getId().equals(id)) {
                                        messages = chats.getMessages();
                                        for(int j=0;j<messages.size();j++){
                                            messages.get(j).setStatus(true);
                                        }
                                        chats.setMessages(messages);
                                        chatsArrayList.set(i, chats);
                                        user.setChats(chatsArrayList);
                                    }
                                }
//                                Toast.makeText(Chat.this, messages.toString(), Toast.LENGTH_SHORT).show();
                                messageAdapter = new MessageAdapter(Chat.this,messages,"");
                                recyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));
                                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
                                recyclerView.setAdapter(messageAdapter);

//                                FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).setValue(user);

                            }
                            catch (Exception e){
                                Toast.makeText(Chat.this, "ERROR : On Bind in CHAT 210"+e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
    }

    public void getMessages() {
        try {
            FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Toast.makeText(Chat.this, "GET", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        String imageURL = "";
                        if(user.getId().equals(FirebaseAuth.getInstance().getUid())){
//                        Toast.makeText(Chat.this, user.getUserName(), Toast.LENGTH_SHORT).show();
                            //GET MESSAGES
                            ArrayList<Chats> list = user.getChats();
                            for(int i=0;i< list.size();i++){
                                Chats userIn =  list.get(i);
//                            Toast.makeText(Chat.this, userIn.getId(), Toast.LENGTH_SHORT).show();
                                if(userIn.getId().equals(id)){
                                    ArrayList<Messages> msgs = userIn.getMessages();
                                    ArrayList<Messages> msg = new ArrayList<>();
                                    msg.add(msgs.get(msgs.size()-1));
                                    int size = messageAdapter.getItemCount();
                                    if(size<msgs.size()){
                                        messages.addAll(msg);
                                        messageAdapter.notifyItemRangeInserted(size,msg.size());
                                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
                                    }
                                }
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Chat.this, "ERROR : On Bind in Chat 247"+error.getMessage()+" "+error.getDetails() , Toast.LENGTH_LONG).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(Chat.this, "ERROR : get catch -> "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private String getDateAndTime(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(milliseconds);
        String date = sdf.format(resultdate);
        return date;
    }
}