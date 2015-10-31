package in.ac.iitp.anwesha;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by gagan on 30/10/15.
 */
public class BackgroundFetch extends Service {

    final static String BASE_URL = "http://172.16.132.110";
    RequestQueue queue;
    WebSyncDB db;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = new WebSyncDB(getApplicationContext());
        Log.e("BackgroundFetch","Service Created");

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("BackgroundFetch","Service Started");
        JsonArrayRequest events = new JsonArrayRequest(BASE_URL + "/a_eventjson.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.e("BackgroundFetch", "EventSuccess");
                updateEvents(jsonArray);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("BackgroundFetch","Cound't Fetch Event list");
            }
        });
        queue =  Volley.newRequestQueue(getApplicationContext());
        queue.add(events);

    }

    void updateEvents(JSONArray jsonArray)
    {
        ContentValues[] contentValues=new ContentValues[jsonArray.length()];
        try {
            for(int i=0;i<jsonArray.length();i++)
            {
                    JSONArray row = jsonArray.getJSONArray(i);
                    if(row.length()!=5)
                        return;//Invalid Download
                ContentValues cv = new ContentValues();
                cv.put(WebSyncDB.EVENT_ID,row.getInt(0));
                cv.put(WebSyncDB.EVENT_NAME,row.getString(1));
                cv.put(WebSyncDB.EVENT_fee,row.getInt(2));
                cv.put(WebSyncDB.EVENT_day,row.getInt(3));
                cv.put(WebSyncDB.EVENT_size,row.getInt(4));
                contentValues[i]=cv;

            }
            db.insertEvent(contentValues);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("BackgroundFetch", "Invalid JSON");

        }

    }
}

