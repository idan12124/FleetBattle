package com.example.idanc.fleetbattle;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Before extends AppCompatActivity implements View.OnClickListener {

    static TextView title;
    FrameLayout frameLayout; // framelayout main
    FrameLayout enemyframe; // enemy fram
    LinearLayout linearLayout; // linerlayout of this player
    LinearLayout linearLayout2; // linerlayout of enemy player
    Button button; // submit button
    Handler handler;
    Board board; // the borad of the game
    Clock clock; // timer
    EnemyBoard enemyBoard; // the enamy board
    MediaPlayer mediaPlayer;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before);

        Context context = this;

        title = (TextView) findViewById(R.id.title_b);
        mediaPlayer = MediaPlayer.create(this, R.raw.start_m);
        button = (Button) findViewById(R.id.start_game);
        frameLayout = (FrameLayout) findViewById(R.id.frame_main);
        enemyframe = (FrameLayout) findViewById(R.id.enameframe);
        linearLayout = (LinearLayout) findViewById(R.id.all_lin);
        linearLayout2 = (LinearLayout) findViewById(R.id.all_lin2);
        this.board = new Board(this, linearLayout, frameLayout);
        handler = new Handler();


        clock = new Clock(button, board);
        clock.execute(35);

        button.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        button.setVisibility(View.GONE);
        clock.continue_thred = false;

        enemyBoard = new EnemyBoard(this, linearLayout2, enemyframe);
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.start();
        final Handle_Game handle_game = new Handle_Game(board, enemyBoard, this);
        handle_game.execute();


    }


}






