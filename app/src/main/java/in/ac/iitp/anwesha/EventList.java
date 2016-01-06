package in.ac.iitp.anwesha;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventList extends AppCompatActivity
       {

    private ListView lv;
    private EventListAdapter ela;
    private String TestEvents[] = {"ByteRace","NJath","CTF","ElectroRush","TitalUnleased","Conntectrix"};
    private String TestEventsDesc[] = {"A Programming Contest","Online Treasure Hunt","Hack Everything","Race Against Electricity","Build THe Shield","Bridge It"};


   WebSyncDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
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

        lv = (ListView) findViewById(R.id.event_list_lv);
        ela = new EventListAdapter(this,R.layout.lay_event_element);
        lv.setAdapter(ela);
        lv.setClickable(false);


        Intent intent=getIntent();
        String code = intent.getStringExtra("event_list");
        /************** Fetch Data According to tag and set Title***********/
        if(code.equalsIgnoreCase("spark")) code= "Sparkonics";
        if(code.equalsIgnoreCase("thesholdNchem")) code= "Threshold";
        if(code.equalsIgnoreCase("njack")) code= "NJACK";
        if(code.equalsIgnoreCase("scme")) code= "SCME";
        if(code.equalsIgnoreCase("ace")) code= "ACE";
        if(code.equalsIgnoreCase("sae")) code= "SAE";
        if(code.equalsIgnoreCase("rtdc")) code= "RTDC";

        Cursor cursor = db.getParticularEvents(code);
        int c=0;
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
                ela.add(new Event(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5),cursor.getString(6)));
                cursor.moveToNext();
            c++;
            if(c>100) break;

        }
        String title= code;

        getSupportActionBar().setTitle(title);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    class Event
    {
        int fee,day,size,id;
        String name,code,desc;
        String toDisplay;
        Event(int id,String name,int fee,int day,int size,String code,String desc)
        {
            this.id=id;
            this.name=name;
            this.desc = desc;
            this.fee= fee;
            this.day = day;
            this.code= code;
            toDisplay = EventDetails.filterLongDesc((desc));
        }
    }
    class EventListAdapter extends ArrayAdapter<Event> implements View.OnClickListener {
        int resource;

        public EventListAdapter(Context context, int resource) {
            super(context, resource);this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
                convertView = getLayoutInflater().inflate(resource,null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_event_list_element);
            tv.setTypeface(AllIDS.font_AnweshaSub);

            tv.setText(getItem(position).name);
            TextView tvdesc = (TextView) convertView.findViewById(R.id.tv_event_list_element_desc);
            tvdesc.setTypeface(AllIDS.font_Sub1);
            tvdesc.setText(getItem(position).toDisplay);
            View v=convertView.findViewById(R.id.fab);
            v.setTag(position);
            v.setOnClickListener(this);

            //Alternate white black card
            /*ImageView iv = (ImageView) convertView.findViewById(R.id.iv_event_list_back);
            if(position%2==0)
                iv.setVisibility(View.GONE);
            else iv.setVisibility(View.VISIBLE);
            */
            return convertView;
        }

        @Override
        public void onClick(View view) {
            int i= (Integer) view.getTag();
            Intent in = new Intent(getContext(),EventDetails.class);
            Event e = getItem(i);
            in.putExtra("eventID", e.id);
            in.putExtra("eventName", e.name);
            in.putExtra("eventDesc", e.desc);
            in.putExtra("eventCode", e.code);
            in.putExtra("eventDay", e.day);
            in.putExtra("eventFee", e.fee);
            startActivity(in);


        }
    }


    public void openNavigation(View v)
    {
    /*    NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                  findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    */}
}
