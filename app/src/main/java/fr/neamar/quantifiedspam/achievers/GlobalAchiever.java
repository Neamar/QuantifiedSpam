package fr.neamar.quantifiedspam.achievers;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by neamar on 10/10/16.
 */

public class GlobalAchiever extends Achiever {
    public final String TAG = "GlobalAchiever";
    public final String HUMAN_TAG = "Global notification achievement";

    public final String[] NAMES = new String[] {
            "Getting the hang of it",
            "You have friends!",
            "You have friends and family!",
            "Spam!",
            "Spammed!",
            "Spammedest!",
            "Spammedestest!",
            "tl;dr",
            "Machine learner"
    };
    @Override
    public void onNotificationReceived(Context context, StatusBarNotification sbn, SharedPreferences prefs, SharedPreferences.Editor editor) {
        // Read previous values
        int currentMilestoneIndex = prefs.getInt(getPrefsKey("milestoneIndex"), 0);
        int currentMilestone = MILESTONES[currentMilestoneIndex];

        int currentValue = prefs.getInt(getPrefsKey("count"), 0);

        // Increment value locally
        currentValue += 1;

        // Store new value
        editor.putInt(getPrefsKey("count"), currentValue);

        Log.i(TAG, String.format("Current: %s [%s]", currentValue, currentMilestone));

        if(currentValue >= currentMilestone) {
            unlockAchievement(context, getPrefsKey(Integer.toString(currentMilestone)), NAMES[Math.min(currentMilestoneIndex, NAMES.length - 1)], String.format("Get %s notifications", currentValue), HUMAN_TAG);
            editor.putInt(getPrefsKey("count"), 0);
        }
    }
}
