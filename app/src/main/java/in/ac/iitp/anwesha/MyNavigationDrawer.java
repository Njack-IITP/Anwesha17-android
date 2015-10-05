package in.ac.iitp.anwesha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import in.ac.iitp.anwesha.R;

/**
 * Created by gagan on 4/10/15.
 */
public class MyNavigationDrawer implements NavigationView.OnNavigationItemSelectedListener {

    Activity activity;
    public MyNavigationDrawer(Activity activity)
    {
        this.activity = activity;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_event) {
            if(openEvent(activity)) activity.finish();

        } else if (id == R.id.nav_spons) {
            if(openSponser(activity)) activity.finish();

        } else if (id == R.id.nav_gallery) {
            if(openGallery(activity)) activity.finish();

        } else if (id == R.id.nav_pronites) {
            if(openPronites(activity)) activity.finish();

        } else if (id == R.id.nav_schedule) {
            if(openSchedule(activity)) activity.finish();

        } else if (id == R.id.nav_map) {
            if(openMap(activity)) activity.finish();

        } else if (id == R.id.nav_about) {
            if(openAbout(activity)) activity.finish();

        } else if (id == R.id.nav_team) {
            if(openTeam(activity)) activity.finish();

        } else  if (id == R.id.nav_event_cult) {
            if(openSubEvent(activity,"cult")) activity.finish();

        }else  if (id == R.id.nav_event_eco) {
            if(openSubEvent(activity,"eco")) activity.finish();

        }else  if (id == R.id.nav_event_lit) {
            if(openSubEvent(activity,"lit")) activity.finish();

        }else  if (id == R.id.nav_event_mang) {
            if(openSubEvent(activity,"mang")) activity.finish();

        }else  if (id == R.id.nav_event_tech) {
            if(openSubEvent(activity,"tech")) activity.finish();

        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    static protected void openActivity(Context context,Class class_name)
    {
        Intent in=new Intent(context,class_name);
        context.startActivity(in);

    }
    static boolean openEvent(Context context)
    {
        openActivity(context, Event.class);
        return true;
    }

    static boolean openSponser(Context context)
    {
        openActivity(context,Sponser.class);
        return true;
    }

    static boolean openGallery(Context context)
    {
        //Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        openActivity(context,Gallery.class);
        return false;
    }

    static boolean openPronites(Context context)
    {
        ///Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        openActivity(context,ProNites.class);
        return false;
    }

    static boolean openAbout(Context context)
    {
        openActivity(context,About.class);
        return false;
    }

    static boolean openTeam(Context context)
    {
        openActivity(context,Team.class);
        return true;
    }

    static boolean openSchedule(Context context)
    {
        Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        //openActivity(context,Event.class);
        return false;
    }

    static boolean openMap(Context context)
    {
        Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        //openActivity(context,Event.class);
        return false;
    }


    static boolean openSubEvent(Context context,String tag)
    {
        if(tag.equals("tech"))
        {
            Intent in =new Intent(context,TechnicalEvent.class);
            context.startActivity(in);
            return true;

        }
        else {
            Intent in = new Intent(context, EventList.class);
            in.putExtra("event_list", tag);
            context.startActivity(in);
            if(Event.getEventName(tag)!=null)
                return true;
        }
        return false;
    }



}
