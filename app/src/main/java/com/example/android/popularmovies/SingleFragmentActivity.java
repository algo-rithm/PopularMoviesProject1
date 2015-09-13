package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Peter F Balanesi, @gizmobeta, on 9/1/2015.
 *
 * This abstract class was created to DRY out MovieActivity and MovieListActivity. Both Activity
 * classes host a single fragment. This activity uses a generic container, so that it can be reused
 * by any implementing fragment.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }

}
