package com.example.android.popmovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    MovieInfo selMovie;
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Intent intent = getActivity().getIntent();;
//        try{
//            intent = getActivity().getIntent();
//            selMovie = intent.getParcelableExtra("Movie_Info");
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            Log.e("Readme",e.getMessage());
//        }

//        String BaseUrl = "http://image.tmdb.org/t/p/w185/";
//        BaseUrl += selMovie.movieImgPath;
//
//        TextView Title = (TextView) getActivity().findViewById(R.id.movieTitel);
//        TextView Year = (TextView) getActivity().findViewById(R.id.movieYear);
//        TextView ReData = (TextView) getActivity().findViewById(R.id.movieData);
//        TextView Rating = (TextView) getActivity().findViewById(R.id.movieRating);
//        TextView OView = (TextView) getActivity().findViewById(R.id.movieOverView);
//
//        ImageView PosterImg = (ImageView) getActivity().findViewById(R.id.moviePoster);
//        try{
//            Picasso.with(getActivity()).load(BaseUrl).into(PosterImg);
//        }
//        catch(Exception e)
//        {
//            Log.e("ReadMe","MSG");
//        }

//        Title.setText(selMovie.Title);
//        Year.setText(selMovie.releaseYear);
//        ReData.setText(selMovie.releaseDate);
//        Rating.setText(selMovie.voteAverage);
//        OView.setText(selMovie.overView);

//        TextView Length = (TextView) getActivity().findViewById(R.id.movieLength);
//        Length.setText("110");

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
}
