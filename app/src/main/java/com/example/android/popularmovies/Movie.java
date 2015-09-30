package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by Noodle Boy on 8/29/2015.
 */
public class Movie implements Parcelable {

    private UUID mID;
    private String mTitle;
    private String mSynopsis;
    private String mPoster;
    private String mReleaseDate;
    private String mUserRating;
    private String mVoteAverage;
    private String mPopularity;

    public Movie(){

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

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String popularity) {
        mPopularity = popularity;
    }

    private Movie(Parcel in){
        mID = ParcelUuid.CREATOR.createFromParcel(in).getUuid();
        mTitle = in.readString();
        mSynopsis = in.readString();
        mPoster = in.readString();
        mReleaseDate = in.readString();
        mUserRating = in.readString();
        mVoteAverage = in.readString();
        mPopularity = in.readString();

    }

    public void writeToParcel(Parcel out, int flags){
        new ParcelUuid(mID).writeToParcel(out,0);
        out.writeString(mTitle);
        out.writeString(mSynopsis);
        out.writeString(mPoster);
        out.writeString(mReleaseDate);
        out.writeString(mUserRating);
        out.writeString(mVoteAverage);
        out.writeString(mPopularity);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }

        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };

    public int describeContents(){
        return 0;
    }

}
