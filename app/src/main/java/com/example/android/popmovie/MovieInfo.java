package com.example.android.popmovie;

import android.os.Parcel;
import android.os.Parcelable;


public class MovieInfo implements Parcelable{
    public String movieID;
    public String movieImgPath;
    public String Title;
    public String overView;
    public String voteAverage;
    public String releaseYear;

    public MovieInfo()
    {

    }

    private MovieInfo(Parcel in){
        this.movieID = in.readString();
        this.movieImgPath = in.readString();
        this.Title = in.readString();
        this.overView = in.readString();
        this.voteAverage = in.readString();
        this.releaseYear = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieID);
        parcel.writeString(movieImgPath);
        parcel.writeString(Title);
        parcel.writeString(overView);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseYear);
    }

    //github中的例程里 没有将其定义为static类型 Intent接受时会报错
    public static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>(){

        @Override
        public MovieInfo createFromParcel(Parcel parcel) {
            return new MovieInfo(parcel);
        }

        @Override
        public MovieInfo[] newArray(int i) {
            return new MovieInfo[i];
        }
    };
}
