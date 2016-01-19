package com.godpowerturbo.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mikepenz.materialdrawer.util.KeyboardUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import api.Connection;
import api.MySingleton;
import api.Resource;
import api.Session;


public class Login extends Activity {
    private static String TAG = Login.class.getSimpleName();
    EditText username;
    Button signIn, signUp;
    private ProgressDialog dialog;
    Context context = this;
    private Vibrator vibrate;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MOBILE = "mobile";
    private static final String TAG_IP = "ip_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        registerViews();
    }

    private void registerViews(){
        username = (EditText) findViewById(R.id.editTextPhone);
        signIn = (Button) findViewById(R.id.buttonSignIn);
        signUp = (Button) findViewById(R.id.buttonSignUp);
        final AwesomeValidation av = new AwesomeValidation(ValidationStyle.COLORATION);
        av.setColor(Color.YELLOW);
        setValidation(av);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(Login.this);
                boolean isValid = av.validate();
                Connection conn = new Connection(context);
                boolean isConnected = conn.getConnection();
                if(isValid){
                    if(isConnected){
                        dialog = new ProgressDialog(Login.this);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setMessage("Logging In, Please wait...");
                        dialog.setIndeterminate(false);
                        dialog.setCancelable(false);
                        makeStringRequest();
                    }else{
                        Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                        vibrate();
                    }
                }
            }
        });
    }

    private void makeStringRequest(){
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, Resource.VALIDATE_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");
                            if(status.equals(TAG_SUCCESS)){
                                Session session = new Session(context);
                                hideDialog();
                                String user = jObj.getString("user");
                                JSONObject jUser = new JSONObject(user);
                                String activeStatus = jUser.getString("status");
                                if(activeStatus.equals("Active")){
                                    session.setLogin(true);
                                    session.firstDamn(true);
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("log_id", jObj.getString("log_id"));
                                    map.put("status", activeStatus);
                                    map.put("name", jUser.getString("name"));
                                    map.put("mobile", jUser.getString("mobile"));
                                    map.put("email", jUser.getString("email"));
                                    map.put("id", jUser.getString("id"));
                                    session.setUser(map);
                                    Intent i = new Intent(Login.this, Main.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.putExtra("login", true);
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "You have been blocked", Toast.LENGTH_SHORT).show();
                                }
                                //Start Activity
                            }else{
                                hideDialog();
                                Toast.makeText(getApplicationContext(), "Wrong Credentails", Toast.LENGTH_SHORT).show();
                                username.setError("Incorrect Username or Password");
                                vibrate();
                            }
                        }catch(JSONException e){
                            hideDialog();
                            e.printStackTrace();
                            Toast.makeText(getApplication(), "Please try Again", Toast.LENGTH_SHORT).show();
                            vibrate();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_SHORT).show();
                hideDialog();
                vibrate();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String IMEI = getIMEI();
                params.put(TAG_MOBILE, username.getText().toString());
                params.put(TAG_IP, IMEI);
                return params;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(Resource.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        MySingleton.getInstance(this).addToRequestQueue(strReq);
    }

    private void setValidation(AwesomeValidation av){
        av.addValidation(Login.this, R.id.editTextPhone, Patterns.PHONE, R.string.err_phone);
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

    private void vibrate(){
        if(vibrate.hasVibrator()){
            vibrate.vibrate(Resource.VIBR_TIME);
        }
    }

    private String getIMEI(){
        TelephonyManager telMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        return telMgr.getDeviceId();
    }

}