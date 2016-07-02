package com.example.android.popmovie;

/**
 * Created by ESON on 2016/7/2.
 */
public class MovieItem {

    String movieID = "";
    String movieTitle = "";
    String imgPath = "";

    public MovieItem(String vID, String vTitle ,String vPath)
    {
        this.movieID = vID;
        this.movieTitle = vTitle;
        this.imgPath = vPath;
    }

}
