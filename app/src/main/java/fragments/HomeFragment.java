package fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.icheck_app_final.MainActivity;
import com.example.icheck_app_final.R;
import com.example.icheck_app_final.RemindersActivity;
import com.example.icheck_app_final.SettingsActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ImageView mImageViewNewList;
    ImageView mImageViewPurchases;
    ImageView mImageViewFavourites;
    ImageView mImageViewCupboard;
    TextView mTextViewUser;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //wiring up
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mImageViewNewList= view.findViewById(R.id.ImageButtonShopping);
        mImageViewFavourites= view.findViewById(R.id.ImageButtonFavourites);
        mImageViewCupboard= view.findViewById(R.id.ImageButtonCupboard);
        mImageViewPurchases=view.findViewById(R.id.ImageButtonPurchases);
        mTextViewUser= view.findViewById(R.id.TextViewWelcomeHome);

        mImageViewNewList.setOnClickListener(this);
        mImageViewFavourites.setOnClickListener(this);
        mImageViewCupboard.setOnClickListener(this);
        mImageViewPurchases.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_remainders){
            Intent intent = RemindersActivity.newInstance(getContext());
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_settings){
            Intent intent = SettingsActivity.newInstance(getContext());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.ImageButtonShopping: {
                Navigation.findNavController(v).navigate(R.id.nav_shoppingList);
                break;
            }
            case R.id.ImageButtonFavourites: {
                Navigation.findNavController(v).navigate(R.id.nav_favourites);
                break;
            }
            case R.id.ImageButtonPurchases: {
                Navigation.findNavController(v).navigate(R.id.nav_purchases);
                break;
            }
            case R.id.ImageButtonCupboard:{
                Navigation.findNavController(v).navigate(R.id.nav_cupboard);
                break;
            }
        }
    }
}
