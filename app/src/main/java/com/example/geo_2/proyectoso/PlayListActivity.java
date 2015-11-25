package com.example.geo_2.proyectoso;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;


import java.util.ArrayList;

import Domain.Track;

public class PlayListActivity extends AppCompatActivity{


    private ActionBar actionBar;
    private Toolbar toolbar;
    private int idUser;
    private String username;
    private DrawerLayout drawerLayout;
    private ListView listViewServer;
    //private ArrayList<Track> songList = new ArrayList<Track>();
    //private MyAdapter myAdapter;
    private ArrayAdapter<String> adapterSend;
   // private DBConnection dbConn;
    //CustomAdapter customAdapter = null;
    MyCustomAdapter dataAdapter = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        Bundle b = getIntent().getExtras();
        idUser =  b.getInt("ID_USER");
        username = b.getString("Name");

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);


        //fillListView();
        displayListView();
        checkButtonClick();
    }

    private void displayListView() {

        //Array list of countries
        ArrayList<Track> TrackList = new ArrayList<Track>();
        Track tr = new Track();
        for(int i =0; i < 15; i++){
            TrackList.add(i, tr );
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.list_row, TrackList);
        ListView listView = (ListView) findViewById(R.id.SongsList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Track track = (Track) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + track.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Track> {

        private ArrayList<Track> TrackList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Track> TrackList) {
            super(context, textViewResourceId, TrackList);
            this.TrackList = new ArrayList<Track>();
            this.TrackList.addAll(TrackList);
        }

        private class ViewHolder {
            TextView title;
            CheckBox checkbox;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_row, null);

                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title_song);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.chk_add);
                convertView.setTag(holder);

                holder.checkbox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Track track = (Track) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        track.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Track track = TrackList.get(position);
            holder.checkbox.setText("Add");
            holder.title.setText(track.getName());
            holder.checkbox.setChecked(track.isSelected());
            holder.checkbox.setTag(track);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.btnSend);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Track> TrackList = dataAdapter.TrackList;
                for(int i=0;i<TrackList.size();i++){
                    Track track = TrackList.get(i);
                    if(track.isSelected()){
                        responseText.append("\n" + track.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

    //Metodo que llena el list view con los elementos existentes
    public void fillListView(){
        String track = "1";
        for(int i =0; i < 20; i++){
         // songList.add(i, track);
        }

    }
    // Metodo que me inserta la cancion
    public void saveSong(String song){
        try{
            Track tr = new Track();
            tr.setName(song);
         //   dbConn.addSong(tr, );
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // Metodo que me inserta la cancion
    public void addSongDB(View view){
        SparseBooleanArray checked = listViewServer.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        String song;
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i))
                selectedItems.add(adapterSend.getItem(position));
        }
        for (int i = 0; i < selectedItems.size(); i++) {
            song = selectedItems.get(i);
        //    dbConn.addSong(song,username);
        }

        Toast.makeText(this, "SI si sirvo", Toast.LENGTH_LONG);
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
