package com.example.covid19.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.covid19.CountryAdapter;
import com.example.covid19.CountryChild;
import com.example.covid19.CountryParent;
import com.example.covid19.CovidDbHelper;
import com.example.covid19.R;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import static com.example.covid19.Contract.LocationTable.COL_COUNTRY;


public class WorldReportFragment extends Fragment {

    CovidDbHelper covidDbHelper;
    SQLiteDatabase db;

    RecyclerView recyclerView;
    CountryAdapter adapter;

    public static WorldReportFragment newInstance() {
        WorldReportFragment fragment = new WorldReportFragment();
        return fragment;
    }
    public WorldReportFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //for toolbar seaxch option
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_world_report, container, false);
        covidDbHelper = new CovidDbHelper(getContext());
        db = covidDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT country,date, SUM(confirmed), SUM(deaths), SUM(recovered) From LocationTable WHERE date IN(SELECT date FROM LocationTable ORDER BY _ID DESC LIMIT 1) GROUP BY country;",null);
        List<CountryParent> list = new ArrayList<>();
        if(cursor!= null){
            while(cursor.moveToNext()){
                String name =cursor.getString(cursor.getColumnIndex(COL_COUNTRY));
                if(name.equals("Cote d'Ivoire")){
                    continue;
                }
                CountryParent countryParent = new CountryParent(name);
                ArrayList<CountryChild> childList = new ArrayList<>();
                int c = cursor.getInt(2);
                int r = cursor.getInt(3);
                int d =cursor.getInt(4);

                Cursor cursor1 = db.rawQuery("SELECT date, SUM(confirmed) FROM LocationTable where country ='"+countryParent.getName()+"' GROUP BY date ORDER BY _ID;",null);
                ArrayList<Entry> entryList = new ArrayList<>();
                //for first time
                cursor1.moveToNext();
                int prev = cursor1.getInt(1);
                entryList.add(new Entry(0,prev));

                for(int i=1; i<cursor1.getCount(); i++){
                    cursor1.moveToNext();
                    entryList.add(new Entry(i,(cursor1.getInt(1)-prev)));
                    prev = cursor1.getInt(1);
                }

                childList.add(new CountryChild(c,r,d,entryList));
                countryParent.setChildList(childList);


                list.add(countryParent);
            }
        }
        cursor.close();




        recyclerView = (RecyclerView)root.findViewById(R.id.country_list);
        adapter = new CountryAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_serach);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

}
