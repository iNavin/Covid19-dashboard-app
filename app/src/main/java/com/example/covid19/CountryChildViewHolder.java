package com.example.covid19;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class CountryChildViewHolder extends ChildViewHolder implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private TextView confirmed;
    private TextView recovered;
    private TextView deaths;
    private LineChart chart;
    Context context;

    public CountryChildViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        confirmed = (TextView)itemView.findViewById(R.id.confirmed_count);
        recovered = (TextView)itemView.findViewById(R.id.recovered_count);
        deaths = (TextView)itemView.findViewById(R.id.death_count);
        chart = (LineChart)itemView.findViewById(R.id.line_chart);
        this.context = context;
    }

    public void bind(CountryChild countryChild){
        confirmed.setText(String.valueOf(countryChild.getConfirmedCount()));
        recovered.setText(String.valueOf(countryChild.getRecoveredCount()));
        deaths.setText(String.valueOf(countryChild.getDeathCount()));
        {   // // Chart Style // //
            chart.setBackgroundColor(Color.WHITE);
            // disable description text
            chart.getDescription().setEnabled(false);
            // enable touch gestures
            chart.setTouchEnabled(true);
            // set listeners
            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(false);
            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
        }
        setData(countryChild.getValues());


    }

    private void setData(ArrayList<Entry> values) {


        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);
            set1.setDrawCircles(false);
            set1.setDrawCircleHole(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);
            // line thickness and point size
            set1.setLineWidth(0f);
            set1.setCircleRadius(0f);
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
