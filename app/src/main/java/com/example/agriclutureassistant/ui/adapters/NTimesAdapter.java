package com.example.agriclutureassistant.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.NextForecastModel;

import java.util.List;

public class NTimesAdapter extends RecyclerView.Adapter<NTimesAdapter.ViewHolder> {

    public static Context context;
    public static List<NextForecastModel> list;

    public NTimesAdapter(Context c, List<NextForecastModel> data_list) {
        this.context = c;
        list = data_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cstomnextforecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NextForecastModel data = list.get(position);
        holder.day.setText(data.getDay());
        holder.temp.setText(data.getTemper());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView day,temp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day=itemView.findViewById(R.id.day);
            temp=itemView.findViewById(R.id.nexttempr);
        }
    }
}
