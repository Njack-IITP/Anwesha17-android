package in.ac.iitp.anwesha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class SocialActivity extends AppCompatActivity {

    int loginflag;
    String id,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
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

    }

    void getOpenFacebookPage()
    {

        Intent in;
        try {
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/163423960356924"));
            startActivity(in);
        } catch (Exception e) {
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/anwesha.iitpatna"));
            startActivity(in);
        }

    }
    void getOpenGooglePPage() {

        Intent in;
        try {
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("android-app://com.google.android.apps.plus/https/plus.google.com/+iitpatna"));
            in.setPackage("com.google.android.apps.plus");
            startActivity(in);

        } catch (Exception e) {
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/+iitpatna"));
            startActivity(in);
        }

    }

    void getOpenInstagramPage() {

        Intent in;
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/anwesha.iitp/"));
            startActivity(in);


    }
    void getOpenYoutubePage() {

        Intent in;

            in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCVVdnGvmkm-Z9v9IKAq77JQ"));
            startActivity(in);


    }

    void getOpenWebsitePage() {

        Intent in;
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://2017.anwesha.info"));
            startActivity(in);

    }
    public void onIconClick(View view){
        int id = view.getId();
        switch(id){
            case R.id.facebook:
                getOpenFacebookPage();
                break;
            case R.id.googleplus:
                getOpenGooglePPage();
                break;
            case R.id.instagram:
                getOpenInstagramPage();
                break;
            case R.id.youtube:
                getOpenYoutubePage();
                break;
            case R.id.website:
                getOpenWebsitePage();
                break;
        }
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
}
