package com.godpowerturbo.android;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;

import api.Database;

public class PartNumber extends Activity {
    private static final String TAG = PartNumber.class.getSimpleName();
    private Context mCtx = this;
    String TAG_PARTNUMBER;
    String TAG_ID;
    TextView cmp, pat, app, gpn, mod;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnumber);
        Log.e(TAG, "onCreate");
        registerViews();
    }

    private void registerViews(){
        iv = (ImageView) findViewById(R.id.partImage);
        cmp = (TextView) findViewById(R.id.companyName);
        pat = (TextView) findViewById(R.id.partNumber);
        app = (TextView) findViewById(R.id.application);
        gpn = (TextView) findViewById(R.id.gpno);
        mod = (TextView) findViewById(R.id.model);
        displayImage();
    }

    private void displayImage(){
        TAG_PARTNUMBER = getIntent().getStringExtra("partnumber");
        TAG_ID = getIntent().getStringExtra("_id");
        Log.e(TAG, "PartNumber: " + TAG_PARTNUMBER);
        Database db = new Database(mCtx);
        try{
            db.open();
            Cursor c = db.getDetails(TAG_ID);
            Log.e(TAG, "List Data: " + DatabaseUtils.dumpCursorToString(c));
            setData(c);
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void setData(Cursor c){
        c.moveToFirst();
        Log.e(TAG, "Cursor Data 0th: " + c.getString(0));
        String gpno  = c.getString(c.getColumnIndex("gpno"));
        String company = c.getString(c.getColumnIndex("company"));
        String application = c.getString(c.getColumnIndex("application"));
        String model = c.getString(c.getColumnIndex("model"));
        String partnumber = c.getString(c.getColumnIndex("oemno"));
        String image = c.getString(c.getColumnIndex("image"));
        int i = setImage(image);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setImageResource(i);
        gpn.setText(gpno);
        cmp.setText(company);
        pat.setText(partnumber);
        mod.setText(model);
        app.setText(application);
    }

    private int setImage(String id){
        switch(id){
            case "0":
                return 0;
            case "1":
                return R.drawable.i1;
            case "2":
                return R.drawable.i2;
            case "3":
                return R.drawable.i3;
            case "4":
                return R.drawable.i4;
            case "5":
                return R.drawable.i5;
            case "6":
                return R.drawable.i6;
            case "7":
                return R.drawable.i7;
            case "8":
                return R.drawable.i8;
            case "9":
                return R.drawable.i9;
            case "10":
                return R.drawable.i10;
            case "11":
                return R.drawable.i11;
            case "12":
                return R.drawable.i12;
            case "13":
                return R.drawable.i13;
            case "14":
                return R.drawable.i14;
            case "16":
                return R.drawable.i16;
            case "17":
                return R.drawable.i17;
            case "18":
                return R.drawable.i18;
            case "19":
                return R.drawable.i19;
            case "20":
                return R.drawable.i20;
            case "21":
                return R.drawable.i21;
            case "22":
                return R.drawable.i22;
            case "23":
                return R.drawable.i23;
            case "24":
                return R.drawable.i24;
            case "25":
                return R.drawable.i25;
            default:
                return 0;
        }
    }

}
