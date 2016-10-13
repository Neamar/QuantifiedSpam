package fr.neamar.quantifiedspam.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.neamar.quantifiedspam.R;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {
    private JSONArray achievements;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleView;
        public final TextView descriptionView;
        public final TextView typeView;

        public ViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.achievement_title);
            descriptionView = (TextView) v.findViewById(R.id.achievement_description);
            typeView = (TextView) v.findViewById(R.id.achievement_type);
        }

        public void bind(JSONObject achievement) {
            titleView.setText(achievement.optString("title"));
            descriptionView.setText(achievement.optString("description"));
            typeView.setText(achievement.optString("type"));
        }
    }

    public AchievementAdapter(JSONArray achievements) {
        this.achievements = achievements;
    }

    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_achievement, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.bind(achievements.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return achievements.length();
    }
}