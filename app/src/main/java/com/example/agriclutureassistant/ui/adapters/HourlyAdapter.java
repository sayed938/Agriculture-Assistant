package com.example.agriclutureassistant.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.ui.pojo.WeatherModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.MyViewHolder> {

    public static Context context;

    public static List<WeatherModel.Hour> list;

    public HourlyAdapter(List<WeatherModel.Hour> data_list) {
        list = data_list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customwheatherxml, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WeatherModel.Hour data = list.get(position);
        holder.hour.setText(data.time.split("\\s")[1].split("\\.")[0]);
        holder.temper.setText(Math.round(data.temp_c) + "º");
        Picasso.get().load("https:" + data.condition.icon).into(holder.hourly_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView hour, temper;
        private ImageView hourly_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.hour);
            temper = itemView.findViewById(R.id.hourly_temper);
            hourly_img = itemView.findViewById(R.id.hourly_img);
        }
    }
}
