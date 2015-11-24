package com.example.geo_2.proyectoso;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import Adapters.MyPlayListAdapter;

public class MyPlaylistActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ListView myListView;
    private ArrayAdapter<String> myListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        MyPlaylistActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        myListView = (ListView) findViewById(R.id.myList);
        ArrayList<String> myList = new ArrayList<String>();
        //myList.addAll(Arrays.asList());
        MyPlayListAdapter myAd = new MyPlayListAdapter(myList, this);
        myListAdapter = new ArrayAdapter<String>(this, R.layout.list_element, myList);
        myListView.setAdapter( myListAdapter );
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyPlaylistActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
    }
}
