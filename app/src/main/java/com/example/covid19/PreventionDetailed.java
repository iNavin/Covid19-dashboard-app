package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid19.Adapter.PreventionRecyclerAdapter;

import java.util.ArrayList;

public class PreventionDetailed extends AppCompatActivity {

    ImageView imageView;
    TextView title, desc;
    RecyclerView pointRecyclerView;
    ArrayList<String> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevention_detailed);

        InfoModel model = (InfoModel) getIntent().getSerializableExtra("obj");

        imageView = findViewById(R.id.image);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        pointRecyclerView = findViewById(R.id.points_list);

        imageView.setImageResource(model.getImage());
        title.setText(model.getTitle());
        desc.setText(model.getDesc());


        points = model.getPoints();
        model.setPoints(points);

        PreventionRecyclerAdapter adapter = new PreventionRecyclerAdapter(this,points);
        pointRecyclerView.setAdapter(adapter);
        pointRecyclerView.setLayoutManager(new LinearLayoutManager(this));








    }

}
