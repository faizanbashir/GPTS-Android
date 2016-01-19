package com.godpowerturbo.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import api.Database;
import api.Resource;
import api.Session;

public class Welcome extends Activity {
    private static String TAG = Welcome.class.getSimpleName();
    Context mCtx = this;
    private static final int TIMER = 3000;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        session = new Session(mCtx);
        init(TIMER);
    }

    public void init(final int timer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if(!session.isFirstRun()){
                        Thread.sleep(timer);
                    }
                    setUpDB();
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

    private void setUpDB(){
        try{
            if(session.isFirstRun()){
                session.firstRun();
                AssetManager asset = mCtx.getAssets();
                Database db = new Database(mCtx);
                db.open();
                copyCompany(db, asset);
                copyPartnumbers(db, asset);
                copySymptoms(db, asset);
                copyCause(db, asset);
                copyPotentialCause(db, asset);
                db.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void copyCompany(Database db, AssetManager asset) throws SQLException{
        try{
            InputStream in = asset.open(Resource.CSV_COMPANY);
            InputStreamReader ins = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ins);
            String line;
            String[] value;
            while((line = br.readLine()) != null){
                value = line.split(";");
                db.insertCompany(value[0], value[1]);
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void copyPotentialCause(Database db, AssetManager asset) throws SQLException{
        try{
            InputStream in = asset.open(Resource.CSV_POTENTIAL_CAUSES);
            InputStreamReader ins = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ins);
            String line;
            String[] value;
            while((line = br.readLine()) != null){
                value = line.split(";");
                db.insertPotentialCause(value[0], value[1]);
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void copyPartnumbers(Database db, AssetManager asset) throws SQLException{
        try{
            InputStream in = asset.open(Resource.CSV_PARTNUMBERS);
            InputStreamReader ins = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ins);
            String line;
            String[] value;
            while((line = br.readLine()) != null){
                value = line.split(";");
                String image = (value[6] == null  ? null : value[6]);
                db.insertPartnumbers(value[0], value[1], value[2], value[3], value[4], value[5], image);
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void copySymptoms(Database db, AssetManager asset) throws SQLException{
        try{
            InputStream in = asset.open(Resource.CSV_SYMPTOMS);
            InputStreamReader ins = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ins);
            String line;
            String[] value;
            while((line = br.readLine()) != null){
                value = line.split(";");
                db.insertSymptom(value[0], value[1]);
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void copyCause(Database db, AssetManager asset) throws SQLException{
        try{
            InputStream in = asset.open(Resource.CSV_CAUSES);
            InputStreamReader ins = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ins);
            String line;
            String[] value;
            while((line = br.readLine()) != null){
                value = line.split(";");
                db.insertCause(value[0], value[1], value[2], value[3]);
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
