package in.ac.iitp.anwesha;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Random;

public class Home extends AppCompatActivity implements Animation.AnimationListener {

    private View nameBox,welcomeframe,head,head_container;
    private Animation name_Box,welcome_anim,header,tray_anim,tray_anim1,img1_anim,img2_anim,moveout1,moveout2,fadedrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));



        new syncNotifications();
    }



    public void onButtonClick(View v)
    {
        int id= v.getId();
        Intent in;
        switch (id)
        {
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
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
     }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    class syncNotifications{
        NotificationManager nm;
        Random r;
        RequestQueue queue;
        syncNotifications()
        {
            queue = Volley.newRequestQueue(getApplicationContext());
            nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            r = new Random();
            checkWeb();
        }


        void checkWeb()
        {
            final String time = AllIDS.readLastNotificationTime(getApplicationContext());
            JsonObjectRequest objectRequest = new JsonObjectRequest(BackgroundFetch.BASE_URL+"/notifications/"+time,null,new Response.Listener<JSONObject>(){

                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if(jsonObject.getBoolean("status"))
                        {
                            long maxTime = Long.parseLong(time);
                            JSONArray msg = jsonObject.getJSONArray("msgs");
                            for(int i=0;i<msg.length();i++)
                            {
                                JSONObject obj = msg.getJSONObject(i);
                                long t = obj.getLong("time");
                                String m = obj.getString("msg");
                                notification(m);

                                if(maxTime<t)
                                    maxTime = t;

                            }
                            AllIDS.saveLastNotificationTime(getApplicationContext(),String.valueOf(maxTime));


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            queue.add(objectRequest);

        }
        void notification(String msg)
        {

            Notification.Builder nb = new Notification.Builder(getApplicationContext())
                    .setAutoCancel(true)
                    .setContentTitle("Anwesha")
                    .setContentText(msg)
                    .setSmallIcon(R.mipmap.ic_launcher);
            Notification notification = nb.build();
            nm.notify(r.nextInt() ,notification);//Some Random Number + eventID


        }
    }
}
