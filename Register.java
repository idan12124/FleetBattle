package com.example.idanc.fleetbattle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, age, password; // the data of the user
    String gander;
    Button submit; // submit buuton
    FirebaseAuth mAuth; // referens to firebase auth
    ProgressDialog pd; // dialog
    DatabaseReference dr; // ref to database
    String id; // the id of the users

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.passowrd);
        age = (EditText) findViewById(R.id.age);
        submit = (Button) findViewById(R.id.submit);
        gander = "";
        mAuth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference().child("users");


        submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        pd = ProgressDialog.show(this, "Regiseration", "Creat user plase wait...");
        pd.setCancelable(false);
        pd.show();
        if (checkdata()) {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                id = mAuth.getCurrentUser().getUid();
                                dr.child(id).setValue(getdata());
                                Intent i = new Intent(getApplicationContext(), Start_Page.class);
                                startActivity(i);
                                finish();
                            } else {


                            }
                            pd.dismiss();

                        }
                    });

        } else {
            pd.dismiss();
            Toast.makeText(this, "the inputs are worng", Toast.LENGTH_LONG).show();
        }
    }


    public void wichbuttonclick(View view) {

        // check with radio button was click
        RadioButton radioButton = (RadioButton) view;
        if (radioButton.isChecked()) {
            switch (radioButton.getId()) {
                case R.id.male:
                    gander = "male";
                    break;
                case R.id.female:
                    gander = "female";
                    break;
            }
        }
    }

    public HashMap<String, String> getdata() {
        // returen hasmap of the data from the user

        HashMap<String, String> data = new HashMap<>();
        data.put("name", name.getText().toString());
        data.put("email", email.getText().toString());
        data.put("password", password.getText().toString());
        data.put("age", age.getText().toString());
        data.put("gander", gander);
        data.put("wining", "0");
        data.put("losing", "0");
        data.put("exp", "0");
        return data;
    }


    public boolean checkdata() {
        // check if the inputs are right

        RadioButton r1, r2;
        r1 = (RadioButton) findViewById(R.id.male);
        r2 = (RadioButton) findViewById(R.id.female);
        return name.getText().toString().length() > 2 && password.getText().toString().length() > 5 && age.getText().toString().length() > 0 && this.email.getText().toString().length() > 9 && (r1.isChecked() || r2.isChecked());

    }


}
