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
import org.json.JSONObject;

/**
 * Created by gagan on 30/10/15.
 */
public class BackgroundFetch extends Service {

    final static String BASE_URL = "http://2016.anwesha.info";
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
        JsonArrayRequest events = new JsonArrayRequest(BASE_URL + "/test/allEvents", new Response.Listener<JSONArray>() {
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
        try {
        JSONArray array = jsonArray.getJSONArray(1);// Second Element
        ContentValues[] contentValues=new ContentValues[array.length()];
            for(int i=0;i<array.length();i++)
            {
                JSONObject row = array.getJSONObject(i);
                ContentValues cv = new ContentValues();
                cv.put(WebSyncDB.EVENT_ID,row.getInt("eveId"));
                cv.put(WebSyncDB.EVENT_NAME,row.getString("eveName"));
                cv.put(WebSyncDB.EVENT_fee,row.getInt("fee"));
                cv.put(WebSyncDB.EVENT_day,row.getInt("day"));
                cv.put(WebSyncDB.EVENT_size,row.getInt("size"));
                cv.put(WebSyncDB.EVENT_code,row.getString("code"));
                cv.put(WebSyncDB.EVENT_details,row.getString("details"));

                contentValues[i]=cv;

            }
            db.insertEvent(contentValues);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("BackgroundFetch", "Invalid JSON");

        }

    }
}

