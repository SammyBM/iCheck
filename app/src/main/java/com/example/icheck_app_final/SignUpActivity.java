package com.example.icheck_app_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUpActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText mEditTextName;
    EditText mEditTextUsername;
    EditText mEditTextPassword;
    EditText mEditTextConfirmPassword;
    EditText mEditTextEmail;
    Button mButtonSignUp;

    JsonRequest mJsonRequest;

    SharedPreferences file;

    private static final String FILE_NAME = "check it session";
    private static final String CURRENT_USER =  "user";


    public static Intent newInstance (Context context) {
        return new Intent(context, SignUpActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //wiring up
        mEditTextName = findViewById(R.id.sign_up_name_input);
        mEditTextUsername= findViewById(R.id.sign_up_username_input);
        mEditTextPassword=findViewById(R.id.sign_up_password_input);
        mEditTextConfirmPassword= findViewById(R.id.sign_up_confirm_password_input);
        mButtonSignUp=findViewById(R.id.sign_up_start_button);
        mEditTextEmail= findViewById(R.id.sign_up_email_input);

        file = this.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp(){
        if(!validate()){
            return;
        }
        String name = mEditTextName.getText().toString();
        String username = mEditTextUsername.getText().toString();
        String password= mEditTextPassword.getText().toString();
        String email= mEditTextEmail.getText().toString();

        try {
            double coord_lat = LoginActivity.COORD_LAT;
            double coord_long = LoginActivity.COORD_LONG;
            String url= "https://checkitdatabase.000webhostapp.com/create_user.php?user=" + username +
                    "&pwd=" + password +"&email=" + email + "&name=" + name + "&coord_lat=" + coord_lat +
                    "&coord_long=" + coord_long;
            mJsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            QueueSingleton.get(this).addToRequestQueue(mJsonRequest);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validate(){
        boolean validation = true;
        if (mEditTextName.getText().toString().isEmpty()){
            validation = false;
            mEditTextName.setError("Name is required");
        }
        if(mEditTextUsername.getText().toString().isEmpty() ){
            validation= false;
            mEditTextUsername.setError("Username is required");
        }
        if(mEditTextEmail.getText().toString().isEmpty() ){
            validation= false;
            mEditTextEmail.setError("Email is required");
        }
        if(mEditTextPassword.getText().toString().isEmpty() ){
            validation= false;
            mEditTextPassword.setError("Password is required");
        }
        if(mEditTextConfirmPassword.getText().toString().isEmpty() ){
            validation= false;
            mEditTextConfirmPassword.setError("Please confirm your password");
        }

        if(validation){
            if(!mEditTextPassword.getText().toString().equals(mEditTextConfirmPassword.getText().toString())){
                Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                validation= false;
            }
        }
        return  validation;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Ups, there was a problem", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Connection was successful", Toast.LENGTH_LONG).show();
        JSONArray array = response.optJSONArray("data");
        try {
            assert array != null;
            JSONObject object = array.getJSONObject(0);
            UserLab.get().getCurrentUser().setUsername(object.getString("user"));

            SharedPreferences.Editor editor = file.edit();
            editor.putString(CURRENT_USER, object.getString("user"));
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setResult(RESULT_OK);
        finish();
    }
}
