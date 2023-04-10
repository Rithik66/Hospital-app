package com.rithik.hospitalApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rithik.hospitalApp.R;
import com.rithik.hospitalApp.activities.Chat;
import com.rithik.hospitalApp.activities.Contacts;
import com.rithik.hospitalApp.models.Chats;
import com.rithik.hospitalApp.models.Messages;
import com.rithik.hospitalApp.models.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

        private ArrayList<User> users;
        private Context context;
        private onUserClickListener onUserClickListener;

    public UserAdapter(ArrayList<User> users, Context context, UserAdapter.onUserClickListener onUserClickListener) {
        this.users = users;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }

    public interface onUserClickListener{
        void onUserClicked(int position);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_holder,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder,int position) {
        try{
//            Toast.makeText(context, users.get(position).getUserName(), Toast.LENGTH_SHORT).show();
            if(!users.get(position).getId().equals(FirebaseAuth.getInstance().getUid())){
                holder.userName.setText(users.get(position).getUserName());
                Glide.with(context).load(users.get(position).getProfilePic()).error(R.drawable.profilepic).placeholder(R.drawable.profilepic).into(holder.profile_image);
                String id = FirebaseAuth.getInstance().getUid();
                ArrayList<Chats> chats = users.get(position).getChats();
            }
            for(User user:users) {
//                Toast.makeText(context,user.getUserName(), Toast.LENGTH_SHORT).show();
                try {
                    if(user.getId().equals(FirebaseAuth.getInstance().getUid())) {
//                        Toast.makeText(context, user.getUserName(), Toast.LENGTH_SHORT).show();
                        for(Chats chats2 : user.getChats()) {
                            if(chats2.getId().equals(users.get(position).getId())) {
//                                Toast.makeText(context,"IN "+ users.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                                int count = 0;
                                for(Messages messages : chats2.getMessages()) {
                                    if(!messages.getStatus()) {
                                        count++;
                                    }
                                }
//                                Toast.makeText(context, count+"", Toast.LENGTH_SHORT).show();
                                if(count > 0) {
                                    holder.newMessage.setVisibility(View.VISIBLE);
                                    if(count > 9) holder.newMesaageCount.setText(9 + "+");
                                    else holder.newMesaageCount.setText(count + "");
                                }
                            }
                        }
                    }
                }catch(Exception e) {
                    Toast.makeText(context, "UserAdpater -> onBindViewHolder : " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(context, "ERROR : On Bind in UserAdapter"+e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{

        CircleImageView profile_image;
        FrameLayout status;
        TextView userName;
        TextView lastMessage;
        TextView lastMessageTime;
        FrameLayout newMessage;
        TextView newMesaageCount;
        TextView lastMessageName;


        public UserHolder(@NonNull View itemView) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            status = itemView.findViewById(R.id.status);
            userName = itemView.findViewById(R.id.userName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            lastMessageTime = itemView.findViewById(R.id.lastMessageTime);
            newMessage = itemView.findViewById(R.id.newMessage);
            newMesaageCount = itemView.findViewById(R.id.newMesaageCount);
            lastMessageName = itemView.findViewById(R.id.lastMessageName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserClickListener.onUserClicked(getAdapterPosition());
                }
            });
        }
    }
}
