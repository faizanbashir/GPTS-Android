package com.godpowerturbo.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import api.Connection;
import api.MySingleton;
import api.Resource;


public class Register extends Activity {
    private static String TAG = Register.class.getSimpleName();
    EditText name, phone, email;
    Button signIn, signUp;
    private ProgressDialog dialog;
    private Vibrator vibrate;
    Context context = this;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_NAME = "name";
    private static final String TAG_MOBILE = "mobile";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_IP = "ip_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        registerViews();
    }

    private void registerViews(){
        name = (EditText) findViewById(R.id.editTextName);
        phone = (EditText) findViewById(R.id.editTextPhone);
        email = (EditText) findViewById(R.id.editTextEmail);
        signUp = (Button) findViewById(R.id.buttonSignUp);
        signIn = (Button) findViewById(R.id.buttonSignIn);
        final AwesomeValidation av = new AwesomeValidation(ValidationStyle.COLORATION);
        av.setColor(Color.YELLOW);
        setValidation(av);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = av.validate();
                Log.e(TAG, "av.validate(): " + isValid);
                Connection conn = new Connection(context);
                boolean isConnected = conn.getConnection();
                if(isValid){
                    if(isConnected){
                        dialog = new ProgressDialog(Register.this);
                        dialog.setMessage("Registering User, Please wait...");
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
        showdialog();
        final String TAG = "Register";
        final StringRequest strReq = new StringRequest(Request.Method.POST, Resource.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Request Response: " + response);
                        hidedialog();
                        try{
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");
                            if(status.equals(TAG_SUCCESS)){
                                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this, Login.class));
                            }else{
                                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                                vibrate();
                            }
                        }catch(JSONException e){
                            hidedialog();
                            e.printStackTrace();Toast.makeText(getApplication(), "Please try Again", Toast.LENGTH_SHORT).show();
                            vibrate();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidedialog();
                Log.e(TAG, "Error Response: " + error.toString());
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                vibrate();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String IMEI = getIMEI();
                params.put(TAG_NAME, name.getText().toString());
                params.put(TAG_MOBILE, phone.getText().toString());
                params.put(TAG_EMAIL, email.getText().toString());
                params.put(TAG_IP, IMEI);
                Log.e(TAG, "Sending data :" + params.values());
                return params;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(Resource.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        MySingleton.getInstance(this).addToRequestQueue(strReq);
    }

    private void setValidation(AwesomeValidation av){
        av.addValidation(Register.this, R.id.editTextPhone, Patterns.PHONE, R.string.err_phone);
        av.addValidation(Register.this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        av.addValidation(Register.this, R.id.editTextName, "^[a-z A-z\\\\s]+", R.string.err_name);
    }

    private void showdialog(){
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    private void hidedialog(){
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