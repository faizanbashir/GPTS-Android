package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import api.Session;

public class Welcome extends ActionBarActivity {
    private static String TAG = Welcome.class.getSimpleName();
    Context context = this;
    private static final int TIMER = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init(TIMER);
    }

    public void init(final int timer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(timer);
                    Session session = new Session(context);
                    Log.d(TAG, "USER INFO");
                    Log.d(TAG, "Login Status: " + session.isLoggedIn());
                    if(session.isLoggedIn()){
                        Intent i = new Intent(Welcome.this, Main.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }else{
                        Intent i = new Intent(Welcome.this, Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
