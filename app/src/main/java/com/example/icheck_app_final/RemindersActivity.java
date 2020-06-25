package com.example.icheck_app_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import labs.QueueSingleton;
import labs.ReminderLab;
import labs.UserLab;
import models.Reminder;

public class RemindersActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ImageView mImageViewEmptyList;
    TextView mTextViewOmg;
    TextView mTextViewEmpty;
    List<Reminder> mReminders =  new ArrayList<>();

    Context context;

    ReminderListAdapter mAdapter;

    public static Intent newInstance(Context context){
        return new Intent(context, RemindersActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        mRecyclerView = findViewById(R.id.reminder_recycler);
        mImageViewEmptyList= findViewById(R.id.cute_empty);
        mTextViewOmg= findViewById(R.id.empty_omg_label);
        mTextViewEmpty= findViewById(R.id.empty_main_label);

        context = this;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getReminders();
    }

    private void updateUI(){
        if (mReminders.size() == 0){
            mImageViewEmptyList.setVisibility(View.VISIBLE);
            mTextViewOmg.setVisibility(View.VISIBLE);
            mTextViewEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mImageViewEmptyList.setVisibility(View.GONE);
            mTextViewOmg.setVisibility(View.GONE);
            mTextViewEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter = new ReminderListAdapter();
                mRecyclerView.setAdapter(mAdapter);
        }
    }

    private class ReminderListAdapter extends RecyclerView.Adapter<ReminderListHolder>{

        @NonNull
        @Override
        public ReminderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(context, R.layout.item_reminder, null);
            return new ReminderListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReminderListHolder holder, int position) {
            final Reminder reminder = mReminders.get(position);
            holder.mTextViewReminder.setText(reminder.getDescription() + "\ncould be expired");
            holder.mRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    removeReminder(reminder.getId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mReminders.size();
        }
    }

    private static class ReminderListHolder extends RecyclerView.ViewHolder{
        RadioButton mRadioButton;
        TextView mTextViewReminder;

        ReminderListHolder(@NonNull View itemView) {
            super(itemView);
            mRadioButton = itemView.findViewById(R.id.reminder_button);
            mTextViewReminder = itemView.findViewById(R.id.reminder_text);
        }
    }

    private void getReminders(){
        mReminders.clear();
        String username = UserLab.get().getCurrentUser().getUsername();
        String queryReminder = "https://checkitdatabase.000webhostapp.com/search_reminders.php?user=" + username;
        JsonRequest mJsonRequest = new JsonArrayRequest(Request.Method.GET, queryReminder, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Reminder reminder = new Reminder();
                        reminder.setId(object.getInt(ReminderLab.REMINDER_ID_TAG));
                        reminder.setDescription(object.getString(ReminderLab.REMINDER_DESC_TAG));
                        mReminders.add(reminder);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                updateUI();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateUI();
            }
        });
        QueueSingleton.get(this).addToRequestQueue(mJsonRequest);
    }

    private void removeReminder(int id){
        Toast.makeText(this, Integer.toString(id), Toast.LENGTH_SHORT).show();
        String queryRemoveReminder = "https://checkitdatabase.000webhostapp.com/delete_reminder.php?id=" + id;
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, queryRemoveReminder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                getReminders();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Ups, there was an error", Toast.LENGTH_LONG).show();
            }
        });
        QueueSingleton.get(this).addToRequestQueue(mStringRequest);
    }
}

