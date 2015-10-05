package in.ac.iitp.anwesha;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by gagan on 4/10/15.
 */
public class Reminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra("Notification"))
        {
            String title = intent.getStringExtra("title");
            String subtitle = intent.getStringExtra("stitle");
            int id= intent.getIntExtra("event_id",-1);
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notif = new Notification(R.mipmap.ic_launcher,
                    title, System.currentTimeMillis());

            Intent notificationIntent = new Intent(context, EventDetails.class);

            notificationIntent.putExtra("eventID", id);
            notificationIntent.putExtra("eventName", title);
            notificationIntent.putExtra("eventDesc", subtitle);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pintent = PendingIntent.getActivity(context, 0,
                    notificationIntent, 0);

            notif.contentIntent = pintent;

            //notif.setLatestEventInfo(context, title, "Event time is near", pintent);
            //nm.notify(1, notif);

        }
    }
}
