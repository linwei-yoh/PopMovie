package com.example.android.popmovie;

import android.os.Parcel;
import android.os.Parcelable;


public class MovieInfo implements Parcelable{
    String movieID;
    String movieImgPath;

    public MovieInfo(String vID, String Path)
    {
        this.movieID = vID;
        this.movieImgPath = Path;
    }

    private MovieInfo(Parcel in){
        this.movieID = in.readString();
        this.movieImgPath = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieID);
        parcel.writeString(movieImgPath);
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
