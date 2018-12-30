package com.example.idanc.fleetbattle;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

/**
 * Created by idanc on 19/11/2018.
 */

public class Clock extends AsyncTask<Integer, Integer, String> {

    int num;// the num fo sec;
    Boolean continue_thred;// stop the thred
    Button button; // the view to update
    Board board; // the board
    String text = "\n" + "Start Game"; // the str that update


    public Clock(Button button, Board board) {
        this.button = button;
        this.board = board;
        continue_thred = true;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        num = integers[0];
        while (continue_thred && num >= 0) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(num);
            num--;
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        button.setText(values[0] + "'s" + text);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (continue_thred) {
            button.setVisibility(View.GONE);
            board.hide();
        }
    }
}
