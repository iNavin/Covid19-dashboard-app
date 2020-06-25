package com.example.covid19;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

public class CountryParent implements Parent<CountryChild> {
    private String name;
    private double count;
    private ArrayList<CountryChild> childList;

    public CountryParent(String name) {
        this.name = name;
    }

    public CountryParent(String name, ArrayList<CountryChild> childList) {
        this.name = name;
        this.childList = childList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public void setChildList(ArrayList<CountryChild> childList) {
        this.childList = childList;
    }

    @Override
    public List<CountryChild> getChildList() {
        return childList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


}
