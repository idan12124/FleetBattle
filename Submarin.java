package com.example.idanc.fleetbattle;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by idanc on 17/11/2018.
 */

public class Submarin implements View.OnClickListener {

    static Submarin submarin; // how is clicke now
    final int spase_x; // spase from line
    final int spase_y; // spase from line
    final int num_of_squer; // the nuber of sqer size
    Button button; // present the shep
    Context context; // who call the class
    float x, y; // possishen
    int width, height; // withe and hight
    boolean horizental; // the horizental ro vertical


    public Submarin(Context context, float x, float y, int width, int height, int num_of_squer) {

        this.context = context;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.num_of_squer = num_of_squer;
        this.horizental = true;
        spase_x = (int) context.getResources().getDimension(R.dimen.spase_x);
        spase_y = (int) context.getResources().getDimension(R.dimen.spase_y);

        Log.d("abcd", spase_x + "");

        button = new Button(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        button.setLayoutParams(layoutParams);
        button.setBackground(context.getResources().getDrawable(R.drawable.submarin));
        button.setX(x + spase_x);
        button.setY(calculate_y() + spase_y);
        button.setOnClickListener(this);


    }

    public Button getButton() {
        return button; // get buttuon
    }

    private float calculate_y() {
        // calculate the y posison
        return 90 * y + 3 * y;
    }


    public void change_plase(float x, float y) {
        // change possin to button
        if (check_the_posisin(y, x)) {
            this.x = x;
            this.y = y;
            button.setX(x + spase_x);
            button.setY(calculate_y() + spase_y);
        } else {
            Toast.makeText(context, "you can't put your submarin ther", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        submarin = this;
    }

    public boolean check_the_posisin(float y, float x) {
        // check if the posisin exsist

        if (horizental) {
            if (y > 10 - num_of_squer) return false;
        } else if (!horizental) {
            if ((x - 45) / 80 > 10 - num_of_squer + 1) return false;
        }
        return true;
    }


}
