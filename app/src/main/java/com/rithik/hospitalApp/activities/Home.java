package com.rithik.hospitalApp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rithik.hospitalApp.R;

public class Home extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    private Button check;
    private TextView check_time;
    private DatabaseReference dbref;
    private Handler handler;
    private MaterialCardView chat,patients;
    private long startTime, timeInMilliseconds = 0;
    private boolean isRunning = false;

    final String[] check_in = {""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        handler = new Handler();
        dbref = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        check = findViewById(R.id.check);
        check_time = findViewById(R.id.check_time);
        chat = findViewById(R.id.chat);
        patients = findViewById(R.id.patients);

//        addUser();
        getTime();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbref.child("check").child("in").get().addOnCompleteListener(
                            new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(task.isSuccessful()){
                                        isRunning = Boolean.parseBoolean(task.getResult().getValue().toString());
                                        if (isRunning) {
                                            handler.removeCallbacks(runnable);
                                            timeInMilliseconds = 0;
                                            check_time.setText("00:00:00");
                                            check.setText("CHECK IN");
                                            dbref.child("check").child("in").setValue(false);
                                            dbref.child("check").child("time").setValue(0);
//                                        Toast.makeText(Home.this, isRunning+" OUT", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            startTime = SystemClock.uptimeMillis();
                                            check.setText("CHECK OUT");
                                            dbref.child("check").child("in").setValue(true);
                                            dbref.child("check").child("time").setValue(startTime);
                                            handler.postDelayed(runnable, 0);
//                                        Toast.makeText(Home.this, isRunning+" IN", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(Home.this, "Restart the application", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                }catch(Exception e){
                    Toast.makeText(Home.this, "Error : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contacts = new Intent(Home.this, Contacts.class);
                startActivity(contacts);
            }
        });

        patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent patients = new Intent(Home.this, Patients.class);
                    startActivity(patients);
                }catch (Exception e){
                    Toast.makeText(Home.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
//    public void addUser(){
//        Messages messages = new Messages("mQi6kaUpI6dhJPrcyuNrWz7u6qs2","Call me",false,"09:05");
//        ArrayList<Messages> messagesArrayList = new ArrayList<>();
//        messagesArrayList.add(messages);
//        Chats chats = new Chats("mQi6kaUpI6dhJPrcyuNrWz7u6qs2",messagesArrayList,"Call me","09:05");
//        ArrayList<Chats> chatsArrayList = new ArrayList<>();
//        User.Check check1 = new User.Check(false,0);
//        chatsArrayList.add(chats);
//        User user = new User(
//                chatsArrayList,
//                check1,
//                "online",
//                FirebaseAuth.getInstance().getCurrentUser().getEmail(),
//                FirebaseAuth.getInstance().getUid(),
//                "",
//                "mathu"
//        );
//
//        FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(Home.this, "success", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(Home.this, "Failed", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//    }

    public void getTime(){
//        Toast.makeText(this, "HI", Toast.LENGTH_SHORT).show();
        final boolean[] is = new boolean[1];
        dbref.child("check").child("in").get().addOnCompleteListener(

                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        Toast.makeText(Home.this, "IN", Toast.LENGTH_SHORT).show();
                        if(task.isSuccessful()){

//                            Toast.makeText(Home.this, "HI IN", Toast.LENGTH_SHORT).show();
                            is[0] = Boolean.parseBoolean(task.getResult().getValue().toString());
                            if(is[0]){
                                dbref.child("check").child("time").get().addOnCompleteListener(
                                        new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    startTime = Long.parseLong(task.getResult().getValue().toString());
//                                                    Toast.makeText(Home.this, startTime+"", Toast.LENGTH_SHORT).show();
                                                    handler.postDelayed(runnable, 0);
                                                }
                                            }
                                        }
                                );
                            }else{
                                check.setText("CHECK IN");
                            }
                        }
                        else{
                            Toast.makeText(Home.this, "Restart the application", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Home.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(Home.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int hours = (int) (timeInMilliseconds / (1000 * 60 * 60));
            int minutes = (int) ((timeInMilliseconds / (1000 * 60)) % 60);
            int seconds = (int) ((timeInMilliseconds / 1000) % 60);
            int milliseconds = (int) (timeInMilliseconds % 1000);
            check_time.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_profile){
            startActivity(new Intent(Home.this, Profile.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}