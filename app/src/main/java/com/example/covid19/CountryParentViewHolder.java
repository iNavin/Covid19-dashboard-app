package com.example.covid19;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

public class CountryParentViewHolder extends ParentViewHolder {
    private TextView name;

    public CountryParentViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.country_name);
    }
    public void bind(CountryParent countryParent){
        name.setText(countryParent.getName());
    }

}
