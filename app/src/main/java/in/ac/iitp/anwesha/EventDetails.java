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
    private String name, shortDesc;
    private int id, size = 1;

    static String filterLongDesc(String longdesc) {
        longdesc = longdesc.replaceAll("<[^>]*>", "");
        longdesc = longdesc.replaceAll("&nbsp;", "");
        longdesc = longdesc.replaceAll("&ldquo;", "\n");
        longdesc = longdesc.replaceAll("\r", "\n");
        longdesc = longdesc.replaceAll("\n{2,}", "\n\n");
        longdesc = longdesc.trim();
        longdesc = longdesc.substring(longdesc.indexOf("\n"));
        longdesc = longdesc.trim();

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

        //if(in.hasExtra("eventDesc"))
        //    shortDesc = in.getStringExtra("eventDesc");
        //else shortDesc = "";
        id = in.getIntExtra("eventID", -1);
        //Toast.makeText(this,name+","+shortDesc+","+id,Toast.LENGTH_SHORT).show();
        if (id == -1) {
            finish();
            return;
        }
        /************** Fetch Data According to tag and set Title***********/
        //Cursor cursor = db.getAllEvents();
        //cursor.moveToFirst();
        String organisers = "Not Available";
        String rules = "Fee : " + in.getIntExtra("eventFee", 0);
        String bottom = "Team Member : " + (size = in.getIntExtra("eventSize", 1));
        String longdesc = in.getStringExtra("eventDesc");
        String code = in.getStringExtra("eventCode");

        setTitle(name);
        longdesc = filterLongDesc(longdesc);

        ((TextView) findViewById(R.id.event_details_title)).setText(code);
        ((TextView) findViewById(R.id.event_details_title)).setTypeface(AllIDS.font_AnweshaSub);

        ((TextView) findViewById(R.id.event_details_subtitle)).setText("");
        ((TextView) findViewById(R.id.event_details_organisers)).setText(organisers);//"> Gagan Kumar\n> Abhishek Kumar");
        ((TextView) findViewById(R.id.event_details_rules)).setText(rules);//"> Don't do legal Work\n> Smash The Arena");
        ((TextView) findViewById(R.id.event_details_schNvenue)).setText(bottom);//"> Tutorial Block (1400 Day 1)");
        ((TextView) findViewById(R.id.event_details_story)).setText(longdesc);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");
        ((TextView) findViewById(R.id.event_details_story)).setTypeface(AllIDS.font_Sub1);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");

        (findViewById(R.id.event_details_register)).setOnClickListener(this);


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
