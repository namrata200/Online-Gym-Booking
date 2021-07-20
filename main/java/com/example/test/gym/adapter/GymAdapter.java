package com.example.test.gym.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.gym.R;
import com.example.test.gym.preferance.SharePref;
import com.example.test.gym.util.Gym;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GymAdapter  extends RecyclerView.Adapter<GymAdapter.MyViewHolder> {


    private List<Gym> gymList;

    private Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gymName, address;
        public ImageView gymListImage;


        public MyViewHolder(View view) {
            super(view);
            gymName = (TextView) view.findViewById(R.id.gymName);
            address = (TextView) view.findViewById(R.id.address);
            gymListImage = (ImageView) view.findViewById(R.id.gymListImage);

        }
    }


    public GymAdapter(List<Gym> gymList,Context context) {
        this.context = context;
        this.gymList = gymList;
    }

    @Override
    public GymAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gym_row_list, parent, false);

        return new GymAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(GymAdapter.MyViewHolder holder, int position) {
        Gym gym = gymList.get(position);
        holder.gymName.setText(gym.getGymName());
        holder.address.setText(gym.getAddress());
        String fileName= gymList.get(position).getGymImage();
        String image_url = "http://"+new SharePref().getServerURL(context)+"/Gym/GymImages/" +fileName;
        Picasso.with(context).load(image_url).resize(120, 60).into(holder.gymListImage);

    }

    @Override
    public int getItemCount() {
        return gymList.size();
    }
}
