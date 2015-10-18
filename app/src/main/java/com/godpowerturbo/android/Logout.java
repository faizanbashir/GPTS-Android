package com.godpowerturbo.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import api.Session;

public class Logout extends Activity {

    private static final String TAG = Logout.class.getSimpleName();
    TextView txtName = null;
    TextView txtEmail = null;
    Button btnLogout;
    private Context mCtx = this;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        registerViews();
    }

    private void registerViews(){
        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        session = new Session(mCtx);
        String str;
        if(!session.getName().isEmpty()){
            str = session.getName();
        }else{
            str = "X";
        }
        Log.e(TAG, "NAME: " + str + " EMAIL: " + session.getEmailAddr());
        txtName.setText(str);
        txtEmail.setText(session.getEmailAddr());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.logOut();
        stopService(new Intent(Logout.this, MyService.class));
        Intent intent = new Intent(Logout.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
