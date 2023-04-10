package com.rithik.hospitalApp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rithik.hospitalApp.R;
import com.rithik.hospitalApp.adapters.MessageAdapter;
import com.rithik.hospitalApp.adapters.PatientsAdapter;
import com.rithik.hospitalApp.models.Chats;
import com.rithik.hospitalApp.models.Messages;
import com.rithik.hospitalApp.models.User;

import java.util.ArrayList;


public class Patients extends AppCompatActivity {

    private static final DatabaseReference patientDB = null;
    PatientsAdapter patientAdapter;
    ArrayList<com.rithik.hospitalApp.models.Patients> patientsArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        recyclerView = findViewById(R.id.recyclerView);
        patientsArrayList = new ArrayList<>();
        init();
        getPatients();
    }

    public void init() {
        patientAdapter = new PatientsAdapter(patientsArrayList, Patients.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(Patients.this));
        recyclerView.smoothScrollToPosition(patientAdapter.getItemCount());
        recyclerView.setAdapter(patientAdapter);
        // READ PATIENTS
        FirebaseDatabase.getInstance().getReference().child("Patients").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        com.rithik.hospitalApp.models.Patients patients = userSnapshot.getValue(com.rithik.hospitalApp.models.Patients.class);
                        patientsArrayList.add(patients);
                    }
                    patientAdapter = new PatientsAdapter(patientsArrayList,Patients.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Patients.this));
                    recyclerView.smoothScrollToPosition(patientAdapter.getItemCount());
                    recyclerView.setAdapter(patientAdapter);
                }
            }
        });
    }

    private void getPatients() {

            FirebaseDatabase.getInstance().getReference().child("Patients").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try{
                        int i=0;
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            com.rithik.hospitalApp.models.Patients patients = dataSnapshot.getValue(com.rithik.hospitalApp.models.Patients.class);
//                            Toast.makeText(Patients.this, patients.getName(), Toast.LENGTH_SHORT).show();
                            patientsArrayList.set(i,patients);
                            patientAdapter.notifyItemChanged(i);
                            recyclerView.smoothScrollToPosition(patientAdapter.getItemCount());
                            i++;
                        }
                    }catch (Exception e){
                        Toast.makeText(Patients.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
}