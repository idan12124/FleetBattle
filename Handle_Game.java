package com.example.idanc.fleetbattle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Created by idanc on 21/11/2018.
 */

public class Handle_Game extends AsyncTask<Void, Integer, Void> {


    static String messgae = ""; // x and y to send
    static int boom = 0; // how musch hit
    static boolean win;
    final String ip = "192.168.121.148";
    final int port = 5556;
    Board board; // board game
    EnemyBoard enemyBoard; // enemy board
    Socket socket; // socket object
    ProgressDialog pd; // dialog
    Context context; // who call the class
    String data = ""; // x and y recv
    boolean loop = true; // conrnue loop
    int ok = 0; // if hit
    int x = 0; // if anemy hit

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String t = "a";

    Intent i = null;


    public Handle_Game(Board board, EnemyBoard enemyBoard, Context context) {
        this.board = board;
        this.enemyBoard = enemyBoard;
        this.context = context;
    }

    public static void sentmessge(String m) {
        messgae = m;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            socket = new Socket(ip, port);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            publishProgress(2);

            String start = dataInputStream.readUTF();

            publishProgress(6);

            publishProgress(4);


            if (start.equals("2")) {
                enemyBoard.myturn = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            //     publishProgress(6);

        }

        while (loop) {
            if (enemyBoard.myturn) {
                // Before.title.setText("THE ENEMY TURN");
                String m = messgae;
                publishProgress(1);
                enemyBoard.myturn = !enemyBoard.myturn;
                while (m.equals(messgae)) {

                }

                try {

                    dataOutputStream.writeUTF(messgae);
                    String text = dataInputStream.readUTF();

                    ok = Integer.parseInt(text);
                    publishProgress(3);

                    if (ok == 3) {
                        loop = false;
                        win = true;
                    }

                    publishProgress(3);

                    Thread.sleep(1000);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } else {
                //     Before.title.setText("YOUR TURN");
                publishProgress(0);
                enemyBoard.myturn = !enemyBoard.myturn;


                try {

                    data = dataInputStream.readUTF();
                    x = board.hit(data);
                    publishProgress(5);
                    Thread.sleep(100); // for time to change boom counter
                    if (boom == 5) {
                        x = 3;
                        loop = false;
                        win = false;
                    }
                    dataOutputStream.writeUTF(x + "");

                    Thread.sleep(1000);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace(); //yes
                }
            }
        }


        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        Integer i = values[0];
        switch (i) {
            case 0:
                board.show();
                enemyBoard.hide();
                break;
            case 1:
                enemyBoard.show();
                board.hide();
                break;
            case 2:
                pd = ProgressDialog.show(context, "CONNECT TO SERVER", "Pleas wait while looking for player...");
                pd.setCancelable(false);
                pd.show();
                break;
            case 3:
                enemyBoard.mark(ok, messgae);
                break;
            case 4:
                pd.dismiss();
                break;
            case 5:
                board.mark(x, data);
                break;

        }


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        i = new Intent(context, EndGame.class);
        context.startActivity(i);
        ((Activity) context).finish();
    }
}




