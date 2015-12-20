package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import api.ActionBarListActivity;
import api.Database;
import api.Resource;

public class PartListing extends ActionBarListActivity {
    private static final String TAG = PartListing.class.getSimpleName();
    private String company;
    private Context mCtx = this;
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        setUpActionBar();
        mList = (ListView) findViewById(R.id.list);
        listView();
    }

    private void listView(){
        Database db = new Database(mCtx);
        List<String> a = new ArrayList<>();
        try{
            db.open();
            Cursor c = db.getPartnumbers(company);
            List<String> results = new ArrayList<>();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                Log.e(TAG, "Loop data: " + c.getString(c.getColumnIndex("oemno")));
                Log.e(TAG, "Loop data: " + c.getString(c.getColumnIndex("_id")));
                a.add(c.getString(c.getColumnIndex("_id")));
                results.add(c.getString(c.getColumnIndex("oemno")));
            }
            ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(PartListing.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, results);
            mList.setAdapter(arrayAdapter);
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        registerView(a);
    }

    private void registerView(final List<String> a){
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "Position: " + position + " Id: " + id);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                String part = tv.getText().toString();
                Log.e(TAG, "List Item Value: " + part);
                Log.e(TAG, "Id: " + a.get(position));
                Bundle bd = new Bundle();
                bd.putString("_id", a.get(position));
                bd.putString("partnumber", part);
                Intent i = new Intent(PartListing.this, PartNumber.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }

    private void setUpActionBar(){
        company = getIntent().getStringExtra("company");
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setTitle(company);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.menu_logout:
                Intent i = new Intent(PartListing.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(PartListing.this, MyService.class));
                android.os.Process.killProcess(Resource.PID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getListViewId() {
        return 0;
    }

}
