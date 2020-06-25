package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.icheck_app_final.R;

import java.nio.file.Watchable;

import labs.QueueSingleton;
import labs.UserLab;
import models.User;

public class AccountSettingsDialog extends DialogFragment {

    EditText mEditTextName;
    EditText mEditTextUsername;
    EditText mEditTextEmail;
    EditText mEditTextPassword;
    EditText mEditTextConfirmPassword;

    private static final String FILE_NAME = "check it session";
    private static final String CURRENT_USER =  "username";

    public static AccountSettingsDialog newInstance(){
        return new AccountSettingsDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_account_settings, null);
        //wiring up
        mEditTextName = view.findViewById(R.id.name_settings);
        mEditTextUsername= view.findViewById(R.id.username_settings);
        mEditTextEmail= view.findViewById(R.id.email_settings);
        mEditTextPassword= view.findViewById(R.id.password_settings);
        mEditTextConfirmPassword= view.findViewById(R.id.confirm_password_settings);

        updateUI();

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditTextConfirmPassword.setVisibility(View.VISIBLE);
                mEditTextConfirmPassword.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        validate();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_CANCELED);
                    }
                })
                .create();
    }

    private void updateUI(){
        mEditTextConfirmPassword.setVisibility(View.GONE);
        String name = UserLab.get().getCurrentUser().getName();
        String username = UserLab.get().getCurrentUser().getUsername();
        String password = UserLab.get().getCurrentUser().getPassword();
        String email = UserLab.get().getCurrentUser().getEmail();
        mEditTextName.setText(name);
        mEditTextUsername.setText(username);
        mEditTextPassword.setText(password);
        mEditTextEmail.setText(email);
    }

    private void validate(){
        boolean validation = true;
        final String name = mEditTextName.getText().toString();
        final String username = mEditTextUsername.getText().toString();
        final String password = mEditTextPassword.getText().toString();
        final String email = mEditTextEmail.getText().toString();
        String confirmPassword;

        if (name.isEmpty()){
            mEditTextName.setError("Name is required");
            validation = false;
        }

        if (username.isEmpty()){
            mEditTextName.setError("Username is required");
            validation = false;
        }

        if (password.isEmpty()){
            mEditTextPassword.setError("Password is required");
            validation = false;
        }

        if (email.isEmpty()){
            mEditTextEmail.setError("Email is required");
            validation = false;
        }

        if (mEditTextConfirmPassword.getVisibility() == View.VISIBLE) {
            confirmPassword = mEditTextConfirmPassword.getText().toString();
            if (confirmPassword.isEmpty()){
                mEditTextConfirmPassword.setError("Confirm Password is required");
                validation = false;
            }
            if (validation){
                if (password.equals(confirmPassword)){
                    mEditTextConfirmPassword.setError("Do not match");
                    validation = false;
                }
            }
        }

        if (validation){
            String searchUser = "";
            String updateUser = "";

            final StringRequest updateRequest = new StringRequest(Request.Method.GET, updateUser, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    User user = UserLab.get().getCurrentUser();
                    user.setName(name);
                    user.setPassword(password);
                    user.setUsername(username);
                    user.setEmail(email);
                    SharedPreferences file = getActivity().getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = file.edit();
                    editor.remove(CURRENT_USER);
                    editor.apply();
                    editor.putString(CURRENT_USER, username);
                    editor.apply();
                    sendResult(Activity.RESULT_OK);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "There was an error connecting with server", Toast.LENGTH_LONG).show();
                }
            });

            StringRequest searchRequest = new StringRequest(Request.Method.GET, searchUser, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Exists")) {
                        //Username exists
                        mEditTextUsername.setError("This username already exists");
                    } else if (response.equals("Not Exists"))
                        QueueSingleton.get(getActivity()).addToRequestQueue(updateRequest);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "There was an error connecting with server", Toast.LENGTH_LONG).show();
                }
            });
            QueueSingleton.get(getActivity()).addToRequestQueue(searchRequest);
        }
    }

    private void sendResult(int result){
        if(getTargetFragment() == null){
            return;
        }
        getTargetFragment().onActivityResult(getTargetRequestCode(), result, null);
    }
}
