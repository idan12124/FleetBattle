package com.example.idanc.fleetbattle;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mp;// the muzic menger
    Button muzicb, regiserb, login; // button of muzic
    boolean muzicup; // if muzic is up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mp = MediaPlayer.create(this, R.raw.start_m);
        mp.setLooping(true);
        mp.start();
        muzicup = true;


        muzicb = (Button) findViewById(R.id.muzicb);
        muzicb.setOnClickListener(this);

        regiserb = (Button) findViewById(R.id.registerb);
        login = (Button) findViewById(R.id.login1);
        regiserb.setOnClickListener(this);
        login.setOnClickListener(this);


    }


    @Override
    protected void onPause() {
        super.onPause();
        mp.release();
    }

    @Override
    public void onClick(View view) {
        if (view == muzicb) {
            // stop and unstp the muzic
            if (muzicup) {
                mp.pause();
                muzicup = !muzicup;
                muzicb.setAlpha(0.4f);
            } else if (!muzicup) {
                mp.start();
                muzicup = !muzicup;
                muzicb.setAlpha(1);
            }
        } else if (view == regiserb) {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        } else if (view == login) {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }


    }


}
