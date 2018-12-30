package com.example.idanc.fleetbattle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText email, password; // data from user
    Button submit; // submit buttun
    ProgressDialog pd; // dialog
    FirebaseAuth mAuth; // ref to auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email_log);
        password = (EditText) findViewById(R.id.passowrd_log);
        submit = (Button) findViewById(R.id.Log);
        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        pd = ProgressDialog.show(this, "LOG-IN", "Pleas wait log-in...");
        pd.setCancelable(false);
        pd.show();
        if (checkinput()) {

            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(getApplicationContext(), Start_Page.class);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), " worng inputs ", Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        }
                    });

        } else {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), " worng inputs ", Toast.LENGTH_LONG).show();
        }

    }


    public boolean checkinput() {
        // check if the input nare rights

        return email.getText().toString().length() > 7 && password.getText().toString().length() > 5;

    }
}
