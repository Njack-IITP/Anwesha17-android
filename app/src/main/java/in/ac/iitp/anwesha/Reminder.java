package in.ac.iitp.anwesha;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


public class Reminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra("Notification"))
        {
            String eventName = intent.getStringExtra("title");
            int id= intent.getIntExtra("event_id", -1);

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent notificationIntent = new Intent(context, EventDetails.class);

            notificationIntent.putExtra("eventID", id);
            notificationIntent.putExtra("eventName", eventName);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pintent = PendingIntent.getActivity(context, 0,
                    notificationIntent, 0);
            Notification.Builder nb = new Notification.Builder(context)
                    .setAutoCancel(true)
                    .setContentTitle("Anwesha")
                    .setContentText(eventName + " is about to Start")
                    .setContentIntent(pintent)
                    .setSmallIcon(R.mipmap.ic_launcher);
           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    nb = nb.addAction(new Notification.Action(R.mipmap.ic_launcher,"Anwesha",pintent));
                }
                else Log.e("Notification","Event Calling Intent, Not Added");

            } else
            {
                Log.e("Notification","Event Calling Intent, Not Added");

            }
            */
            Notification notification = nb.build();
            nm.notify(753246 + id ,notification);//Some Random Number + eventID

            //notif.contentIntent = pintent;

            //notif.setLatestEventInfo(context, title, "Event time is near", pintent);
            //nm.notify(1, notif);

        }

    }
}
