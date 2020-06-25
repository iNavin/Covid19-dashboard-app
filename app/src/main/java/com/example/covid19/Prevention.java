package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.covid19.Adapter.PreventionAdapter;

import java.util.List;

public class Prevention extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter adapter;
    List<InfoModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevention);

        models = JsonUtils.extractPreventionOrSymptomsData(this,"prevention");
        adapter = new PreventionAdapter(models,this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);





    }
}
