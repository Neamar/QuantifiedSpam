package fr.neamar.quantifiedspam.achievers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.neamar.quantifiedspam.MainActivity;
import fr.neamar.quantifiedspam.R;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by neamar on 10/10/16.
 */

public abstract class Achiever {
    public final String tag;
    public final String humanTag;
    public final String[] achievementNames;

    public static final int[] MILESTONES = new int[]{
            10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000, Integer.MAX_VALUE
    };

    public static Achiever[] achievers = new Achiever[]{
            new GlobalAchiever(),
            new DayAchiever(),
            new WeekAchiever(),
    };

    public static void onNotificationReceived(Context context, StatusBarNotification sbn) {
        SharedPreferences prefs = context.getSharedPreferences("achievements_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (Achiever achiever : achievers) {
            achiever.onNotificationReceived(context, sbn, prefs, editor);
        }

        editor.apply();
    }

    public Achiever(String tag, String humanTag, String[] names) {
        this.tag = tag;
        this.humanTag = humanTag;
        achievementNames = names;
    }

    public abstract void onNotificationReceived(Context context, StatusBarNotification sbn, SharedPreferences prefs, SharedPreferences.Editor editor);

    public void unlockAchievement(Context context, String id, String title, String description, String achievementType) {
        Log.e(tag, "Unlocked achievement " + id + ": " + title + " " + description);

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id.hashCode(), intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setSubText(achievementType)
                        .setContentIntent(pendingIntent)
                        .setContentText(description);

        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(2, mBuilder.build());

        SharedPreferences prefs = context.getSharedPreferences("achievements_unlocked", MODE_PRIVATE);

        try {
            JSONArray achievements = new JSONArray(prefs.getString("achievements", "[]"));

            JSONObject achievement = new JSONObject();
            achievement.put("id", id);
            achievement.put("title", title);
            achievement.put("description", description);
            achievement.put("type", achievementType);

            achievements.put(achievement);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("achievements", achievements.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected String getPrefsKey(String name) {
        return this.tag + "_" + name;
    }
}
