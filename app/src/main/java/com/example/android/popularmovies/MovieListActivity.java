package com.example.android.popularmovies;

import android.support.v4.app.Fragment;

/**
 * Created by Noodle Boy on 9/13/2015.
 */
public class MovieListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new MovieListFragment();
    }

}
