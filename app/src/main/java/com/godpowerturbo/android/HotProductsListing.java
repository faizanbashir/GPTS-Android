package com.godpowerturbo.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.ActionBarListActivity;
import api.Connection;
import api.Model;
import api.MySingleton;
import api.Resource;

public class HotProductsListing extends ActionBarListActivity {
    private static final String TAG = HotProductsListing.class.getSimpleName();
    private Context mCtx = this;
    private ProgressDialog dialog;
    DrawerLayout dw;
    PercentRelativeLayout rl, l;
    android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    EditText searchbox;
    ImageButton nav, nav_home, nav_catalogue, nav_upcomingevents, nav_newlaunch,
            nav_turbofailure, nav_aboutus, nav_enquiry, nav_faq, nav_feedback,
            nav_search;
    private List<Model> dataList = new ArrayList<>();
    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        registerViews();
        listView();
    }

    private void registerViews(){
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                String ttitle = ((TextView) view.findViewById(R.id.title)).getText().toString();
                String tdescription = ((TextView) view.findViewById(R.id.description)).getText().toString();
                Bundle b = new Bundle();
                b.putString("id", tid);
                b.putString("description", tdescription);
                b.putString("title", ttitle);
                b.putString("loc", "1");
                Intent i = new Intent(HotProductsListing.this, BlackBoard.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
        //Drawer Buttons
        nav_home = (ImageButton) findViewById(R.id.nav_home);
        nav_catalogue = (ImageButton) findViewById(R.id.nav_catalogue);
        nav_upcomingevents = (ImageButton) findViewById(R.id.nav_upcomingevents);
        nav_newlaunch = (ImageButton) findViewById(R.id.nav_newlaunch);
        nav_turbofailure = (ImageButton) findViewById(R.id.nav_turbofailure);
        nav_aboutus = (ImageButton) findViewById(R.id.nav_aboutus);
        nav_enquiry = (ImageButton) findViewById(R.id.nav_enquiry);
        nav_faq = (ImageButton) findViewById(R.id.nav_faq);
        nav_feedback = (ImageButton) findViewById(R.id.nav_feedback);
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HotProductsListing.this, Main.class);
                startActivity(i);
            }
        });
        nav_catalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HotProductsListing.this, CompanyListing.class);
                startActivity(i);
            }
        });
        nav_upcomingevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(HotProductsListing.this, EventsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nav_newlaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(HotProductsListing.this, HotProductsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nav_turbofailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HotProductsListing.this, Troubleshootlisting.class);
                startActivity(i);
            }
        });
        nav_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HotProductsListing.this, About.class);
                startActivity(i);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HotProductsListing.this, Contact.class);
                startActivity(i);
            }
        });
        nav_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HotProductsListing.this, Faq.class);
                startActivity(i);
            }
        });
        nav_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HotProductsListing.this, Feedback.class);
                startActivity(i);
            }
        });
        dw = (DrawerLayout) findViewById(R.id.nav_main);
        rl = (PercentRelativeLayout) findViewById(R.id.nav_left);
        pk();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void pk(){
        ActionBar idk = getSupportActionBar();
        idk.setDisplayHomeAsUpEnabled(false);
        idk.setDisplayShowTitleEnabled(false);
        idk.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        idk.setStackedBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.header, null);
        idk.setCustomView(mCustomView);
        idk.setDisplayShowCustomEnabled(true);
        mDrawerToggle = new android.support.v4.app.ActionBarDrawerToggle(this, dw, R.drawable.option, 0, 0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                l.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                l.setVisibility(View.VISIBLE);
            }
        };
        dw.setDrawerListener(mDrawerToggle);
        l = (PercentRelativeLayout) mCustomView.findViewById(R.id.linear_search);
        nav = (ImageButton) mCustomView.findViewById(R.id.nav_menu);
        nav_search = (ImageButton) mCustomView.findViewById(R.id.search);
        searchbox = (EditText) mCustomView.findViewById(R.id.query);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dw.isDrawerOpen(Gravity.LEFT)){
                    l.setVisibility(View.INVISIBLE);
                    dw.openDrawer(rl);
                }else{
                    l.setVisibility(View.VISIBLE);
                    dw.closeDrawer(rl);
                }

            }
        });
        nav_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchbox.getText().toString();
                Bundle bd = new Bundle();
                bd.putString("query", query);
                Intent i = new Intent(HotProductsListing.this, PartNumberListing.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }

    private void listView(){
        adapter = new CustomListAdapter(HotProductsListing.this, dataList);
        dialog = new ProgressDialog(HotProductsListing.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading Hot Products...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        showDialog();
        makeJsonRequest();
        listView.setAdapter(adapter);
    }

    private void makeJsonRequest(){
        JsonObjectRequest jsonReq = new JsonObjectRequest(Resource.FETCH_HOT_PRODUCTS,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            JSONArray obj = response.getJSONArray("product_parts");
                            // Parsing json
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject jok = obj.getJSONObject(i);
                                String id = jok.getString("id");
                                String title = jok.getString("title");
                                String description = jok.getString("description");
                                String created = jok.getString("created_at");
                                String image = Resource.FETCH_HOT_PRODUCT_IMAGES + id + ".png";
                                Model mod = new Model(id, title, description, created, image);
                                mod.setTitle(title);
                                mod.setThumbnailUrl(image);

                                // adding mod to movies array
                                dataList.add(mod);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideDialog();
                Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(HotProductsListing.this, Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy(Resource.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonReq.setRetryPolicy(policy);
        MySingleton.getInstance(mCtx).addToRequestQueue(jsonReq);
    }

    @Override
    protected int getListViewId() {
        return android.R.id.list;
    }

    private void showDialog(){
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    private void hideDialog(){
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
