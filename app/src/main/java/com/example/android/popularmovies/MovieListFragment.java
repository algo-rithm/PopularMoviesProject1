package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import java.util.Comparator;
import java.util.List;

/**
 * Created by Peter Balanesi, @GizmoBeta on 9/1/2015.
 */
public class MovieListFragment extends Fragment {

    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mMovieAdapter;
    public Movie[] mReturnedMovieList;
    static private Comparator<Movie> descPopular;
    static private Comparator<Movie> descRating;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_movie_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        FetchPopularMoviesTask fpmt = new FetchPopularMoviesTask();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        switch (item.getItemId()){
            case R.id.menu_item_sort_by_most_popular:
                MovieList.get(getActivity()).clearMovies();
                fpmt.execute("popularity.desc");
                //String title = getString(R.string.app_name);

                activity.getSupportActionBar().setSubtitle("Most Popular");
                return true;
            case R.id.menu_item_sort_by_highest_rated:
                MovieList.get(getActivity()).clearMovies();
                fpmt.execute("vote_average.desc");


                activity.getSupportActionBar().setSubtitle("Highest Rated");
                return true;
            case R.id.menu_item_sort_by_highest_revenue:
                MovieList.get(getActivity()).clearMovies();
                fpmt.execute("revenue.desc");

                activity.getSupportActionBar().setSubtitle("Highest Revenue");
                return true;
            case R.id.menu_item_sort_by_release_date:
                MovieList.get(getActivity()).clearMovies();
                fpmt.execute("release_date.desc");

                activity.getSupportActionBar().setSubtitle("Release Date");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        /* set the rootView layout to the support.v7.widget RecyclerView. Decided on RecyclerView
         * to utilize the ViewHolder pattern. As far as I have learned, this approach was created
         * to deal with the shortcomings of vanilla GridView and ListView layouts
         */
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mMovieRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_recycler_view);

        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        /* An extended AsyncTask to fetch the movie list utilizing TMDb (themoviedb.org) API.
         * It sets mReturnedMovieList with Movie objects through JSON parsed data.
         * It then runs private method updateUI() which creates the Adapter and sets it to the
         * RecyclerView;
         */
        FetchPopularMoviesTask fpmt = new FetchPopularMoviesTask();
        fpmt.execute("popularity.desc");

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle("Most Popular");

        return rootView;

    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    static {
        descPopular = new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getPopularity().compareTo(rhs.getPopularity());
            }
        };

        descRating = new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getUserRating().compareTo(rhs.getUserRating());
            }
        };
    }

    private void updateUI(){

        MovieList movieList = MovieList.get(getActivity());
        List<Movie> movies = movieList.getMovies();

        if (mMovieAdapter == null){
            mMovieAdapter = new MovieAdapter(movies);
            mMovieRecyclerView.setAdapter(mMovieAdapter);
        } else {
            mMovieAdapter.notifyDataSetChanged();
        }


    }

    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Movie mMovie;
        public ImageView mImageView;

        public MovieHolder(View imageView){
            super(imageView);

            imageView.setOnClickListener(this);
            mImageView = (ImageView) imageView.findViewById(R.id.image_movie_poster);
        }

        public void bindMovie(Movie movie){
            mMovie = movie;
            Uri uri = Uri.parse("http://image.tmdb.org/t/p/w185" + mMovie.getPoster());
            Context context = mImageView.getContext();

            Picasso.with(context).load(uri).into(mImageView);
        }

        @Override
        public void onClick(View v){

            Intent intent = MoviePagerActivity.newIntent(getActivity(), mMovie.getID());
            startActivity(intent);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{


        private List<Movie> mMovies;



        public MovieAdapter(List<Movie> movies){
            mMovies = movies;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_movie_list_view, parent, false);
            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position){


            // Movie movie = mReturnedMovieList[position];

            Movie movie = mMovies.get(position);
            holder.bindMovie(movie);
        }

        @Override
        public int getItemCount(){
            // return mReturnedMovieList.length;
            return mMovies.size();
        }
    }

    private class FetchPopularMoviesTask extends AsyncTask<String,Void, Void>{

        private final String LOG_TAG = FetchPopularMoviesTask.class.getSimpleName();

        private void getPopularMoviesFromJSON(String popMoviesJsonStr) throws JSONException {

            Log.v(LOG_TAG, popMoviesJsonStr);
            final String TMDB_LIST = "results";
            final String TMDB_TITLE = "original_title";
            final String TMDB_SYNOPSIS = "overview";
            final String TMDB_POSTER = "poster_path";
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_VOTE_AVERAGE = "vote_average";
            final String TMDB_POPULARITY = "popularity";

            JSONObject popMovieJson = new JSONObject(popMoviesJsonStr);
            JSONArray popMovieArray = popMovieJson.getJSONArray(TMDB_LIST);

            for (int i = 0; i < popMovieArray.length(); i++) {

                Movie movie = new Movie();
                JSONObject popMovie = popMovieArray.getJSONObject(i);

                movie.setTitle(popMovie.getString(TMDB_TITLE));
                movie.setSynopsis(popMovie.getString(TMDB_SYNOPSIS));
                movie.setPoster(popMovie.getString(TMDB_POSTER));
                movie.setReleaseDate(popMovie.getString(TMDB_RELEASE_DATE));
                movie.setUserRating(popMovie.getString(TMDB_VOTE_AVERAGE));
                movie.setPopularity(popMovie.getString(TMDB_POPULARITY));

                MovieList.get(getActivity()).addMovie(movie);
            }

        }

        protected Void doInBackground(String... params){

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String popMoviesJsonStr = null;
            String sort_by = params[0];
            //String sort_by = "popularity.desc";
            String api_key = "51f4fc55cd0aca9e9c5d43f710768c4f";

            try{

                final String TMDB_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
                final String KEY = "api_key";
                final String SORT_PARAM = "sort_by";

                Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, sort_by)
                        .appendQueryParameter(KEY, api_key)
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null){
                    return null;

                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0){
                    return null;
                }

                popMoviesJsonStr = buffer.toString();


            }   catch (IOException e){
                Log.v(LOG_TAG, "Error ", e);
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
                getPopularMoviesFromJSON(popMoviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return
                    null;


        }

        @Override
        protected void onPostExecute(Void v){
            updateUI();
        }

    }

}
