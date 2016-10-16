package fr.neamar.quantifiedspam;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import fr.neamar.quantifiedspam.achievers.Achiever;

/**
 * Created by neamar on 27/09/16.
 */
public class NotificationListener extends NotificationListenerService {
    public static final String TAG = "NotificationListener";

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        NotificationParser notificationParser = new NotificationParser(sbn);

        if(!notificationParser.shouldBeTracked()) {
            Log.v(TAG, "Ignoring notification from " + notificationParser.getPackageName());
            return;
        }

        String packageName = notificationParser.getPackageName();
        String title = notificationParser.getTitle();
        String account = notificationParser.getAccount();
        Log.i(TAG, String.format("%s => %s [%s]", packageName, title, account));

        // Write to DB
        DB.insertNotification(getBaseContext(),packageName , title, account, notificationParser.hasVibration(), notificationParser.hasSound());

        // Unlock achievements
        Achiever.onNotificationReceived(getBaseContext(), sbn);

        super.onNotificationPosted(sbn);
    }
}
