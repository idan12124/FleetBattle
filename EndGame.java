package com.example.idanc.fleetbattle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EndGame extends AppCompatActivity implements View.OnClickListener {

    TextView win, exp; // the text on screen
    ImageView imagewin; // the image of win
    DatabaseReference databaseReference; // ref to the database
    Button goback; //go back to end of activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        win = (TextView) findViewById(R.id.win);
        exp = (TextView) findViewById(R.id.exp);
        imagewin = (ImageView) findViewById(R.id.imagewin);
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        goback = (Button) findViewById(R.id.goback);
        goback.setOnClickListener(this);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String exp = dataSnapshot.child("exp").getValue().toString();
                Log.d("abab", exp);
                setexp(exp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if (!Handle_Game.win) {

            win.setText("YOU LOSE");
            exp.setText("YOU EARN 5 exp");
            imagewin.setBackgroundResource(R.drawable.lose);
        }


    }


    public void setexp(String exp) {
        int exp1 = Integer.parseInt(exp);
        if (!Handle_Game.win) {
            exp1 += 5;
        } else {
            exp1 += 30;
        }
        String a = exp1 + "";
        databaseReference.child("exp").setValue(a);
    }


    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, Start_Page.class);
        startActivity(i);
        finish();
    }


}
