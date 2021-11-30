package com.example.appcine.Models;

public class MovieModelClass {

    String id, name, img, rdate, overview;

    public MovieModelClass(String id, String name, String img, String rdate, String overview) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.rdate = rdate;
        this.overview = overview;
    }

    public MovieModelClass() {
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
