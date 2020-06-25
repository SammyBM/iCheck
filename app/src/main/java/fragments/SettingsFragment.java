package fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.icheck_app_final.R;
import com.example.icheck_app_final.SettingsActivity;

public class SettingsFragment extends Fragment {

    TextView mTextViewAccount;
    TextView mTextViewAppearance;
    TextView mTextViewHelp;

    private static final String DIALOG_ACCOUNT = "Dialog Account";
    private static final int REQUEST_ACCOUNT = 1;
    private static final int REQUEST_APPEARANCE = 2;
    private static final int REQUEST_HELP = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        //wiring up
        mTextViewAccount=view.findViewById(R.id.TextViewAccount);
        mTextViewAppearance=view.findViewById(R.id.TextViewApAppearance);
        mTextViewHelp=view.findViewById(R.id.TextViewHelp);

        mTextViewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                AccountSettingsDialog dialog = AccountSettingsDialog.newInstance();
                dialog.setTargetFragment(SettingsFragment.this, REQUEST_ACCOUNT);
                assert manager != null;
                dialog.show(manager, DIALOG_ACCOUNT);
            }
        });

        mTextViewAppearance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mTextViewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        switch(requestCode){
            case REQUEST_ACCOUNT:
                Toast.makeText(getActivity(), "Update Account was successful", Toast.LENGTH_LONG).show();
                break;
            case REQUEST_APPEARANCE:
                Toast.makeText(getActivity(), "Update Appearance was successful", Toast.LENGTH_LONG).show();
                break;
            case REQUEST_HELP:
                break;
        }
    }
}
