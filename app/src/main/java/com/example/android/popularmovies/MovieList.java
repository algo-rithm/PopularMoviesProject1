package com.example.android.popularmovies;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Noodle Boy on 9/12/2015.
 */
public class MovieList {

    private static MovieList sMovieList;
    private List<Movie> mMovies;


    public static MovieList get(Context context){
        if (sMovieList == null) {
            sMovieList = new MovieList(context);
        }

        return sMovieList;

    }

    private MovieList(Context context){
        mMovies = new ArrayList<>();
    }

    public void clearMovies(){
        mMovies.clear();
    }

    public void addMovie(Movie m){
        mMovies.add(m);
    }

    public List<Movie> getMovies(){
        return mMovies;
    }

    public Movie getMovie(UUID id){
        for (Movie movie : mMovies){
            if (movie.getID().equals(id)) {
                return movie;
            }
        }
        return null;
    }

}
