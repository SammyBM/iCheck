package labs;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class QueueSingleton {
    private static QueueSingleton sQueueSingleton;
    private RequestQueue mQueue;
    private static Context mContext;

    public static QueueSingleton get(Context context){
        if (sQueueSingleton == null)
            sQueueSingleton = new QueueSingleton(context);
        return sQueueSingleton;
    }

    private QueueSingleton(Context context){
        mContext = context;
        mQueue = getQueue();
    }

    private RequestQueue getQueue(){
        if (mQueue == null)
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        return mQueue;
    }

    public<T> void addToRequestQueue(Request<T> request){
        mQueue.add(request);
    }

}
