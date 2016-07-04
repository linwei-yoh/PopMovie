package com.example.android.popmovie;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Movieadapter extends ArrayAdapter<MovieInfo> {
    private final Context context;

    public Movieadapter(Activity context, ArrayList<MovieInfo> MovieItems) {
        super(context, 0, MovieItems);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieInfo MovieItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movieblock, parent, false);
        }

        String BaseUrl = "http://image.tmdb.org/t/p/w185/";
        BaseUrl += MovieItem.movieImgPath;

        ImageView iconView = (ImageView) convertView.findViewById(R.id.Movie_Img);
        Picasso.with(context)
                .load(BaseUrl)
//                .error(R.drawable.error)
                .fit()
                .tag(context)
                .into(iconView);

        return convertView;
    }
}

