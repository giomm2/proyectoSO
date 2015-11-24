package com.example.geo_2.proyectoso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Data.DBConnection;
import Domain.User;

public class HomeActivity extends Activity {

    private EditText txtName;
    private User us = null;
    private DBConnection dbConn;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtName = (EditText) findViewById(R.id.TxName);
        dbConn = new DBConnection(HomeActivity.this);


    }

    public void goPlay(View v){
        String name;
        if(txtName.getText().toString().trim().length() > 0) {
            Bundle b = new Bundle();
            b.putString("Name", txtName.getText().toString());
            Intent i = new Intent(HomeActivity.this, LoadingActivity.class);
            i.putExtras(b);
            startActivity(i);
            saveUser(txtName.getText().toString());
            username = txtName.getText().toString();
            HomeActivity.this.finish();
         //   name = dbConn.selectUser(txtName.getText().toString());
          //  Toast.makeText(getApplicationContext(),name + "Bienvenido", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(),"Please enter your Name", Toast.LENGTH_SHORT).show();
        }

    }

    //Metodo que guarda usuario
    public void saveUser(String name){
        try {
            us = new User();
            us.setName(name);
            dbConn.addUser(us);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
