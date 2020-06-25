package com.example.covid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ExpandableRecyclerAdapter<CountryParent,CountryChild, CountryParentViewHolder,CountryChildViewHolder> implements Filterable {
    private LayoutInflater mInflater;
    private List<CountryParent> parentListFull;
    private List<CountryParent> parentList;
    Context context;

    public CountryAdapter(Context context, @NonNull List<CountryParent> parentList) {
        super(parentList);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.parentList = parentList;
        parentListFull = new ArrayList<>(parentList);
    }

    @NonNull
    @Override
    public CountryParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View countryParentView = mInflater.inflate(R.layout.country_parent_list_item,parentViewGroup,false);
        return new CountryParentViewHolder(countryParentView);
    }

    @Override
    public void setParentList(@NonNull List<CountryParent> parentList, boolean preserveExpansionState) {
        super.setParentList(this.parentList, preserveExpansionState);
    }

    @NonNull
    @Override
    public CountryChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View countryChildView = mInflater.inflate(R.layout.country_child_list_item,childViewGroup,false);
        return new CountryChildViewHolder(countryChildView,context);
    }

    @Override
    public void onBindParentViewHolder(@NonNull CountryParentViewHolder parentViewHolder, int parentPosition, @NonNull CountryParent parent) {
          parentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull CountryChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull CountryChild child) {
            childViewHolder.bind(child);
       }

    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryParent> filteredList = new ArrayList<>();

            if(constraint == null|| constraint.length() == 0){
                filteredList.addAll(parentListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(CountryParent item : parentListFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            parentList.clear();
            parentList.addAll((List)results.values);
            notifyParentDataSetChanged(false);
        }
    };
}
