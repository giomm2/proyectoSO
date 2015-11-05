package com.example.geo_2.proyectoso;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class PlayActivity extends AppCompatActivity {

    private ImageView btnPlay;
    private boolean flag = true;
    private TextView clientName;
    private ActionBar actionBar;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        btnPlay = (ImageView) findViewById(R.id.play_Music);
        switchButton();

        clientName = (TextView) findViewById(R.id.textName);
        Bundle b = getIntent().getExtras();
        String name = b.getString("Name");
        clientName.setText(name);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_drawer);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);
    }

    //Metodo que hace el cambio del icono del boton play/pause
    public void switchButton(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    btnPlay.setImageResource(R.mipmap.ic_pause);
                    flag = false;
                } else {
                    btnPlay.setImageResource(R.mipmap.ic_play);
                    flag = true;
                }
            }
        });
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
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_songs:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                             //   Intent intent2 = new Intent(HomeActivity.this, UserRoutes.class);
                             //   startActivity(intent2);
                                return true;

                            case R.id.item_navigation_drawer_genders:
                                menuItem.setChecked(true);
                              //  Intent intent = new Intent(PlayActivity.this, RetosActivity.class);
                               // startActivity(intent);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_navigation_drawer_playlist:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;

                            case R.id.item_navigation_drawer_help_and_feedback:
                                menuItem.setChecked(true);
                              //  messageClose();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }
}
