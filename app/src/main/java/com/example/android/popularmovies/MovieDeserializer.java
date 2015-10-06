package com.example.android.popularmovies;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Noodle Boy on 10/5/2015.
 */
public class MovieDeserializer implements JsonDeserializer<Movie> {

    @Override
    public Movie deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException{

        final JsonObject jsonObject = json.getAsJsonObject();

        JsonElement titleElement = jsonObject.get("original_title");
        final String title = titleElement.getAsString();

        final Movie movie = new Movie();
        movie.setTitle(title);
        //movie.setPoster(poster);
        //movie.setVoteAverage(voteAverage);
        //movie.setUserRating(userRating);
        //movie.setReleaseDate(releaseDate);
        //movie.setSynopsis(synopsis);

        return movie;
    }
}
