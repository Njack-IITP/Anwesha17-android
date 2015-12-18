package in.ac.iitp.anwesha;

import android.content.Intent;
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
import android.widget.TextView;

public class TechnicalEvent extends AppCompatActivity
        implements View.OnClickListener {
    private   TextView njack,scme,rtdc,sparkonics,civilNchem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialise();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));
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





    public void initialise() {
        njack = (TextView) findViewById(R.id.njack);
        scme = (TextView) findViewById(R.id.scme);
        rtdc = (TextView) findViewById(R.id.rtdc);
        sparkonics = (TextView) findViewById(R.id.spark);
        civilNchem = (TextView) findViewById(R.id.civilNchem);
        njack.setTypeface(AllIDS.font_Title);
        scme.setTypeface(AllIDS.font_Title);
        rtdc.setTypeface(AllIDS.font_Title);
        sparkonics.setTypeface(AllIDS.font_Title);
        civilNchem.setTypeface(AllIDS.font_Title);
        /*((ImageButton) njack).setBackground((new BitmapDrawable(Event.giveMeCirculatImage(((BitmapDrawable) ((ImageButton) njack).getBackground()).getBitmap(), 0.5f, true))).getCurrent());
        ((ImageButton) scme).setBackground((new BitmapDrawable(Event.giveMeCirculatImage(((BitmapDrawable) ((ImageButton) scme).getBackground()).getBitmap(), 0.5f, true))).getCurrent());
        ((ImageButton) rtdc).setBackground((new BitmapDrawable(Event.giveMeCirculatImage(((BitmapDrawable) ((ImageButton) rtdc).getBackground()).getBitmap(), 0.5f, true))).getCurrent());
        ((ImageButton) sparkonics).setBackground((new BitmapDrawable(Event.giveMeCirculatImage(((BitmapDrawable) ((ImageButton) sparkonics).getBackground()).getBitmap(), 0.5f, true))).getCurrent());
        ((ImageButton) civilNchem).setBackground((new BitmapDrawable(Event.giveMeCirculatImage(((BitmapDrawable) ((ImageButton) civilNchem).getBackground()).getBitmap(), 0.5f, true))).getCurrent());
        */njack.setOnClickListener(this);
        scme.setOnClickListener(this);
        rtdc.setOnClickListener(this);
        sparkonics.setOnClickListener(this);
        civilNchem.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent in = new Intent(this,EventList.class);
        in.putExtra("event_list", (String) view.getTag());
        startActivity(in);
    }
}
