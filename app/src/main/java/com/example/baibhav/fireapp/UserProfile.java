package com.example.baibhav.fireapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    //declarations

    private FirebaseAuth firebaseAuth;
    private Button logoutbtn_btn;
    private TextView userprofile_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //initialise firebase object
        firebaseAuth=FirebaseAuth.getInstance();

        // if the user is logged out then goto the main registration page
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();

        logoutbtn_btn=(Button) findViewById(R.id.btn_logoutbtn);
        userprofile_tv=(TextView) findViewById(R.id.tv_userprofile);

        userprofile_tv.setText("Welcome"+user.getEmail());


        logoutbtn_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==logoutbtn_btn){


            //if log out button pressed!!
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginPage.class));
        }
    }
}
