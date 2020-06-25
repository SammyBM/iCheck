package com.example.icheck_app_final;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import labs.QueueSingleton;
import labs.UserLab;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    EditText mEditTextUsername;
    EditText mEditTextPassword;
    Button mButtonSignIn;
    TextView mTextViewForgotPassword;
    Button mButtonCreateAccount;

    JsonRequest mJsonRequest;

    SharedPreferences file;

    public static double  COORD_LAT;
    public static double  COORD_LONG;

    private static final int REQUEST_NEW_USER = 1;
    private static final String FILE_NAME = "check it session";

    private static final String CURRENT_USER =  "username";

    public static Intent newInstance(Context context){
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadPosition();

        file = this.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        if(file.contains(CURRENT_USER)){
            UserLab.get().getCurrentUser().setUsername(file.getString(CURRENT_USER, null));
            Intent intent = MainActivity.newInstance(this);
            startActivity(intent);
            finish();
        }

        //wiring up
        mEditTextUsername= findViewById(R.id.sign_in_username_input);
        mEditTextPassword = findViewById(R.id.sign_in_password_input);
        mButtonSignIn= findViewById(R.id.sign_in_start_button);
        mButtonCreateAccount= findViewById(R.id.sign_in_create_button);
        mTextViewForgotPassword = findViewById(R.id.forget_password_label);

        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.newInstance(v.getContext());
                startActivityForResult(intent, REQUEST_NEW_USER);
            }
        });

        mTextViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ForgetPasswordActivity.newInstance(v.getContext());
                startActivity(intent);
            }
        });
    }


    private void signIn(){
        if (!validate())
            return;
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();

        //Begin query on server by Volley
        String url = "https://checkitdatabase.000webhostapp.com/login.php?user=" + username +
                "&pwd=" + password ;
        mJsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        QueueSingleton.get(this).addToRequestQueue(mJsonRequest);
    }

    private boolean validate(){
        boolean validation = true;
        if(mEditTextUsername.getText().toString().isEmpty() ){
            validation= false;
            mEditTextUsername.setError("Username is required");
        }
        if(mEditTextPassword.getText().toString().isEmpty() ){
            validation= false;
            mEditTextPassword.setError("Password is required");
        }
        return  validation;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_NEW_USER){
            Intent intent = MainActivity.newInstance(this);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Ups, there was an error connecting with the server or finding the user. Try again",
                Toast.LENGTH_LONG).show();
        mEditTextUsername.setText("");
        mEditTextPassword.setText("");
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Connection successful", Toast.LENGTH_LONG).show();
        JSONArray array = response.optJSONArray("data");
        try {
            assert array != null;
            JSONObject object = array.getJSONObject(0);
            UserLab.get().getCurrentUser().setUsername(object.getString("username"));
            SharedPreferences.Editor editor = file.edit();
            editor.putString(CURRENT_USER, object.getString("username"));
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = MainActivity.newInstance(this);
        startActivity(intent);
        finish();
    }

    public void loadPosition(){
        //Request permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            COORD_LAT = 0;
            COORD_LONG = 0;
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            assert location != null;
            COORD_LAT = location.getLatitude();
            COORD_LONG = location.getLongitude();
        }
    }
}
