package com.example.idanc.fleetbattle;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by idanc on 17/11/2018.
 */

public class Board implements View.OnClickListener, View.OnLongClickListener {

    Context context; // who call this class
    LinearLayout main; // main linearlayout;
    FrameLayout frameLayout; // framelayoyut
    int y = 0; // the num of line
    Submarin[] submarins;
    ArrayList<ImageView> arrayList;


    public Board(Context context, LinearLayout main, FrameLayout frameLayout) {

        this.context = context;
        this.main = main;
        this.frameLayout = frameLayout;
        for (int i = 0; i < 10; i++) {
            line();
            y++;
        }

        arrayList = new ArrayList<>();
        submarins = new Submarin[5];
        submarins[0] = new Submarin(context, 0, 5, 35, 420, 5);
        submarins[1] = new Submarin(context, 93, 6, 35, 330, 4);
        submarins[2] = new Submarin(context, 186, 7, 35, 240, 3);
        submarins[3] = new Submarin(context, 279, 7, 35, 240, 3);
        submarins[4] = new Submarin(context, 372, 8, 35, 150, 2);

        for (int i = 0; i < 5; i++) {
            frameLayout.addView(submarins[i].getButton());
            submarins[i].getButton().setOnLongClickListener(this);
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
        int m = (int) context.getResources().getDimension(R.dimen.withe_all);
        View v = new View(context);
        LinearLayout.LayoutParams ViewParams = new LinearLayout.LayoutParams(m, y);
        v.setBackgroundColor(context.getResources().getColor(R.color.black));
        v.setLayoutParams(ViewParams);

        main.addView(linearLayout);
        main.addView(v);
    }

    @Override
    public void onClick(View view) {

        float x = view.getX();
        float id = view.getId();


        if (Submarin.submarin != null && check(x, id)) {
            Toast.makeText(context, x + " , " + id, Toast.LENGTH_LONG).show();
            Submarin.submarin.change_plase(x, id);
        }


    }


    public boolean check(float x, float y) {
        //check if not tuch  submarin

        for (Submarin s : submarins) {
            if (Submarin.submarin.horizental && s != Submarin.submarin) {
                if (s.horizental) {
                    if ((s.x <= x && x < s.x + s.width) && ((y <= s.y && s.y < y + Submarin.submarin.num_of_squer) || (y < s.y + s.num_of_squer && s.y + s.num_of_squer <= y + Submarin.submarin.num_of_squer - 1) || (s.y <= y && y < s.y + s.num_of_squer))) {
                        Toast.makeText(context, "you cant piy submatin on submarin", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } else if (!s.horizental) {
                    if ((y <= s.y && s.y < y + Submarin.submarin.num_of_squer) && (s.x <= x && x < s.x + s.width)) {
                        Toast.makeText(context, "you cant piy submatin on submarin", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            } else if (!Submarin.submarin.horizental && s != Submarin.submarin) {

                if (s.horizental) {
                    if ((x <= s.x && s.x < x + Submarin.submarin.width) && (s.y <= y && y < s.y + s.num_of_squer)) {
                        Toast.makeText(context, "you cant piy submatin on submarin", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } else if (!s.horizental) {
                    if ((y == s.y) && ((x <= s.x && s.x < x + Submarin.submarin.width) || (x < s.x + s.width && s.x + s.width <= x + Submarin.submarin.width))) {
                        Toast.makeText(context, "you cant piy submatin on submarin", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }


        }


        return true;
    }

    @Override
    public boolean onLongClick(View view) {

        submarin_for_bottun(view);
        Submarin.submarin.horizental = !Submarin.submarin.horizental;

        if (Submarin.submarin.check_the_posisin(Submarin.submarin.y, Submarin.submarin.x)) {

            int x_ = Submarin.submarin.width;
            Submarin.submarin.width = Submarin.submarin.height;
            Submarin.submarin.height = x_;


            if (check(Submarin.submarin.x, Submarin.submarin.y)) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = Submarin.submarin.width;
                layoutParams.height = Submarin.submarin.height;
                view.setLayoutParams(layoutParams);
                return true;
            } else {
                x_ = Submarin.submarin.width;
                Submarin.submarin.width = Submarin.submarin.height;
                Submarin.submarin.height = x_;
                Submarin.submarin.horizental = !Submarin.submarin.horizental;
                Toast.makeText(context, "you can't shift your submarin ther", Toast.LENGTH_SHORT).show();
            }

        } else {
            Submarin.submarin.horizental = !Submarin.submarin.horizental;
            Toast.makeText(context, "you can't shift your submarin ther", Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    public void submarin_for_bottun(View view) {
        //change the submarin for how clicke long click
        for (Submarin s : submarins) {
            if (s.getButton() == (Button) view) {
                Submarin.submarin = s;
                return;
            }
        }
    }


    public void hide() {
        // hide all the bord and submarins
        for (Submarin s : submarins) {
            s.getButton().setVisibility(View.GONE);
            s.getButton().setEnabled(false);
            Submarin.submarin = null;
        }

        for (ImageView imageView : arrayList) {
            imageView.setVisibility(View.GONE);
        }
        main.setVisibility(View.GONE);
    }

    public void show() {
        // show the board
        for (Submarin s : submarins) {
            s.getButton().setVisibility(View.VISIBLE);
        }

        for (ImageView imageView : arrayList) {
            imageView.setVisibility(View.VISIBLE);
        }

        main.setVisibility(View.VISIBLE);
    }

    public void mark(int x, String message) {
        // mark hit or not

        int m = (int) context.getResources().getDimension(R.dimen.pic);
        LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(m, m);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(Params);
        imageView.setX(messageto_x(message) + 9);
        imageView.setY(messageto_y(message) + 9);
        arrayList.add(imageView);

        if (x == 1) {
            imageView.setImageResource(R.drawable.boom);
            Handle_Game.boom++;
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

        float m = Float.parseFloat(mx);
        Log.d("qwer x", m + "");
        return m;
    }


    public float messageto_y(String message) {
        // take from the message the y
        String my = "";
        boolean take = false;
        for (int i = 0; i < message.length(); i++) {

            if (take) my += message.charAt(i);
            if (message.charAt(i) == ',') take = true;

        }

        float m = Float.parseFloat(my);
        Log.d("qwer y", m + "");
        return m;
    }


    public int hit(String message) {

        float x = messageto_x(message);
        float y = messageto_y(message);

        if (y < 1) {
            y = 0;
        } else {
            y = y / 100 + 1;
        }
        for (Submarin s : submarins) {
            if (s.horizental) {
                if (s.x == x && s.y <= y && y < s.y + s.num_of_squer) return 1;
            } else if (!s.horizental) {
                if (s.y <= y && y < s.y + 1 && s.x <= x && x < s.x + s.width) return 1;
            }
        }

        return 0;
    }


}
