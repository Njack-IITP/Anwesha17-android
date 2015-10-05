package in.ac.iitp.anwesha;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Sponser extends AppCompatActivity
       {


    private LinearLayout ll_others;
    private int OtherIDs[]={R.drawable.event_tech,R.drawable.event_managment,R.drawable.event_lit,R.drawable.event_managment,R.drawable.event_eco,R.drawable.event_cult,R.drawable.iitp};
    private int columns;
    private int MAX_WIDTH;

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




        ll_others = (LinearLayout) findViewById(R.id.grid_other_sponser);
        MAX_WIDTH = ll_others.getWidth();
        ll_others.setOrientation(LinearLayout.VERTICAL);
        columns = Integer.parseInt((String)ll_others.getTag());
        for(int i=0;i<OtherIDs.length;)
        {
            ArrayList<Integer> d_row = new ArrayList<Integer>();
            for(int j=0;j<columns && i<OtherIDs.length;i++,j++)
            {
                d_row.add(OtherIDs[i]);
            }

            append(d_row);
        }

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
        getMenuInflater().inflate(R.menu.sponser, menu);
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






    void append(ArrayList<Integer> drow)
    {
        LinearLayout ll_row = (LinearLayout) getLayoutInflater().inflate(R.layout.lay_sponser_row,null);
        ImageView iv[]=new ImageView[4];
        iv[0] = (ImageView) ll_row.findViewById(R.id.iv_s1);
        iv[1] = (ImageView) ll_row.findViewById(R.id.iv_s2);
        iv[2] = (ImageView) ll_row.findViewById(R.id.iv_s3);
        iv[3] = (ImageView) ll_row.findViewById(R.id.iv_s4);
        for(int i=0;i<4;i++)
            iv[i].setVisibility(View.GONE);

        for(int i=0;i<drow.size();i++)
        {
            iv[i].setImageResource(drow.get((i)));
            iv[i].setVisibility(View.VISIBLE);
        }

        ll_others.addView(ll_row);
    }

}
