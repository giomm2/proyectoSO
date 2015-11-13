package com.example.geo_2.proyectoso;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayListActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ListView listViewServer;
    private ArrayAdapter<String> listAdapter ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listViewServer = (ListView) findViewById( R.id.SongsList);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        String[] song = new String[] { "1. Track01", "2. Track02", "3. Track03", "4. Track04",
                "5. Track05", "6. Track06", "7. Track07"};
        ArrayList<String> SongList = new ArrayList<String>();
        SongList.addAll(Arrays.asList(song));

        listAdapter = new ArrayAdapter<String>(this, R.layout.list_element, SongList);
        listViewServer.setAdapter( listAdapter );
        listViewServer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlayListActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
       // Intent i = new Intent(HomeActivity.this, RetosActivity.class);
       // startActivity(i);
        return super.onOptionsItemSelected(item);
    }
    //Metodo para el navigation drawer
    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_home:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_search:
                                menuItem.setChecked(true);
                                Intent i1 = new Intent(PlayListActivity.this, SearchActivity.class);
                                startActivity(i1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_playlist:
                                menuItem.setChecked(true);
                                Intent i2 = new Intent(PlayListActivity.this, PlayActivity.class);
                                startActivity(i2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_navigation_drawer_myplaylist:
                                menuItem.setChecked(true);
                                Intent i3 = new Intent(PlayListActivity.this, MyPlaylistActivity.class);
                                startActivity(i3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_navigation_drawer_help_and_feedback:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }
}
