package com.example.geo_2.proyectoso;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;

import Adapters.MyAdapter;
import Data.SocketConnection;

public class PlayListActivity extends AppCompatActivity{


    private ActionBar actionBar;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ListView listViewServer;
    private ArrayList<String> songList = new ArrayList<String>();
    private MyAdapter myAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);


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

        myAdapter = new MyAdapter(PlayListActivity.this,R.layout.list_row , songList);
        listViewServer = (ListView) findViewById( R.id.SongsList);
        listViewServer.setItemsCanFocus(false);
        listViewServer.setAdapter(myAdapter);
        listViewServer.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i("List View Clicked", "**********");
                Toast.makeText(PlayListActivity.this,
                        "Click:" + position, Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(PlayListActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

        fillListView();
    }

    public void fillListView(){
        for(int i =0; i < 20; i++){
            songList.add("Cancion "+ i);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
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
                                confirmClose();
                                return true;
                        }
                        return true;
                    }
                });
    }

    //Metodo que muestra el dialog
    public void confirmClose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayListActivity.this);
        builder.setMessage("Do you want close session?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      closeSession();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Close Session");
        alert.show();
    }

    //Metodo que cierra la sesion
    public void closeSession(){
        Intent i = new Intent(PlayListActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
