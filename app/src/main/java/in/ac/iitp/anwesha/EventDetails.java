package in.ac.iitp.anwesha;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    private String name,shortDesc;
    private int id, size =1 ;


    WebSyncDB db;
    static String filterLongDesc(String longdesc)
    {
        longdesc = longdesc.replaceAll("<[^>]*>", "");
        longdesc = longdesc.replaceAll("&nbsp;", "");
        longdesc = longdesc.replaceAll("&ldquo;", "\n");
        longdesc = longdesc.replaceAll("\r", "\n");
        longdesc = longdesc.trim();
        longdesc = longdesc.substring(longdesc.indexOf("\n"));
        longdesc = longdesc.trim();

        return  longdesc;

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


        Intent in=getIntent();

        name = in.getStringExtra("eventName");

        //if(in.hasExtra("eventDesc"))
        //    shortDesc = in.getStringExtra("eventDesc");
        //else shortDesc = "";
        id = in.getIntExtra("eventID", -1);
        //Toast.makeText(this,name+","+shortDesc+","+id,Toast.LENGTH_SHORT).show();
        if(id == -1) {
            finish();return;
        }
        /************** Fetch Data According to tag and set Title***********/
        //Cursor cursor = db.getAllEvents();
        //cursor.moveToFirst();
        String organisers = "Not Available";
        String rules = "Fee : "+in.getIntExtra("eventFee",0);
        String bottom = "Team Member : "+(size=in.getIntExtra("eventSize",1))+"\nDay : "+in.getIntExtra("eventDay",0);
        String longdesc = in.getStringExtra("eventDesc");
        String code = in.getStringExtra("eventCode");

        setTitle(name);
        longdesc = filterLongDesc(longdesc);
        //Matcher matcher = Pattern.compile("<")
        /*try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.parse(longdesc);

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String shortDesc =
    */
        //For Now all is Just Event Name
        /*
        if(!cursor.isAfterLast())
        {
            shortDesc = cursor.getString(1);
            organisers = cursor.getString(1);
            rules = cursor.getString(1);
            venue = cursor.getString(1);
            longdesc = cursor.getString(1);

        }
        else
        {
            Toast.makeText(this,"Some Error in this Event",Toast.LENGTH_SHORT).show();
        }
        */
        /****************************************/

        ((TextView) findViewById(R.id.event_details_title)).setText(code);
        ((TextView) findViewById(R.id.event_details_title)).setTypeface(AllIDS.font_AnweshaSub);

        ((TextView)findViewById(R.id.event_details_subtitle)).setText("");
        ((TextView)findViewById(R.id.event_details_organisers)).setText(organisers);//"> Gagan Kumar\n> Abhishek Kumar");
        ((TextView)findViewById(R.id.event_details_rules)).setText(rules);//"> Don't do legal Work\n> Smash The Arena");
        ((TextView)findViewById(R.id.event_details_schNvenue)).setText(bottom);//"> Tutorial Block (1400 Day 1)");
        ((TextView)findViewById(R.id.event_details_story)).setText(longdesc);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");
        ((TextView)findViewById(R.id.event_details_story)).setTypeface(AllIDS.font_Sub1);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");

        (findViewById(R.id.event_details_register)).setOnClickListener(this);


    }

    public void goBack(View v)
    {
        this.finish();
    }
    @Override
    public void onClick(View view) {
        //SplashMessage(name,"Registration Failed",android.R.drawable.ic_dialog_alert);

        if(size == 1)
            postData(null,""+id);
        else
        {
            LinearLayout v = new LinearLayout(this);
            v.setOrientation(LinearLayout.VERTICAL);
            final EditText team_name = new EditText(this);
            team_name.setHint("Team Name");
            v.addView(team_name);
            final EditText[] team_members = new EditText[size-1];
            for(int i=0;i<size-1;i++)
            {
                EditText et = new EditText(this);
                et.setHint("Member "+(i+2)+" ID");
                team_members[i]=et;
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
                try {
                    obj.put("name", team_name.getText().toString());
                    ArrayList<String> list = new ArrayList<String>();
                    for (int i2 = 0; i2 < size - 1; i2++)
                        list.add( team_members[i].getText().toString());
                    JSONArray array = new JSONArray(list);
                    obj.put("IDs", array);
                    ArrayList<Pair<String, String>> param = new ArrayList<Pair<String, String>>();
                    param.add(new Pair<String, String>(obj.toString(), ""));
                    String url = "group/" + id;
                    postData(param, url);

                } catch (JSONException e) {
                    e.printStackTrace();
                    SplashMessage(name, "Error from App Side", android.R.drawable.ic_dialog_info);

                }

            }
        });
            ad.setNegativeButton("Cancel",null);
            ad.show();

        }
    }

    void postData(ArrayList<Pair<String,String>> param,String appurl)
    {
        //**** For Registering in Event  ******/
        if(AllIDS.USER_key==null)
        {
            Intent in = new Intent(this,Users.class);
            startActivity(in);
            return;
        }
        String url = BackgroundFetch.BASE_URL + "/register/"+appurl;
        if (param == null)
            param = new ArrayList<>();
        HMac mac = new HMac();
        param.add(new Pair<String, String>("hmac _mapped",mac.getHash()));
        param.add(new Pair<String, String>("content",mac.getMessage()));
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
                SplashMessage(name,msg,android.R.drawable.ic_dialog_info);

            }

            @Override
            public void onBackgroundSuccess(String result) {

            }
        });


        //SplashMessage(name,"Registered",android.R.drawable.ic_dialog_info);

    }
    public void SplashMessage(String Title,String str,int icon)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(Title);
        ad.setMessage(str);
        ad.setIcon(icon);
        ad.show();
    }

    public void setReminder(View v)
    {
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
