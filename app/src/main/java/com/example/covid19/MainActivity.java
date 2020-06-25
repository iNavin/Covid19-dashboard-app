package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    CovidDbHelper covidDbHelper;
    SQLiteDatabase db;
    CSVReader csvReader;
    CSVReader csvReaderIndia;
    ProgressDialog p;

    private long lineCount;
    private long IndiaLineCount;
    private PieChart chart;

    ArrayList<Entry> confirmedList;
    ArrayList<Entry> recoveredList;
    ArrayList<Entry> deathList;

    private LineChart chart1;
    private LineChart chart2;
    private LineChart chart3;

    SwipeRefreshLayout refresh;

    int recovered;
    int deaths;
    int confirmed;
    int remaining;

    int confirmedState;
    int recoveredState;
    int deathsState;

    TextView confirmedText;
    TextView recoveredText;
    TextView deathText;

    TextView confirmedTextState;
    TextView recoveredTextState;
    TextView deathTextState;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializng swipe refresh
        refresh = (SwipeRefreshLayout)findViewById(R.id.refresh);


        //initializing the graphs
        chart1 = (LineChart)findViewById(R.id.confirmed_graph);
        chart2 =(LineChart)findViewById(R.id.recovered_graph);
        chart3 = (LineChart)findViewById(R.id.deaths_graph);

        //intializing text associated with the graphs
        confirmedText = (TextView)findViewById(R.id.confirmed);
        recoveredText = (TextView)findViewById(R.id.recovered);
        deathText = (TextView)findViewById(R.id.deaths);

        //initializing state report

        confirmedTextState = (TextView)findViewById(R.id.confirmed_state);
        recoveredTextState = (TextView)findViewById(R.id.recovered_state);
        deathTextState = (TextView)findViewById(R.id.deaths_state);

        //database initialization
        covidDbHelper = new CovidDbHelper(getApplicationContext());
        db = covidDbHelper.getWritableDatabase();

        //CvsReader initialization
        InputStream is = getResources().openRawResource(R.raw.data);
        Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        csvReader = new CSVReader(reader);

        //CvsReader initialization
        InputStream isIndia = getResources().openRawResource(R.raw.indiadata);
        Reader readerIndia = new BufferedReader(new InputStreamReader(isIndia, Charset.forName("UTF-8")));
        csvReaderIndia = new CSVReader(readerIndia);



        CardView report = (CardView) findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Report.class);
                startActivity(i);
            }
        });

        final CardView prevention =(CardView)findViewById(R.id.prevention);
        prevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Prevention.class);
                startActivity(i);
            }
        });

        CardView symtoms = (CardView)findViewById(R.id.symptoms);
        symtoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Symptoms.class);
                startActivity(i);
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CSVReaderAsyncTask task = new CSVReaderAsyncTask();
                task.execute();
                refresh.setRefreshing(false);
            }
        });




    }



   private void generatePieChart(ArrayList<PieEntry> entries){

        chart = (PieChart)findViewById(R.id.pie_chart);

        chart.setUsePercentValues(false);

        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setCenterText(generateCenterSpannableText());

        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);

        chart.animateY(1400, Easing.EaseInOutQuad);

       Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        //chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(9f);
        setPieData(entries);


    }

    private void setPieData(ArrayList<PieEntry> entries) {


        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // adding colors

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ResourcesCompat.getColor(getResources(), R.color.green1, null));
        colors.add(ResourcesCompat.getColor(getResources(), R.color.googleRed, null));
        colors.add(ResourcesCompat.getColor(getResources(), R.color.yellow, null));


        dataSet.setColors(colors);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);


        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    private void readFromCSV(CSVReader csvReader, CSVReader csvReaderIndia ,SQLiteDatabase db) throws IOException {
        //For skipping the header
        csvReader.skip(1);
       //for skipping already read lines
        csvReader.skip((int)lineCount);

        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            insertToDB(db, new CSVData(nextRecord[0],nextRecord[1],Double.valueOf(nextRecord[2]),Double.valueOf(nextRecord[3]),nextRecord[4],Integer.parseInt(nextRecord[5]),Integer.parseInt(nextRecord[6]),Integer.parseInt(nextRecord[7])));
        }

        //For indiaLocation tabble
        csvReaderIndia.skip(1);
        csvReaderIndia.skip((int)IndiaLineCount);
        String[] nextRecordIndiaTable;
        while((nextRecordIndiaTable = csvReaderIndia.readNext())!=null){
            insertToDBIndiaTable(db, new IndiaCSVData(nextRecordIndiaTable[1],nextRecordIndiaTable[3],Integer.parseInt(nextRecordIndiaTable[6]),Integer.parseInt(nextRecordIndiaTable[7]),Integer.parseInt(nextRecordIndiaTable[8])));
        }

    }
    private SpannableString generateCenterSpannableText() {
        String confirmedString = String.valueOf(confirmed);

        SpannableString s = new SpannableString(confirmedString + "\n Total Cases");
         s.setSpan(new RelativeSizeSpan(1.7f), 0, confirmedString.length(), 0);
         s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length() , 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), 0);
        return s;
    }

    private long insertToDB(SQLiteDatabase db, CSVData data){
        ContentValues values = new ContentValues();
        values.put(Contract.LocationTable.COL_STATE, data.getState());
        values.put(Contract.LocationTable.COL_COUNTRY, data.getCountry());
        values.put(Contract.LocationTable.COL_LAT,data.getLatitude());
        values.put(Contract.LocationTable.COL_LONG,data.getLongitude());
        values.put(Contract.LocationTable.COL_DATE,data.getDate());
        values.put(Contract.LocationTable.COL_CONFIRMED,data.getConfirmed());
        values.put(Contract.LocationTable.COL_DEATHS,data.getDeaths());
        values.put(Contract.LocationTable.COL_RECOVERED,data.getRecovered());

        long newRowId = db.insert(Contract.LocationTable.TABLE_NAME,null,values);
        return newRowId;
    }

    private long insertToDBIndiaTable(SQLiteDatabase db, IndiaCSVData data){
        ContentValues values = new ContentValues();


        values.put(Contract.IndiaLocationTable.COL_DATE,data.getDate());
        values.put(Contract.IndiaLocationTable.COL_STATE, data.getState());
        values.put(Contract.IndiaLocationTable.COL_RECOVERED,data.getRecovered());
        values.put(Contract.IndiaLocationTable.COL_DEATHS,data.getDeaths());
        values.put(Contract.IndiaLocationTable.COL_CONFIRMED,data.getConfirmed());

        long newRowId = db.insert(Contract.IndiaLocationTable.TABLE_NAME,null,values);
        return newRowId;
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

    class CSVReaderAsyncTask extends AsyncTask<Void, Void, Long> {
        @Override
        protected void onPostExecute(Long aLong) {
            p.hide();
            //data for state report
            Cursor cursorState = db.rawQuery("SELECT confirmed, recovered, deaths FROM IndiaLocationTable where state='Jharkhand' AND date in(SELECT date FROM IndiaLocationTable ORDER BY _ID DESC LIMIT 1);",null);
            cursorState.moveToNext();
            confirmedState = cursorState.getInt(0);
            recoveredState = cursorState.getInt(1);
            deathsState = cursorState.getInt(2);



            //data for three graphs in the main activity
            Cursor cursor1 = db.rawQuery("SELECT SUM(confirmed), SUM(recovered), SUM(deaths) FROM LocationTable WHERE country ='India' AND date IN(SELECT date FROM LocationTable ORDER BY _id DESC LIMIT 1);",null);
            cursor1.moveToNext();
            recovered = cursor1.getInt(1);
            deaths = cursor1.getInt(2);
            confirmed =cursor1.getInt(0);
            remaining = confirmed - (recovered+deaths);

            recoveredTextState.setText(String.valueOf(recoveredState));
            confirmedTextState.setText(String.valueOf(confirmedState));
            deathTextState.setText(String.valueOf(deathsState));

            recoveredText.setText(String.valueOf(recovered));
            confirmedText.setText(String.valueOf(confirmed));
            deathText.setText(String.valueOf(deaths));

            ArrayList<PieEntry> list = new ArrayList<>();
            list.add(new PieEntry(recovered,"Recovered"));
            list.add(new PieEntry(deaths,"Deaths"));
            list.add(new PieEntry(remaining,"Under\nTreatment"));



            generatePieChart(list);
            generatedataAndgraphs("India");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
            lineCount = DatabaseUtils.queryNumEntries(db, Contract.LocationTable.TABLE_NAME);
            IndiaLineCount = DatabaseUtils.queryNumEntries(db, Contract.IndiaLocationTable.TABLE_NAME);
            System.out.println("count: "+lineCount);
        }


        @Override
        protected Long doInBackground(Void... voids) {
            try {
                readFromCSV(csvReader,csvReaderIndia,db);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long count = DatabaseUtils.queryNumEntries(db, Contract.LocationTable.TABLE_NAME);
            return count;
        }
    }

    private void generatedataAndgraphs(String country){

        Cursor cursor1 = db.rawQuery("SELECT date, SUM(confirmed), SUM(recovered), SUM(deaths) FROM LocationTable where country ='"+country+"' GROUP BY date ORDER BY _ID;",null);
        confirmedList = new ArrayList<>();
        recoveredList = new ArrayList<>();
        deathList = new ArrayList<>();

        cursor1.moveToNext();
        int prevConfirmed = cursor1.getInt(1);
        int prevRecovered = cursor1.getInt(2);
        int prevDeath = cursor1.getInt(3);

        confirmedList.add(new Entry(0,prevConfirmed));
        recoveredList.add(new Entry(0,prevRecovered));
        deathList.add(new Entry(0,prevDeath));

        for(int i=1; i<cursor1.getCount(); i++){
            cursor1.moveToNext();
            confirmedList.add(new Entry(i,cursor1.getInt(1)-prevConfirmed));
            prevConfirmed = cursor1.getInt(1);
            recoveredList.add(new Entry(i, cursor1.getInt(2)-prevRecovered));
            prevRecovered = cursor1.getInt(2);
            deathList.add(new Entry(i, cursor1.getInt(3)-prevDeath));
            prevDeath = cursor1.getInt(3);
        }

         generateLineGraph(chart1,confirmedList);
        generateLineGraph(chart2,recoveredList);
        generateLineGraph(chart3,deathList);

    }

    private void generateLineGraph(LineChart chart, ArrayList<Entry> list){

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

             setLineChartData(list,chart);

    }

    private void setLineChartData(ArrayList<Entry> values, final LineChart chart){
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

            // text size of values
            set1.setValueTextSize(0f);

            // set the filled area
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
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.fade_blue);
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




}
