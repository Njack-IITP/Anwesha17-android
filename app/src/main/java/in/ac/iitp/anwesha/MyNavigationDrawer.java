package in.ac.iitp.anwesha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

        }else  if (id == R.id.nav_login) {
            AllIDS.loginlogout(activity);
        } else  if (id == R.id.nav_registration) {
            if(openRegistationPage(activity)) activity.finish();
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
        openActivity(context, Sponser.class);
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
        //Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        openScheduleDialog(context);
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
        openActivity(context, Users.class);
        return true;
    }
    static boolean openRegistationPage(Context context)
    {
        //Toast.makeText(context,"Coming Soon!",Toast.LENGTH_SHORT).show();
        Intent in=new Intent(context,Users.class);
        in.putExtra("reg",true);
        context.startActivity(in);
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

    static boolean openScheduleDialog(final Context context)
    {
        View.OnClickListener list = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPDF(context,(String)view.getTag());
            }
        };

        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.content_schedule,null);
        view.findViewById(R.id.day1).setOnClickListener(list);
        view.findViewById(R.id.day2).setOnClickListener(list);
        view.findViewById(R.id.day3).setOnClickListener(list);
        view.findViewById(R.id.night).setOnClickListener(list);
        view.findViewById(R.id.mainstage).setOnClickListener(list);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Schedule")
                .setView(view)
                .setNegativeButton("Back",null)
                .create();
        dialog.show();
        return false;

    }
    static  void openPDF(Context context,String name)
    {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Anwesha/");
        folder.mkdirs();
        File file = new File(folder,name);

        if(!file.exists())
        {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                InputStream is = context.getAssets().open("schedule/"+name);
                byte[] bytes = new byte[1024*300];
                int s;
                s = is.read(bytes);
                fos.write(bytes, 0, s);
                fos.close();
                is.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            context.startActivity(intent);
        }catch (ActivityNotFoundException e)
        {
            Toast.makeText(context,"Sorry! Your phone is not supporting PDF",Toast.LENGTH_SHORT).show();
        }


    }


}
