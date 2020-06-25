package com.example.covid19.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.InfoModel;
import com.example.covid19.R;

import java.util.ArrayList;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.ViewHolder> {

    Context context;
    ArrayList<InfoModel> models;
    Integer[] colors;

    public SymptomsAdapter(Context context,ArrayList<InfoModel> models, Integer[] colors) {
        this.context = context;
        this.models = models;
        this.colors = colors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptoms_item, parent, false);
        SymptomsAdapter.ViewHolder holder = new SymptomsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.imageView.setImageResource(models.get(position).getImage());
        holder.title.setText(models.get(position).getTitle());
        holder.title.setTextColor(colors[position%colors.length]);


        holder.desc.setText(models.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);


        }
    }
}
