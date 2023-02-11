package com.example.agriclutureassistant.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.HomeFeaturesModel;
import com.example.agriclutureassistant.ui.MainActivity;
import com.example.agriclutureassistant.ui.features_activities.PlantDisease;
import com.example.agriclutureassistant.ui.features_activities.PlantName;
import com.example.agriclutureassistant.ui.features_activities.SocialMedia;
import com.example.agriclutureassistant.ui.fragments.HomeFeatures;

import java.util.ArrayList;

public class HomeFeaturesAdapter extends RecyclerView.Adapter<HomeFeaturesAdapter.HomeFeaturesViewHolder> {


    ArrayList<HomeFeaturesModel> arrayList = new ArrayList<>();
    Context context;


    public HomeFeaturesAdapter(ArrayList<HomeFeaturesModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeFeaturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_feature,parent,false);
        return new HomeFeaturesViewHolder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFeaturesViewHolder holder, int position) {

        //final HomeFeaturesModel model = arrayList.get(position);
        holder.featureName.setText(arrayList.get(position).getFeatureName());
        Glide.with(context).load(arrayList.get(position).getImages()).into(holder.featureImage);
        //holder.featureImage.setImageResource(arrayList.get(position).getImages());
        holder.featureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(position==0)
                    context.startActivity(new Intent(context, PlantName.class));
                   else if (position==1)
                       context.startActivity(new Intent(context,PlantDisease.class));
                   else context.startActivity(new Intent(context, SocialMedia.class));
                }

        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HomeFeaturesViewHolder extends RecyclerView.ViewHolder  {

        ImageView featureImage;
        TextView featureName;

        public HomeFeaturesViewHolder(@NonNull View itemView) {
            super(itemView);
            featureImage = itemView.findViewById(R.id.feature_image);
            featureName = itemView.findViewById(R.id.feature_name_tv);
        }


    }
}
