package com.example.baibhav.fireapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register_btn;
    private EditText email_et;
    private EditText password_et;
    private TextView signinpage_tv;


    private ProgressDialog progressdialog;
    // show the process happening in the servers


    private FirebaseAuth firebaseAuth;
    //object required for firebase using

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //showing progress
        progressdialog=new ProgressDialog(this);

        //initialise firebaseAuth object!
        firebaseAuth= FirebaseAuth.getInstance();

        //if a user is already logged in directly go to profile
        if(firebaseAuth.getCurrentUser()!=null){

            //goto profile!!

            //start profile activity!!
            finish();
            startActivity(new Intent(getApplicationContext(),UserProfile.class));
        }

        register_btn=(Button) findViewById(R.id.btn_register);
        email_et=(EditText) findViewById(R.id.et_email);
        password_et=(EditText) findViewById(R.id.et_password);
        signinpage_tv=(TextView) findViewById(R.id.tv_signinpage);

        register_btn.setOnClickListener(this);
        signinpage_tv.setOnClickListener(this);

        //listeners set on two views- the button and the link to go to login activity
    }

    @Override
    public void onClick(View v) {
        if(v==register_btn){
            //function that registers the user to the database
            registerUser();
        }
        if(v==signinpage_tv){

            //will open login activity here!!
            finish();
            startActivity(new Intent(this, LoginPage.class));
        }
    }

    private void registerUser() {

        String email=email_et.getText().toString().trim();
        String password=password_et.getText().toString().trim();


        //check that both the inputs are not null!!
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }


        //show the progress
        progressdialog.setMessage("Registering User... Please wait");
        progressdialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //executes this method on completion of registration being successful
                if(task.isSuccessful()){

                    //user is successfully logged in
                    //start profile activity here
                    Toast.makeText(MainActivity.this, "User Registered  Successfully!!", Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(new Intent(getApplicationContext(),UserProfile.class));

                }

                else{

                    Toast.makeText(MainActivity.this, "Failed to Register. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //takes two arguments + added a listener optionally to track the completion of the process
    }
}
