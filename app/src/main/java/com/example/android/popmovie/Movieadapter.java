package com.example.android.popmovie;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Movieadapter extends ArrayAdapter<MovieItem> {
    private final Context context;

    public Movieadapter(Activity context, ArrayList<MovieItem> MovieItems) {
        super(context, 0, MovieItems);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        MovieItem movieInfo = getItem(position);

        ImageView iconView = (ImageView) convertView.findViewById(R.id.movieImg);
        Picasso.with(context)
                .load(R.id.movieImg)
//                .error(R.drawable.error)
                .fit()
                .tag(context)
                .into(view);
        iconView.setImageResource(movieInfo.imgPath); //改为Picasso

        TextView versionNameView = (TextView) convertView.findViewById(R.id.movieTitle);
        versionNameView.setText(movieInfo.movieTitle);

        return view;
    }
}

