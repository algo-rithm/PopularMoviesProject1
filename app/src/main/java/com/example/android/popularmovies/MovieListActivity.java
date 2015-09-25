package com.example.android.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Noodle Boy on 9/13/2015.
 */
public class MovieListActivity extends AppCompatActivity implements MovieCollectionAdapter.OnItemClickListener {

    private String[] mMovieCollectionGroups;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private String mActivityTitle;
    //private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mTitle, mDrawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        mActivityTitle = getTitle().toString();
        mDrawerList = (ListView) this.findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //mDrawerList = (RecyclerView) this.findViewById(R.id.left_drawer);

        addDrawerItems();
        setupDrawer();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //mMovieCollectionGroups = getResources().getStringArray(R.array.movie_collection_array);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setHomeButtonEnabled(true);



        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }

    }

    private void addDrawerItems(){
        String[] mcArray = {"Most Popular Collection", "Highest Rated Collection", "Greatest Revenue Collection", "Release Date Collection"};
        mAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mcArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MovieListActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close){
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //case R.id.action_settings:
        //startActivity(new Intent(getActivity(), SettingsActivity.class));
        //return true;



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position){
        selectItem(position);
    }

    private void selectItem(int position){

    }

    @Override
    public void setTitle(CharSequence title){
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    protected Fragment createFragment(){
        return new MovieListFragment();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.fragment_movie_list, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
