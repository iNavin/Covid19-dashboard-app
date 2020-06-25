package com.example.covid19.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;

import java.util.ArrayList;

public class PreventionRecyclerAdapter extends RecyclerView.Adapter<PreventionRecyclerAdapter.ViewHolder> {


    private ArrayList<String> points;
    private Context context;

    public PreventionRecyclerAdapter(Context context, ArrayList<String> points) {
        this.points = points;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.index.setText(String.valueOf(position+1));
        holder.desc.setText(points.get(position));


    }

    @Override
    public int getItemCount() {
        return points.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView index;
        TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.index);
            desc = itemView.findViewById(R.id.point_description);
        }
    }
}
