package com.example.agriclutureassistant.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.WeatherModel;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.myviewholder> {

    public static class myviewholder extends RecyclerView.ViewHolder {
            TextView tm,cl;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            tm=itemView.findViewById(R.id.tm1);
            cl=itemView.findViewById(R.id.cl1);
        }
    }

    public static Context context;
    public static List<WeatherModel>list;

    public WeatherAdapter(Context c, List<WeatherModel> data_list) {
        this.context = c;
        list = data_list;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customwheatherxml, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        WeatherModel data = list.get(position);
        holder.tm.setText(data.getDegree());
        holder.cl.setText(data.getClock());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
