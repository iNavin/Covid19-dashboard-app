package com.example.covid19;

import java.io.Serializable;
import java.util.ArrayList;

public class InfoModel implements Serializable {

    private int image;
    private String title;
    private String desc;
    private ArrayList<String> points;



    public InfoModel(int image, String title, String desc, ArrayList<String> points) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.points = points;
    }

    public InfoModel(int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<String> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<String> points) {
        this.points = points;
    }
}
