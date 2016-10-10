package fr.neamar.quantifiedspam.achievers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import fr.neamar.quantifiedspam.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by neamar on 10/10/16.
 */

public abstract class Achiever {
    public final String TAG = "Achiever";

    public static final int[] MILESTONES = new int[] {
            10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000, Integer.MAX_VALUE
    };

    public static Achiever[] achievers = new Achiever[] {
            new GlobalAchiever(),
    };

    public static void onNotificationReceived(Context context, StatusBarNotification sbn) {
        SharedPreferences prefs = context.getSharedPreferences("achievements_data", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for(Achiever achiever: achievers) {
            achiever.onNotificationReceived(context, sbn, prefs, editor);
        }

        editor.apply();
    }

    public abstract void onNotificationReceived(Context context, StatusBarNotification sbn, SharedPreferences prefs, SharedPreferences.Editor editor);

    public void unlockAchievement(Context context, String id, String title, String description, String achievementType) {
        Log.e(TAG, "Unlocked achievement " + id + ": " + title + " " + description);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setSubText(achievementType)
                        .setContentText(description);

        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(2, mBuilder.build());
    }

    public String getPrefsKey(String name) {
        return this.TAG + "_" + name;
    }
}
