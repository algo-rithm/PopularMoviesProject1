package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Noodle Boy on 9/12/2015.
 */
public class MoviePagerActivity extends FragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.example.android.popmovies.movie_id";

    private ViewPager mViewPager;
    private List<Movie> mMovies;

    public static Intent newIntent(Context packageContext, UUID movieId){
        Intent intent = new Intent(packageContext, MoviePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pager);

        UUID movieId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_movie_pager_view_pager);

        mMovies = MovieList.get(this).getMovies();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager){

            @Override
            public Fragment getItem(int position){
                Movie movie = mMovies.get(position);
                return MovieFragment.newInstance(movie.getID());
            }

            @Override
            public int getCount(){
                return mMovies.size();
            }


        });

        for(int i = 0; i < mMovies.size(); i++){
            if (mMovies.get(i).getID().equals(movieId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
