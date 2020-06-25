package com.example.covid19;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class CountryChild {
    private int confirmedCount;
    private int recoveredCount;
    private int deathCount;
    private ArrayList<Entry> values;

    public CountryChild(int confirmedCount, int recoveredCount, int deathCount, ArrayList<Entry> values) {
        this.confirmedCount = confirmedCount;
        this.recoveredCount = recoveredCount;
        this.deathCount = deathCount;
        this.values = values;
    }

    public CountryChild(int confirmedCount, int recoveredCount, int deathCount) {
        this.confirmedCount = confirmedCount;
        this.recoveredCount = recoveredCount;
        this.deathCount = deathCount;
    }

    public int getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public int getRecoveredCount() {
        return recoveredCount;
    }

    public void setRecoveredCount(int recoveredCount) {
        this.recoveredCount = recoveredCount;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    public ArrayList<Entry> getValues() {
        return values;
    }

    public void setValues(ArrayList<Entry> values) {
        this.values = values;
    }
}
