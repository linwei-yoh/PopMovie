package com.example.android.popmovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivityFragment extends Fragment {

    private Movieadapter mMovieadapter;
    ArrayList<MovieInfo> movieList = new ArrayList<>();

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movietable_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_order) {
            startActivity(new Intent(getActivity(),SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMovieadapter = new Movieadapter(getActivity(),new ArrayList<MovieInfo>());

        GridView gridView = (GridView) rootView.findViewById(R.id.MovieTable);
        gridView.setAdapter(mMovieadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieInfo Item = mMovieadapter.getItem(i);

                Bundle bundle = new Bundle();
                bundle.putParcelable("Movie_Info",Item);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtras(bundle);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void updateMovies() {
        MovieListTask movieTask = new MovieListTask();

        SharedPreferences sharedPrefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());
        String orderType = sharedPrefs.getString(getString(R.string.order_type_key),getString(R.string.order_popular));
        movieTask.execute(orderType);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Picasso.with(getActivity()).cancelTag(this);
    }

    public class MovieListTask extends AsyncTask<String, Void, Void> {

        private final String LOG_TAG = MovieListTask.class.getSimpleName();

        private Void getMovieDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MOVIE_LIST = "results";
            final String MOVIE_ID = "id";
            final String IMG_PATH = "poster_path";

            JSONObject movieListJson = new JSONObject(forecastJsonStr);
            JSONArray movieArray = movieListJson.getJSONArray(MOVIE_LIST);

            movieList.clear();
            for(int i = 0; i < movieArray.length(); i++) {

                JSONObject movieInfo = movieArray.getJSONObject(i);
                String movieId = movieInfo.getString(MOVIE_ID);
                String moviePath = movieInfo.getString(IMG_PATH);

                movieList.add(new MovieInfo(movieId,moviePath));
            }
            return null;
        }

        @Override
        protected Void doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            try {
                final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendEncodedPath(params[0])
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
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                getMovieDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(movieList.size() != 0){
                mMovieadapter.clear();
                mMovieadapter.addAll(movieList);
            }
        }


    }
}

