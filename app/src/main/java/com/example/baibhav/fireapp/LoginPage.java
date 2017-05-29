package com.example.baibhav.fireapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {


    //declarations
    private Button loginbtn_btn;
    private EditText loginemail_et;
    private EditText loginpassword_et;
    private TextView registerpage_tv;


    private ProgressDialog progressdialog;
    // show the process happening in the servers

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //initialize the firebase object
        firebaseAuth=FirebaseAuth.getInstance();

        //if user is already logged in go directly to userprofile
        if(firebaseAuth.getCurrentUser()!=null){

            //goto profile!!

            //start profile activity!!
            finish();
            startActivity(new Intent(getApplicationContext(),UserProfile.class));
        }

        loginbtn_btn=(Button) findViewById(R.id.btn_loginbtn);
        loginemail_et=(EditText) findViewById(R.id.et_loginemail);
        loginpassword_et=(EditText) findViewById(R.id.et_loginpassword);
        registerpage_tv=(TextView) findViewById(R.id.tv_registerpage);

        //need to show the progress while connecting to the server!!
        progressdialog=new ProgressDialog(this);

        //setting listeners for both the button and the register page link!!
        loginbtn_btn.setOnClickListener(this);
        registerpage_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==loginbtn_btn){


            //created a separate function to log in the users
            loginuser();



        }

        if(v==registerpage_tv){

            //goto register activity
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }
    }

    private void loginuser() {


        //get email and password as strings from the two inputs
        String loginemail=loginemail_et.getText().toString().trim();
        String loginpassword=loginpassword_et.getText().toString().trim();


        //check if Email is empty

        if(TextUtils.isEmpty(loginemail)) {
            //email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;

        }
            //check if password is empty

            if(TextUtils.isEmpty(loginpassword)){
                //password is empty
                Toast.makeText(this, R.string.please_enter_password, Toast.LENGTH_SHORT).show();
                return;
            }
        //otherwise...
        progressdialog.setMessage("Loggin In User... Please wait");
        progressdialog.show();

        //login the user with the help of Email and password
        //the add onCompleteListener le chahi process successful bhayo ki bhaena hercha!!
        firebaseAuth.signInWithEmailAndPassword(loginemail,loginpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progress dialogue remove garne
                progressdialog.dismiss();

                if(task.isSuccessful()){

                    //start profile activity!!
                    finish();
                    startActivity(new Intent(getApplicationContext(),UserProfile.class));
                }
            }
        });

    }
}
