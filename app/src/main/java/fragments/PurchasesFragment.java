package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.icheck_app_final.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import labs.QueueSingleton;

public class PurchasesFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public static PurchasesFragment newInstance() {
        return new PurchasesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchases, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_purchases);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getPurchases();
        return view;
    }

    private void getPurchases(){
        String url = "";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        QueueSingleton.get(getContext()).addToRequestQueue(request);
    }

}
