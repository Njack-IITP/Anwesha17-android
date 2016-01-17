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
    private   TextView DoItYourself,LectureAndPresentation,OnTheMove,OnTheSpot,Quiz,Robotics,CodingHacking;

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
        DoItYourself = (TextView) findViewById(R.id.DoItYourself);
        LectureAndPresentation = (TextView) findViewById(R.id.LectureAndPresentation);
        OnTheMove = (TextView) findViewById(R.id.OnTheMove);
        OnTheSpot = (TextView) findViewById(R.id.OnTheSpot);
        Quiz = (TextView) findViewById(R.id.Quiz);

        Robotics = (TextView) findViewById(R.id.Robotics);
        CodingHacking = (TextView) findViewById(R.id.CodingHacking);


        DoItYourself.setTypeface(AllIDS.font_Title);
        LectureAndPresentation.setTypeface(AllIDS.font_Title);
        OnTheMove.setTypeface(AllIDS.font_Title);
        OnTheSpot.setTypeface(AllIDS.font_Title);
        Quiz.setTypeface(AllIDS.font_Title);
        Robotics.setTypeface(AllIDS.font_Title);
        CodingHacking.setTypeface(AllIDS.font_Title);

        DoItYourself.setOnClickListener(this);
        LectureAndPresentation.setOnClickListener(this);
        OnTheMove.setOnClickListener(this);
        OnTheSpot.setOnClickListener(this);
        Quiz.setOnClickListener(this);
        Robotics.setOnClickListener(this);
        CodingHacking.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent in = new Intent(this,EventList.class);
        in.putExtra("event_list", (String) view.getTag());
        startActivity(in);
    }
}
