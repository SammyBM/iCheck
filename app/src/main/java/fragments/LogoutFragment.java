package fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.icheck_app_final.LoginActivity;

public class LogoutFragment extends Fragment {

    private static final String FILE_NAME = "check it session";
    private static final String CURRENT_USER =  "username";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences file = getActivity().getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();
        editor.remove(CURRENT_USER);
        editor.apply();
        Intent intent = LoginActivity.newInstance(getActivity());
        startActivity(intent);
        getActivity().finish();
    }
}
