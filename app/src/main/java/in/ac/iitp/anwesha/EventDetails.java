package in.ac.iitp.anwesha;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    WebSyncDB db;
    String date,time,venue,short_desc,long_desc,organisers,name,fee,bottom;
    int size,id,code;

    static String filterLongDesc(String longdesc) {
        longdesc = longdesc.replaceAll("<[^>]*>", "");
        longdesc = longdesc.replaceAll("&nbsp;", "");
        longdesc = longdesc.replaceAll("&ldquo;", "\n");
        longdesc = longdesc.replaceAll("\r", "\n");
        longdesc = longdesc.replaceAll("\n{2,}", "\n\n");
        longdesc = longdesc.trim();
        //longdesc = longdesc.substring(longdesc.indexOf("\n"));
        //longdesc = longdesc.trim();

        return longdesc;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        db = new WebSyncDB(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));


        Intent in = getIntent();

        name = in.getStringExtra("eventName");

        id = in.getIntExtra("eventID", -1);

        if (id == -1) {
            finish();
            return;
        }

        organisers = "Organisers : \n" + in.getStringExtra("organisers");
        fee  = "Fee : Rs " + in.getIntExtra("eventFee", 0);
        bottom = "Team Member : " + (size = in.getIntExtra("eventSize", 1));
        long_desc = in.getStringExtra("eventDesc");
        if(long_desc == null)
            long_desc = "To be updated soon";
        short_desc = in.getStringExtra("short_desc");

        if(in.getStringExtra("date") == null)
            date = "Date : TBD" ;
        else
            date = "Date : " + in.getStringExtra("date");

        if(in.getStringExtra("time") == null)
            time = "Time : TBD" ;
        else
            time = "Time : " + in.getStringExtra("time");

        if(in.getStringExtra("venue") == null)
            venue = "Venue : TBD" ;
        else
            venue = "Venue : " + in.getStringExtra("venue");

        setTitle(name);
        if(long_desc !=null)
            long_desc = filterLongDesc(long_desc);
        if(short_desc !=null)
            short_desc = filterLongDesc(short_desc);


        ((TextView) findViewById(R.id.event_short_story)).setText(short_desc);

        ((TextView) findViewById(R.id.event_details_subtitle)).setText("");

        ((TextView) findViewById(R.id.event_details_story)).setText(long_desc);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");
        ((TextView) findViewById(R.id.event_details_story)).setTypeface(AllIDS.font_Sub1);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");

        ((TextView) findViewById(R.id.event_date_time)).setText(date + "\n" + time + "\n" + venue + "\n\n" + organisers);

        ((TextView) findViewById(R.id.event_team)).setText(bottom);

        (findViewById(R.id.event_details_register)).setOnClickListener(this);

        if(short_desc == null || short_desc.equals("null") || short_desc.equals(" ")){
            findViewById(R.id.short_story_card).setVisibility(View.INVISIBLE);
        }

        if(long_desc == null || long_desc.equals("null") || long_desc.equals(" ")){
            findViewById(R.id.long_story_card).setVisibility(View.INVISIBLE);
        }
    }

    public void goBack(View v) {
        this.finish();
    }

    @Override
    public void onClick(View view) {
        //SplashMessage(name,"Registration Failed",android.R.drawable.ic_dialog_alert);

        if (AllIDS.USER_key == null) {
            Intent in = new Intent(this, Users.class);
            startActivity(in);
            return;
        }

        if (size == 1)
            postData(null, null, "" + id);
        else {
            LinearLayout v = new LinearLayout(this);
            v.setOrientation(LinearLayout.VERTICAL);
            final EditText team_name = new EditText(this);
            team_name.setHint("Team Name");
            v.addView(team_name);
            final EditText[] team_members = new EditText[size - 1];
            for (int i = 0; i < size - 1; i++) {
                EditText et = new EditText(this);
                et.setHint("Member " + (i + 2) + " ID");
                team_members[i] = et;
                v.addView(et);
            }
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Group Registration");
            ad.setView(v);
            ad.setIcon(android.R.drawable.ic_dialog_info);
            ad.setPositiveButton("Register", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    JSONObject obj = new JSONObject();
                    JSONArray array = new JSONArray();
                    for (int i2 = 0; i2 < size - 1; i2++) {
                        String id = team_members[i2].getText().toString().toUpperCase();
                        if (!id.trim().isEmpty())
                            array.put(id);
                    }
                    //obj.put("IDs", array);
                    ArrayList<Pair<String, String>> param = new ArrayList<Pair<String, String>>();
                    param.add(new Pair<String, String>(obj.toString(), ""));
                    String url = "group/" + id;
                    postData(array, team_name.getText().toString(), url);

                }
            });
            ad.setNegativeButton("Cancel", null);
            ad.show();

        }
    }

    void postData(JSONArray array, String teamname, String appurl) {
        //**** For Registering in Event  ******/
        String url = BackgroundFetch.BASE_URL + "/register/" + appurl;
        ArrayList param = new ArrayList<>();
        HMac mac = new HMac();
        param.add(new Pair<String, String>("hash", mac.getHash()));
        param.add(new Pair<String, String>("content", mac.getMessage()));
        param.add(new Pair<String, String>("userID", AllIDS.USER_anweshaID.substring(3)));
        if (teamname != null)
            param.add(new Pair<String, String>("name", teamname));
        if (array != null)
            param.add(new Pair<String, String>("IDs", array.toString()));


        MyHttpClient client = new MyHttpClient(url, param, true, new MyHttpClientListener() {
            @Override
            public void onPreExecute() {


            }

            @Override
            public void onFailed(Exception e) {
                //pd.dismiss();
                SplashMessage(name, "Registration Failed", android.R.drawable.ic_dialog_info);

            }

            @Override
            public void onSuccess(Object output) {
                String msg = "Not Registered";
                String out = (String) output;

                try {
                    JSONObject ob = new JSONObject(out);
                    boolean status = ob.getBoolean("status");
                    msg = ob.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //pd.dismiss();
                SplashMessage(name, msg, android.R.drawable.ic_dialog_info);

            }

            @Override
            public void onBackgroundSuccess(String result) {

            }
        });
        client.context = this;


    }

    public void SplashMessage(String Title, String str, int icon) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(Title);
        ad.setMessage(str);
        ad.setIcon(icon);
        ad.show();
    }

    public void setReminder(View v) {
        SplashMessage("Anwesha", "Comming Soon", android.R.drawable.ic_dialog_info);
        if (true)
            return;
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, Reminder.class);
        intent.putExtra("Notification", "true");
        intent.putExtra("title", name);
        intent.putExtra("event_id", id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (5 * 1000), pendingIntent);
    }
}
