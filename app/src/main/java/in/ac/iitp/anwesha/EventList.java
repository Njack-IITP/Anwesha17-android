package in.ac.iitp.anwesha;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
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
        //lv.setOnItemClickListener(this);
        Intent intent=getIntent();
        String tag = intent.getStringExtra("event_list");
        /************** Fetch Data According to tag and set Title***********/
        int c=0;
        for(String s : TestEvents)
        {
            ela.add(new Event(c,s,TestEventsDesc[c]));
            c++;
        }
        getSupportActionBar().setTitle(in.ac.iitp.anwesha.Event.getEventName((String) tag));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    class Event
    {
        int id;
        String name,desc;
        Event(int id,String name,String desc)
        {
            this.id=id;
            this.name=name;
            this.desc = desc;
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
            tv.setText(getItem(position).name);
            TextView tvdesc = (TextView) convertView.findViewById(R.id.tv_event_list_element_desc);
            tvdesc.setText(getItem(position).desc);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_event_list_back);
            View v=convertView.findViewById(R.id.fab);
            v.setTag(position);
            v.setOnClickListener(this);
            if(position%2==0)
                iv.setVisibility(View.GONE);
            else iv.setVisibility(View.VISIBLE);
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
