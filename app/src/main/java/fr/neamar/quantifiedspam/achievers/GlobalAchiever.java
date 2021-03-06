package fr.neamar.quantifiedspam.achievers;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class GlobalAchiever extends Achiever {
    public GlobalAchiever() {
        super("GlobalAchiever", "Global notification achievement", new String[]{
                "",
                "",
                "Getting the hang of it",
                "You have friends!",
                "You have friends and family!",
                "Spam!",
                "Spammed!",
                "Spammedest!",
                "Spammedestest!",
                "tl;dr",
                "Machine learner"
        });
    }

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

        Log.i(tag, String.format("Current: %s [%s]", currentValue, currentMilestone));

        if (currentValue >= currentMilestone) {
            String name = achievementNames[Math.min(currentMilestoneIndex, achievementNames.length - 1)];
            Log.i(tag, String.format("Unlocked potential achievement: %s (%s)", name, currentValue));

            if (!name.isEmpty()) {
                unlockAchievement(context, getPrefsKey(Integer.toString(currentMilestone)), name, String.format("Get %s notifications in total", currentValue), humanTag);
            }

            editor.putInt(getPrefsKey("milestoneIndex"), currentMilestoneIndex + 1);
        }
    }
}
