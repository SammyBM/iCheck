package labs;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import models.Reminder;

public class ReminderLab {
    private static ReminderLab sReminderLab;

    public static final String REMINDER_ID_TAG = "id_reminders";
    public static final String REMINDER_DESC_TAG = "description";

    public static ReminderLab get(){
        if (sReminderLab == null)
            sReminderLab = new ReminderLab();
        return sReminderLab;
    }



    public void removeReminder(RequestQueue mQueue, int id){
        String username = UserLab.get().getCurrentUser().getUsername();
        String queryDeleteReminder = "https://checkitdatabase.000webhostapp.com/remove_reminder.php?user=" + username + "&id=" + id;
        JsonRequest mJsonRequest = new JsonObjectRequest(Request.Method.GET, queryDeleteReminder, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(mJsonRequest);
    }
}
