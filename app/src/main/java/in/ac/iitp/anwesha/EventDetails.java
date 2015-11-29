package in.ac.iitp.anwesha;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    private String name,shortDesc;
    private int id;

    WebSyncDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        db = new WebSyncDB(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent in=getIntent();

        name = in.getStringExtra("eventName");
        toolbar.setTitle("Title ");
        ((TextView)findViewById(R.id.toolbar_text)).setText(name);

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

        ((TextView)findViewById(R.id.event_details_title)).setText(name);
        ((TextView)findViewById(R.id.event_details_subtitle)).setText("");
        ((TextView)findViewById(R.id.event_details_organisers)).setText(organisers);//"> Gagan Kumar\n> Abhishek Kumar");
        ((TextView)findViewById(R.id.event_details_rules)).setText(rules);//"> Don't do legal Work\n> Smash The Arena");
        ((TextView)findViewById(R.id.event_details_schNvenue)).setText(venue);//"> Tutorial Block (1400 Day 1)");
        ((TextView)findViewById(R.id.event_details_story)).setText(longdesc);//"A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");

        (findViewById(R.id.event_details_register)).setOnClickListener(this);


    }

    public void goBack(View v)
    {
        finish();
    }
    @Override
    public void onClick(View view) {
        //**** For Registering in Event  ******/
        SplashMessage(name,"Registered",android.R.drawable.ic_dialog_info);
        SplashMessage(name,"Registration Failed",android.R.drawable.ic_dialog_alert);


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
