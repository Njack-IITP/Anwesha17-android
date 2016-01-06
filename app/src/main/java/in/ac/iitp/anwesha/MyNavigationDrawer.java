package in.ac.iitp.anwesha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

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
            if(openSubEvent(activity,"Cultural")) activity.finish();

        }else  if (id == R.id.nav_event_eco) {
            if(openSubEvent(activity,"Eco")) activity.finish();

        }else  if (id == R.id.nav_event_lit) {
            if(openSubEvent(activity,"Literary")) activity.finish();

        }else  if (id == R.id.nav_event_mang) {
            if(openSubEvent(activity,"Management")) activity.finish();

        }else  if (id == R.id.nav_event_tech) {
            if(openSubEvent(activity,"Technical")) activity.finish();

        } else  if (id == R.id.nav_feedback) {
            Toast.makeText(activity,"Coming Soon!",Toast.LENGTH_SHORT).show();
        } else  if (id == R.id.nav_login) {
            if(openLoginPage(activity)) activity.finish();
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
        Intent in;
        in = new Intent(Intent.ACTION_VIEW, Uri.parse("http://2016.anwesha.info/gallery.html"));
        context.startActivity(in);

        //openActivity(context,Gallery.class);
        return false;
    }

    static boolean openPronites(Context context)
    {
        //Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        openActivity(context, Pronites.class);
        return true;
    }

    static boolean openAbout(Context context)
    {
        openActivity(context,About.class);
        return true;
    }

    static boolean openTeam(Context context)
    {
        //Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
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
       // Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        openActivity(context,Map.class);
        return true;
    }

    static boolean openLoginPage(Context context)
    {
        //Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        openActivity(context,Users.class);
        return true;
    }



    static boolean openSubEvent(Context context,String tag)
    {
        if(tag.equals("Technical"))
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
