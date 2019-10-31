package com.codeshot.skillupfb.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Note implements Serializable {
    private  String title,location,time,des;
    Bitmap imgBitMap;

    public Note(String title, String location, String time, String des, Bitmap imgBitMap) {
        this.title = title;
        this.location = location;
        this.time = time;
        this.des = des;
        this.imgBitMap = imgBitMap;
    }

    public Note(String title, String location, String time, String des) {
        this.title = title;
        this.location = location;
        this.time = time;
        this.des = des;
    }

    public Note(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Bitmap getImgBitMap() {
        return imgBitMap;
    }

    public void setImgBitMap(Bitmap imgBitMap) {
        this.imgBitMap = imgBitMap;
    }
}
