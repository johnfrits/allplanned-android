package com.ticket.bus.employeetaskplanner;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<ModelTask> tasks;
    FirebaseStorage storage;
    StorageReference storageRef;

    public MyAdapter(Context c, ArrayList<ModelTask> p) {
        context = c;
        tasks = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(tasks.get(position).getTaskName());
        holder.name.append(" \nBoard name:" + tasks.get(position).getBoardName());
        holder.name.append(" \nDate Created:" + tasks.get(position).getTaskDateCreated());
        holder.name.append("\nTask Description" + tasks.get(position).getTaskDesc());


        //https://66.media.tumblr.com/10fbf342dadb7120eea947fe8545211e/tumblr_p8vnr7OPpw1ta0hnbo1_1280.jpg
        Glide.with(context).load("https://tutorialspress.com/wp-content/uploads/2015/03/icon.jpg").into(holder.profilePic);
        holder.ViewMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        ImageView profilePic;
        Button btn;
        CardView ViewMoreDetails;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            ViewMoreDetails = itemView.findViewById(R.id.ViewMoreDetails);
//            email =  itemView.findViewById(R.id.count);
            profilePic = itemView.findViewById(R.id.c_image);
//            btn = (Button) itemView.findViewById(R.id.checkDetails);
        }



        public void onClick(final int position) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position + " is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
