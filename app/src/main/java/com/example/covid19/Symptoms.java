package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.covid19.Adapter.SymptomsAdapter;

import java.util.ArrayList;

public class Symptoms extends AppCompatActivity {

    RecyclerView symptomsRecyclerView;
    ArrayList<InfoModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        symptomsRecyclerView = findViewById(R.id.symptoms_recycler_view);
        models = JsonUtils.extractPreventionOrSymptomsData(this,"symptoms");

        Integer[] colors = {
                getResources().getColor(R.color.blue19),
                getResources().getColor(R.color.purple19),
                getResources().getColor(R.color.red19),
                getResources().getColor(R.color.yellow19)
        };

        SymptomsAdapter symptomsAdapter = new SymptomsAdapter(this,models,colors);
        symptomsRecyclerView.setAdapter(symptomsAdapter);
        symptomsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));



    }
}
