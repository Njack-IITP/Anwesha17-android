package in.ac.iitp.anwesha;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity implements View.OnClickListener {

    private String name,shortDesc;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent in=getIntent();

        name = in.getStringExtra("eventName");
        toolbar.setTitle("Title ");
        toolbar.setSubtitle("Desc");
        ((TextView)findViewById(R.id.toolbar_text)).setText(name);
        if(in.hasExtra("eventDesc"))
            shortDesc = in.getStringExtra("eventDesc");
        else shortDesc = "";
        id = in.getIntExtra("eventID", -1);
        //Toast.makeText(this,name+","+shortDesc+","+id,Toast.LENGTH_SHORT).show();
        if(id == -1) {
            finish();return;
        }
        ((TextView)findViewById(R.id.event_details_title)).setText(name);
        ((TextView)findViewById(R.id.event_details_subtitle)).setText(shortDesc);
        ((TextView)findViewById(R.id.event_details_organisers)).setText("> Gagan Kumar\n> Abhishek Kumar");
        ((TextView)findViewById(R.id.event_details_rules)).setText("> Don't do legal Work\n> Smash The Arena");
        ((TextView)findViewById(R.id.event_details_schNvenue)).setText("> Tutorial Block (1400 Day 1)");
        ((TextView)findViewById(R.id.event_details_story)).setText("A very long Story\n\n\n\n\n\n\n\n\n\nGot It.....\tNow\n\n\n\n\n\n\n\n\n\n\nReally Long\n\n Got it!!");

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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
        intent.putExtra("title", name);
        intent.putExtra("stitle", shortDesc);
        intent.putExtra("event_id", id);

        am.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (5 * 1000), pendingIntent);
    }
}
