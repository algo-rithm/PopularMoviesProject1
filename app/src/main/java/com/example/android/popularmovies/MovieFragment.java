package com.example.android.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.UUID;


public class MovieFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";

    private Movie mMovie;
    private ImageView
            mImageView,
            mBackdropImageView;
    private TextView
            mTitleView,
            mSynopsisView,
            mReleaseDateView,
            mPopularityView,
            mVoteRatingView,
            mVoteAverageView ;

    public static MovieFragment newInstance(UUID movieId){
        Bundle args = new Bundle();

        args.putSerializable(ARG_MOVIE_ID, movieId);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID movieId = (UUID) getArguments().getSerializable(ARG_MOVIE_ID);

        mMovie = MovieList.get(getActivity()).getMovie(movieId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_movie_details, container, false);


        mBackdropImageView = (ImageView)v.findViewById(R.id.backdrop_image);
        Uri uriBackdrop = Uri.parse("http://image.tmdb.org/t/p/w342" + mMovie.getBackDrop());
        Context contextBackDrop = mBackdropImageView.getContext();
        Picasso.with(contextBackDrop).load(uriBackdrop).into(mBackdropImageView);

        mTitleView = (TextView)v.findViewById(R.id.movie_detail_title_text);
        mTitleView.setText(mMovie.getTitle());

        mReleaseDateView = (TextView)v.findViewById(R.id.movie_detail_release_date_text);
        mReleaseDateView.setText(mMovie.getReleaseDate());

        mImageView = (ImageView)v.findViewById(R.id.movie_detail_image);
        Uri uri = Uri.parse("http://image.tmdb.org/t/p/w342" + mMovie.getPoster());
        Context context = mImageView.getContext();
        Picasso.with(context).load(uri).into(mImageView);

        mSynopsisView = (TextView)v.findViewById(R.id.movie_detail_overview_text);
        mSynopsisView.setText(mMovie.getSynopsis());

        mPopularityView = (TextView)v.findViewById(R.id.movie_detail_popularity);
        mPopularityView.setText(mMovie.getPopularity());

        mVoteRatingView = (TextView)v.findViewById(R.id.movie_detail_vote_rating);
        mVoteRatingView.setText(mMovie.getUserRating());

        mVoteAverageView = (TextView)v.findViewById(R.id.movie_detail_vote_average);
        mVoteAverageView.setText(mMovie.getVoteAverage());

        return v;
    }
}

