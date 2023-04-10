package com.rithik.hospitalApp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rithik.hospitalApp.R;
import com.rithik.hospitalApp.adapters.UserAdapter;
import com.rithik.hospitalApp.models.Chats;
import com.rithik.hospitalApp.models.User;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    ArrayList<User> users;
    ProgressBar progressBar;
    UserAdapter.onUserClickListener onUserClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView = findViewById(R.id.recycle);
        users = new ArrayList<>();
        progressBar = findViewById(R.id.progress);

        getUser();
        onUserClickListener = new UserAdapter.onUserClickListener() {
            @Override
            public void onUserClicked(int position) {
                finish();
                Intent intent = new Intent(getApplicationContext(),Chat.class);
                intent.putExtra("user",users.get(position).getId());
                startActivity(intent);
//                Toast.makeText(Contacts.this, users.get(position).getUserName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_profile){
            startActivity(new Intent(Contacts.this, Profile.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void getUser(){
        FirebaseDatabase.getInstance().getReference("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    try {
                        User user = userSnapshot.getValue(User.class);
                        if(!user.getId().equals(FirebaseAuth.getInstance().getUid())){
                            users.add(user);
//                            Toast.makeText(Contacts.this,  user.getUserName(), Toast.LENGTH_SHORT).show();
                            userAdapter = new UserAdapter(users,Contacts.this,onUserClickListener);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Contacts.this));
                            recyclerView.setAdapter(userAdapter);
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e) {
                        Toast.makeText(Contacts.this,"GET USER : "+ e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Contacts.this, "ERROR : Get Messages"+databaseError.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Contacts.this,Home.class);
        startActivity(intent);
    }
}