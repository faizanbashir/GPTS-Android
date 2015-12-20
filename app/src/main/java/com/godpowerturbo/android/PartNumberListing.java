package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import api.ActionBarListActivity;
import api.Database;
import api.Resource;

public class PartNumberListing extends ActionBarListActivity {
    private static final String TAG = PartNumberListing.class.getSimpleName();
    private Vibrator vibrate;
    ListView mList;
    private Context mCtx = this;
    EditText et;
    ImageButton im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnumberlisting);
        Log.e(TAG, "onCreate");
        setUpActionBar();
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mList = (ListView) findViewById(R.id.list);
        listView();
        registerViews();
    }

    public boolean searchEngine(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained
        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));
        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;
            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }
        return false;
    }

    private void hideKeyboard(){
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void registerViews(){
        et = (EditText) findViewById(R.id.editTextSearch);
        im = (ImageButton) findViewById(R.id.search);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                Log.e(TAG, "Search Module");
                boolean flag;
                List<String> list = new ArrayList<>();
                String query = et.getText().toString();
                /*if(!query.isEmpty()){
                    for(String str : Resource.parts){
                        flag = searchEngine(str, query);
                        if(flag){
                            Log.e(TAG, "Value added: " + str);
                            list.add(str);
                        }
                    }
                    listView(list);
                }else{
                    vibrate();
                    et.setError("Enter a Search Query");
                }*/
            }
        });
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position: " + position + " Id: " + id);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                String part = tv.getText().toString();
                Log.e(TAG, "List Item Value: " + part);
                Bundle bd = new Bundle();
                bd.putString("partnumber", part);
                Intent i = new Intent(PartNumberListing.this, PartNumber.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }

    private void listView(List<String> list){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PartNumberListing.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        arrayAdapter.notifyDataSetChanged();
        vibrate();
        mList.setAdapter(arrayAdapter);
    }

    private void listView() {
        Database db = new Database(mCtx);
        try{
            db.open();
            List<String> company = db.getCompany();
            Log.e(TAG, "DB data: " + company.toString());
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PartNumberListing.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, company);
            mList.setAdapter(arrayAdapter);
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected int getListViewId() {
        return android.R.id.list;
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
                Intent i = new Intent(PartNumberListing.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(PartNumberListing.this, MyService.class));
                android.os.Process.killProcess(Resource.PID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void vibrate(){
        if(vibrate.hasVibrator())
            vibrate.vibrate(Resource.VIBR_TIME);
    }

}
