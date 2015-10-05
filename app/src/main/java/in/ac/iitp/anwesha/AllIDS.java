package in.ac.iitp.anwesha;

import android.app.Application;
import android.graphics.Typeface;
import android.widget.Toast;

/**
 * Created by gagan on 4/10/15.
 */
public class AllIDS extends Application {
    static int BACKGROUND_HOME = R.drawable.bg;

    /*
    static int ICON_archives = R.drawable.archives;
    static int ICON_events = R.drawable.events;
    static int ICON_gallery = R.drawable.gallery;
    static int ICON_home = R.drawable.home;
    static int ICON_pronites = R.drawable.pronites;
    static int ICON_schedule = R.drawable.schedule;
    static int ICON_sponsors = R.drawable.sponsors;
    static int ICON_team = R.drawable.team;

    static int SOCIAL_fb = R.drawable.fb;
    static int SOCIAL_yt = R.drawable.yt;
    static int SOCIAL_tw = R.drawable.tw;
    static int SOCIAL_gp = R.drawable.gp;
*/

    static Typeface font_Anwesha;
    static Typeface font_AnweshaSub;

    static Typeface font_Title;
    static Typeface font_Sub1;
    static Typeface font_Sub2;

    @Override
    public void onCreate() {
        super.onCreate();

        font_Anwesha = Typeface.createFromAsset(getAssets(),"fonts/ardestine.ttf");
        font_AnweshaSub =  Typeface.createFromAsset(getAssets(),"fonts/caviardreams.ttf");//Typeface.SERIF;//Typeface.createFromAsset(getAssets(),"fonts/agencyfb.tff");

        font_Title = Typeface.createFromAsset(getAssets(),"fonts/modeka.otf");
        font_Sub1 = Typeface.SERIF;//Typeface.createFromAsset(context.getAssets(),"BebasNeue.tff");
        font_Sub2 = Typeface.DEFAULT;//Typeface.createFromAsset(context.getAssets(),"BebasNeue.tff");

    }

}
