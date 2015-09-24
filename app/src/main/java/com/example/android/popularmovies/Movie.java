package com.example.android.popularmovies;

import java.util.Comparator;
import java.util.UUID;

/**
 * Created by Noodle Boy on 8/29/2015.
 */
public class Movie {


    private UUID mID;
    private String mTitle;
    private String mSynopsis;
    private String mPoster;
    private String mReleaseDate;
    private String mUserRating;
    private String mPopularity;

    static private Comparator<Movie> descPopular;
    static private Comparator<Movie> descRating;

    public Movie(){

        //Generate Universal Unique Identifier UUID
        mID = UUID.randomUUID();
    }


    public UUID getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        mSynopsis = synopsis;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public void setUserRating(String userRating) {
        mUserRating = userRating;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String popularity) {
        mPopularity = popularity;
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

}
