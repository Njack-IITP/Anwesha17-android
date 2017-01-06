package in.ac.iitp.anwesha;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gagan on 4/10/15.
 */
public class AllIDS extends Application {

    static Typeface font_Anwesha;
    static Typeface font_AnweshaSub;

    static Typeface font_Title;
    static Typeface font_Sub1;
    static Typeface font_Sub2;
    static String USER_anweshaID = null;
    static String USER_anweshapass = null;
    static String USER_name = null;
    static String USER_key = null;

    static String readLastNotificationTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userdetails", MODE_PRIVATE);
        return sp.getString("lnt", "1453131844");
    }

    static void saveLastNotificationTime(Context context, String time) {
        SharedPreferences sp = context.getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("lnt", time);
        editor.commit();
    }

    static void saveSharedPref(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", USER_anweshaID);
        //editor.putString("name", USER_name);
        //editor.putString("key",USER_key);
        editor.putString("pass", USER_anweshapass);
        //editor.putString("cookie",MyHttpClient.getCookie());

        editor.commit();

    }

    static void readSharedPref(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userdetails", MODE_PRIVATE);
        USER_anweshaID = sp.getString("id", null);
        //USER_name = sp.getString("name", null);
        USER_anweshapass = sp.getString("pass", null);
        //USER_key = sp.getString("key",null);
        //   MyHttpClient.setCookie(sp.getString("cookie",""));

    }

    static void logout(Context context) {
        logout(context, true);

    }

    static void logout(Context context, boolean makeToast) {
        if (makeToast)
            Toast.makeText(context, "Logged Out!", Toast.LENGTH_SHORT).show();
        USER_anweshaID = null;
        USER_key = null;
        USER_name = null;
        saveSharedPref(context);

    }

    static void loginlogout(final Context context) {
        if (AllIDS.USER_key == null)
            MyNavigationDrawer.openLoginPage(context);
        else {
            AlertDialog alert = new AlertDialog.Builder(context)
                    .setTitle("Anwesha")
                    .setMessage("Are you sure you want to logout?")
                    .setIcon(android.R.drawable.stat_sys_warning)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            AllIDS.logout(context);
                        }
                    }).setNegativeButton("No", null).create();
            alert.show();
        }
    }

    static void loadDatabase(Context context) {
        File folder = new File("/data/data/in.ac.iitp.anwesha/databases/");
        folder.mkdirs();
        File file = new File(folder, "websyncdb");

        if (!file.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                InputStream is = context.getAssets().open("websyncdb");
                byte[] bytes = new byte[1024 * 300];
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

    }

    @Override
    public void onCreate() {
        super.onCreate();

        font_Anwesha = Typeface.createFromAsset(getAssets(), "fonts/ardestine.ttf");
        font_AnweshaSub = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");//Typeface.SERIF;//Typeface.createFromAsset(getAssets(),"fonts/agencyfb.tff");

        font_Title = Typeface.createFromAsset(getAssets(), "fonts/modeka.otf");
        font_Sub1 = Typeface.SERIF;//Typeface.createFromAsset(context.getAssets(),"BebasNeue.tff");
        font_Sub2 = Typeface.DEFAULT;//Typeface.createFromAsset(context.getAssets(),"BebasNeue.tff");

        readSharedPref(getApplicationContext());

        loadDatabase(getApplicationContext());
        Intent backgroundService = new Intent(this, BackgroundFetch.class);
        startService(backgroundService);
        Log.e("AllIDS", "Staring Background Service");

    }
}
