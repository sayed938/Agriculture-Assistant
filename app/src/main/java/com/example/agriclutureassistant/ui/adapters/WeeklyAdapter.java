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
import com.example.agriclutureassistant.pojo.WeatherModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ViewHolder> {

    public static List<WeatherModel.Forecastday> list;

    public WeeklyAdapter(List<WeatherModel.Forecastday> data_list) {
        list = data_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cstomnextforecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherModel.Forecastday data = list.get(position);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date datE = sdf.parse(data.date);
            sdf.applyPattern("EEEE");
            holder.day.setText(sdf.format(datE) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.temper.setText(Math.round(data.day.maxtemp_c) + "º" + "/" + Math.round(data.day.mintemp_c) + "ºC");
        Picasso.get().load("https:" + data.day.condition.icon).into(holder.weeklyImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, temper;
        ImageView weeklyImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.weekly_day);
            temper = itemView.findViewById(R.id.weekly_temper);
            weeklyImg = itemView.findViewById(R.id.weekly_img);
        }
    }
}
