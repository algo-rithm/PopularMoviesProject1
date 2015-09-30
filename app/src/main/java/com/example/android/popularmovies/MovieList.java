package com.example.android.popularmovies;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

/**
 * Created by Noodle Boy on 9/12/2015.
 */
public class MovieList {

    private static MovieList sMovieList;
    private ArrayList<Movie> mMovies;


    public static MovieList get(Context context){
        if (sMovieList == null) {
            sMovieList = new MovieList(context);
        }

        return sMovieList;

    }

    static final Comparator<Movie> POPULARITY_ORDER = new Comparator<Movie>() {
        @Override
        public int compare(Movie lhs, Movie rhs) {
            return lhs.getPopularity().compareTo(rhs.getPopularity());
        }
    };

    public void setPopularOrder(){
        Collections.sort(getMovies(), POPULARITY_ORDER);
    }

    static final Comparator<Movie> VOTING_ORDER = new Comparator<Movie>() {
        @Override
        public int compare(Movie lhs, Movie rhs) {
            return lhs.getUserRating().compareTo(rhs.getUserRating());
        }
    };

    public void setRatingsOrder(){
        Collections.sort(getMovies(), VOTING_ORDER);
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

    public ArrayList<Movie> getMovies(){
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
