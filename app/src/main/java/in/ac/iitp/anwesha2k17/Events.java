package in.ac.iitp.anwesha2k17;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Events extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int loginflag;
    String username,id;

    public List<EventData> AllEvents = new ArrayList<EventData>();

    WebSyncDB db;
    int maxId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events2);

        getevents();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Events");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

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

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getevents(){
        db = new WebSyncDB(this);
        Cursor cursor = db.getAllEvents();
        int c = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AllEvents.add(new EventData(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12)));
            cursor.moveToNext();
            c++;
            if (c > 200) break;
        }

    }

    private SharedPreferences getPreferences() {
        SharedPreferences sharedPref = getApplication().getSharedPreferences("login", MODE_PRIVATE);
        return sharedPref;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for(int i=0;i<AllEvents.size();i++) {
            if(AllEvents.get(i).code == -1)
                adapter.addFrag(CulturalEvents.newInstance(AllEvents.get(i).id), AllEvents.get(i).name);
        }
        viewPager.setAdapter(adapter);
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
