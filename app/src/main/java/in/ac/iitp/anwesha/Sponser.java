package in.ac.iitp.anwesha;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Sponser extends AppCompatActivity {


    CAdapter adapter;
    private ListView ll_others;
    private int OtherIDs[] = {R.drawable.s_ebay, R.drawable.s_io, R.drawable.s_sbi, R.drawable.s_sp};
    private String OtherText[] = {"Hospitality Partner", null, null, null, null, "Strategic Sponsors", null, null};
    private int columns;
    private int MAX_WIDTH;
    String username,id;
    int loginflag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));
        View headerview = navigationView.getHeaderView(0);
        TextView headerId = (TextView) headerview.findViewById(R.id.header_id);
        TextView headerName = (TextView) headerview.findViewById(R.id.header_name);
        id = getPreferences().getString("id", "Anwesha 2017");
        username = getPreferences().getString("name", "Think.Dream.Live");
        loginflag = getPreferences().getInt("loginflag", 0);
        headerId.setText(id);
        headerName.setText(username);
        if(loginflag == 2 ){
            navigationView.getMenu().findItem(R.id.nav_loginlogout).setVisible(false);
        }


        ll_others = (ListView) findViewById(R.id.grid_other_sponser);
        adapter = new CAdapter(this);
        ll_others.setAdapter(adapter);
        MAX_WIDTH = ll_others.getWidth();
        columns = Integer.parseInt((String) ll_others.getTag());

    }
    private SharedPreferences getPreferences() {
        SharedPreferences sharedPref = getApplication().getSharedPreferences("login", MODE_PRIVATE);
        return sharedPref;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent in = new Intent(this, Home.class);
            in.putExtra("loginflag",loginflag);
            in.putExtra("name",username);
            in.putExtra("id",id);
            startActivity(in);
        }
    }

    class CAdapter extends ArrayAdapter<Integer> {
        ArrayList<Integer> l = new ArrayList<>();
        int resource = R.layout.lay_sponser_row;

        public CAdapter(Context context) {
            super(context, R.layout.lay_sponser_row);
        }

        @Override
        public void add(Integer object) {
            super.add(object);
            l.add(object);
        }

        @Override
        public int getCount() {
            return l.size();
        }

        @Override
        public Integer getItem(int position) {
            return l.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(resource, null);
            ((ImageView) view.findViewById(R.id.iv_s1)).setImageResource(getItem(position));
            return view;
        }
    }

}
