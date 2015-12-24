package in.ac.iitp.anwesha;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    private String name,shortDesc;
    private int id;

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
        String venue = "Venue : NA\nDay : "+in.getIntExtra("eventDay",0);
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
        ((TextView)findViewById(R.id.event_details_schNvenue)).setText(venue);//"> Tutorial Block (1400 Day 1)");
        ((TextView)findViewById(R.id.event_details_story)).setText(longdesc);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");
        ((TextView)findViewById(R.id.event_details_story)).setTypeface(AllIDS.font_Sub1);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");

        (findViewById(R.id.event_details_register)).setOnClickListener(this);


    }

    public void goBack(View v)
    {
        finish();
    }
    @Override
    public void onClick(View view) {
        //**** For Registering in Event  ******/
        SplashMessage(name,"Comming Soon!",android.R.drawable.ic_dialog_info);

        //SplashMessage(name,"Registered",android.R.drawable.ic_dialog_info);
        //SplashMessage(name,"Registration Failed",android.R.drawable.ic_dialog_alert);


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
