package com.example.icheck_app_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText mEditTextEmail;
    Button mButtonRecover;
    public static Intent newInstance (Context context){
        return new Intent(context,ForgetPasswordActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //wiring up
        mEditTextEmail=findViewById(R.id.forget_username);
        mButtonRecover= findViewById(R.id.forget_start_button);

        mButtonRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //aqui va el código de mandar el email con la contraseña
                    finish();
                }
            }
        });
    }

    private boolean validate(){
        boolean validation = true;
        if(mEditTextEmail.getText().toString().isEmpty()){
            mEditTextEmail.setError("Email is required");
            validation= false;
        }
        //aqui va el código para verificar si hay una cuenta registrada con ese email
        return validation;
    }

}
