package com.example.geo_2.proyectoso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    private ImageView btnPlay;
    private boolean flag = true;
    private TextView clientName;

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
}
