package com.rithik.hospitalApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rithik.hospitalApp.R;
import com.rithik.hospitalApp.models.Patients;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.PatientHolder>{

    ArrayList<Patients> patientsArrayList;
    Context context;

    public PatientsAdapter(ArrayList<Patients> patientsArrayList, Context context) {
        this.patientsArrayList = patientsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.patient_holder,parent,false);
        return new PatientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHolder holder, int position) {
        try {
            Patients patient = patientsArrayList.get(position);
            Glide.with(context).load(patient.getProfilePic()).error(R.drawable.profilepic).placeholder(R.drawable.profilepic).into(holder.profile_image);
            holder.userName.setText(patient.getName());
            holder.LiveT.setText(patient.getStats().split(" ")[0]);
            holder.LiveP.setText((Integer.parseInt(patient.getStats().split(" ")[1])/10)+"");
        }catch (Exception e){
            Toast.makeText(context,"PatientsAdapter BIND"+ e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return patientsArrayList.size();
    }

    class PatientHolder extends RecyclerView.ViewHolder{
        CircleImageView profile_image;
        TextView userName,LiveT,LiveP;

        public PatientHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            profile_image = itemView.findViewById(R.id.profile_image);
            LiveP = itemView.findViewById(R.id.LiveP);
            LiveT = itemView.findViewById(R.id.LiveT);
        }
    }

}
