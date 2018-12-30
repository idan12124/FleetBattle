package com.example.idanc.fleetbattle;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Start_Page extends AppCompatActivity implements View.OnClickListener {

    public static final int GALRY = 0; // resilt code to galry
    DatabaseReference databaseReference; // ref to database
    TextView status; // status of user
    TextView wining, losing, level; // the num of game win and lose and level
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    MediaPlayer mp;// the muzic menger
    Button start; // start game
    ProgressBar progressBar;
    boolean muzicup; // if muzic is up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__page);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        status = (TextView) findViewById(R.id.status);
        start = (Button) findViewById(R.id.start);
        wining = (TextView) findViewById(R.id.wining);
        losing = (TextView) findViewById(R.id.losing);
        progressBar = (ProgressBar) findViewById(R.id.pro);
        level = (TextView) findViewById(R.id.level);

        start.setOnClickListener(this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                settext(name);
                String win = dataSnapshot.child("wining").getValue().toString();
                String lose = dataSnapshot.child("losing").getValue().toString();
                String exp = dataSnapshot.child("exp").getValue().toString();
                setwinandloseandex(win, lose, exp);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        circleImageView = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        mp = MediaPlayer.create(this, R.raw.start_m);
        mp.setLooping(true);
        mp.start();
        muzicup = true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.change_pic:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GALRY);
                break;
            case R.id.mute:
                if (muzicup) {
                    mp.pause();
                    muzicup = !muzicup;
                    item.setTitle("start muzic");
                } else if (!muzicup) {
                    mp.start();
                    muzicup = !muzicup;
                    item.setTitle("mute muzic");
                }
                break;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                Intent o = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(o);
                finish();
                break;
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALRY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            circleImageView.setImageURI(uri);
        }
    }


    public void settext(String name) {
        status.setText("Hi " + name + " are you ready to play");
    }

    public void setwinandloseandex(String win, String lose, String exp) {
        wining.setText("Wining: " + win);
        losing.setText("Losing: " + lose);
        int x = Integer.parseInt(exp);
        int p = x % 100;
        progressBar.setProgress(p);
        if (x / 100 == 0) {
            level.setText("LEVEL: " + 1);
        } else {
            level.setText("LEVEL: " + x / 100);
        }

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getApplicationContext(), Before.class);
        startActivity(i);
        finish();
    }
}
