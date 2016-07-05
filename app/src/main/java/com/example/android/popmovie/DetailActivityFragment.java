package com.example.android.popmovie;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    MovieInfo selMovie;
    String RunTime = new String();

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        selMovie = intent.getParcelableExtra("Movie_Info");

        String BaseUrl = "http://image.tmdb.org/t/p/w185/";
        BaseUrl += selMovie.movieImgPath;

        TextView Title = (TextView) rootView.findViewById(R.id.movieTitel);
        TextView Year = (TextView) rootView.findViewById(R.id.movieYear);
        TextView Rating = (TextView) rootView.findViewById(R.id.movieRating);
        TextView OView = (TextView) rootView.findViewById(R.id.movieOverView);
        ImageView PosterImg = (ImageView) rootView.findViewById(R.id.moviePoster);

        Picasso.with(getContext()).load(BaseUrl).into(PosterImg);
        Title.setText(selMovie.Title);
        Year.setText(selMovie.releaseYear);
        Rating.setText(selMovie.voteAverage + "/10");
        OView.setText(selMovie.overView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMovieRuntime();
    }

    void getMovieRuntime()
    {
        MovieDetail movieDetail = new MovieDetail();
        movieDetail.execute();
    }

    public class MovieDetail extends AsyncTask<String, Void, Void> {


        private Void getMovieDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MOVIE_ID = "runtime";
            JSONObject movieDetailJson = new JSONObject(forecastJsonStr);
            RunTime = movieDetailJson.getString(MOVIE_ID) + "min";

            return null;
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            try {
                final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendEncodedPath(selMovie.movieID)
                        .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIE_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("ReadMe", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ReadMe", "Error closing stream", e);
                    }
                }
            }

            try {
                getMovieDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e("ReadMe", e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ((TextView)getActivity().findViewById(R.id.movieLength)).setText(RunTime);
        }


    }
}
