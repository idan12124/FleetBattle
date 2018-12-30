package com.example.idanc.fleetbattle;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by idanc on 20/11/2018.
 */

public class EnemyBoard implements View.OnClickListener {

    Context context; // contaxt for call the class
    LinearLayout linearLayout_; // the linear layout
    FrameLayout frameLayout; // the  frame layout
    int y = 100; // the id
    boolean myturn; // if the enemy turn
    ArrayList<ImageView> arrayList;

    public EnemyBoard(Context context, LinearLayout linearLayout, FrameLayout frameLayout) {
        this.context = context;
        this.linearLayout_ = linearLayout;
        this.frameLayout = frameLayout;
        myturn = true;
        arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            line();
            y++;
        }
    }

    public void line() {
        // creat line fo buttons

        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setGravity(Gravity.CENTER);


        for (int i = 0; i < 10; i++) {
            Button b = new Button(context);
            int x = (int) context.getResources().getDimension(R.dimen.w_h);
            LinearLayout.LayoutParams ButtonParams = new LinearLayout.LayoutParams(x, x);
            b.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            b.setLayoutParams(ButtonParams);
            b.setId(y);

            b.setOnClickListener(this);


            int y = (int) context.getResources().getDimension(R.dimen.one_dp);
            View v = new View(context);
            LinearLayout.LayoutParams ViewParams = new LinearLayout.LayoutParams(y, x);
            v.setBackgroundColor(context.getResources().getColor(R.color.black));
            v.setLayoutParams(ViewParams);

            linearLayout.addView(b);
            linearLayout.addView(v);
        }

        int y = (int) context.getResources().getDimension(R.dimen.one_dp);
        View v = new View(context);
        LinearLayout.LayoutParams ViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, y);
        v.setBackgroundColor(context.getResources().getColor(R.color.black));
        v.setLayoutParams(ViewParams);

        linearLayout_.addView(linearLayout);
        linearLayout_.addView(v);
    }


    @Override
    public void onClick(View view) {

        /*
        LinearLayout.LayoutParams Params= new LinearLayout.LayoutParams(80, 80);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(Params);
        imageView.setImageResource(R.drawable.boom);
        imageView.setX(view.getX());
        imageView.setY(calculate_y((float) view.getId()));
        frameLayout.addView(imageView);
        Toast.makeText(context, view.getX() + "," + calculate_y((float) view.getId()), Toast.LENGTH_LONG).show();
    */
        float id = view.getId();

        Handle_Game.sentmessge(x_y(view.getX(), id % 100));
    }


    private float calculate_y(float y) {
        // calculate the y posison
        return 90 * y + 3 * y;
    }


    public void hide() {
        // hide all the bord and submarins
        frameLayout.setVisibility(View.GONE);
        for (ImageView imageView : arrayList) {
            imageView.setVisibility(View.GONE);
        }
    }

    public void show() {
        // show the board
        frameLayout.setVisibility(View.VISIBLE);
        for (ImageView imageView : arrayList) {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public String x_y(float x, float id) {
        // return string of x and y
        return x + "," + calculate_y(id);
    }

    public void mark(int x, String message) {
        // mark hit or not

        int m = (int) context.getResources().getDimension(R.dimen.pic);
        LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(m, m);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(Params);
        imageView.setX(messageto_x(message) + 5);
        imageView.setY(messageto_y(message) + 5);
        arrayList.add(imageView);

        if (x == 1 || x == 3) {
            imageView.setImageResource(R.drawable.boom);
        } else if (x == 0) {
            imageView.setImageResource(R.drawable.x);
        }
        frameLayout.addView(imageView);
    }

    public float messageto_x(String message) {
        // take from the message the x
        String mx = "";
        for (int i = 0; i < message.length(); i++) {

            if (message.charAt(i) == ',') break;
            mx += message.charAt(i);
        }

        return Float.parseFloat(mx);
    }

    public float messageto_y(String message) {
        // take from the message the y
        String my = "";
        boolean take = false;
        for (int i = 0; i < message.length(); i++) {

            if (take) my += message.charAt(i);
            if (message.charAt(i) == ',') take = true;

        }

        return Float.parseFloat(my);
    }


}
