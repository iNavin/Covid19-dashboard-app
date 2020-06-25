package com.example.covid19;

public class IndiaCSVData {


    private String date;
    private String state;
    private int recovered;
    private int deaths;
    private int confirmed;



    public IndiaCSVData(String date, String state, int recovered, int deaths, int confirmed) {
        this.date = date;
        this.state = state;
        this.recovered = recovered;
        this.deaths = deaths;
        this.confirmed = confirmed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
}
