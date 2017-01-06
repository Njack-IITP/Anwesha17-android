package in.ac.iitp.anwesha;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class Home extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getPreferences().edit().putBoolean("login", true).apply();
        Log.v("chirag", getPreferences().getBoolean("login", false) + "");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.sliding_image1);
        file_maps.put("Big Bang Theory", R.drawable.sliding_image2);
        file_maps.put("House of Cards", R.drawable.sliding_image3);
        file_maps.put("Game of Thrones", R.drawable.sliding_image4);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.startAutoCycle();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));


        new syncNotifications();
    }

    private SharedPreferences getPreferences() {
        SharedPreferences sharedPref = getApplication().getSharedPreferences("login", MODE_PRIVATE);
        return sharedPref;
    }

    /*@Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
       // mDemoSlider.stopAutoCycle();
        //super.onStop();
    }*/


    public void onButtonClick(View v) {
        int id = v.getId();
        Intent in;
        switch (id) {
            case R.id.b_home_superClubs:
                MyNavigationDrawer.openEvent(this);
                break;
            case R.id.b_home_sponser:
                MyNavigationDrawer.openSponser(this);
                break;

            case R.id.b_home_team:
                MyNavigationDrawer.openTeam(this);
                break;
            case R.id.b_home_pronites:
                MyNavigationDrawer.openPronites(this);
                break;
            case R.id.b_home_galley:
                MyNavigationDrawer.openGallery(this);
                break;
            case R.id.b_home_schedule:
                MyNavigationDrawer.openSchedule(this);
                break;
            case R.id.b_home_map:
                MyNavigationDrawer.openMap(this);
                break;
            case R.id.b_home_about:
                MyNavigationDrawer.openAbout(this);
                break;


        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.finishAffinity();
        }
    }

    class syncNotifications {
        NotificationManager nm;
        Random r;
        RequestQueue queue;

        syncNotifications() {
            queue = Volley.newRequestQueue(getApplicationContext());
            nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            r = new Random();
            checkWeb();
        }


        void checkWeb() {
            final String time = AllIDS.readLastNotificationTime(getApplicationContext());
            JsonObjectRequest objectRequest = new JsonObjectRequest(BackgroundFetch.BASE_URL + "/notifications/" + time, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getBoolean("status")) {
                            long maxTime = Long.parseLong(time);
                            JSONArray msg = jsonObject.getJSONArray("msgs");
                            for (int i = 0; i < msg.length(); i++) {
                                JSONObject obj = msg.getJSONObject(i);
                                long t = obj.getLong("time");
                                String m = obj.getString("msg");
                                notification(m);

                                if (maxTime < t)
                                    maxTime = t;

                            }
                            AllIDS.saveLastNotificationTime(getApplicationContext(), String.valueOf(maxTime));


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            queue.add(objectRequest);

        }

        void notification(String msg) {

            Notification.Builder nb = new Notification.Builder(getApplicationContext())
                    .setAutoCancel(true)
                    .setContentTitle("Anwesha")
                    .setContentText(msg)
                    .setSmallIcon(R.mipmap.ic_launcher);
            Notification notification = nb.build();
            nm.notify(r.nextInt(), notification);//Some Random Number + eventID


        }
    }
}
