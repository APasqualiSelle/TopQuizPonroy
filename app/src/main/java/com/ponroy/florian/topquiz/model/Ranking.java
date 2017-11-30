package com.ponroy.florian.topquiz.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Florian PONROY - OpenClassrooms on 30/11/17.
 */

public class Ranking {

    public static final String PREF_KEY_RANKING = "PREF_KEY_RANKING";

    private Context mContext;
    private SharedPreferences mPreferences;
    private static final int RANKING_ENTRIES = 5;

    public Ranking(Context context) {
        mContext = context;
        mPreferences = mContext.getSharedPreferences("RANKING", MODE_PRIVATE);
    }

    public boolean isEmpty() {
        return mPreferences.getString(PREF_KEY_RANKING, null) != null;
    }

    public List<Score> loadScores() {
        // Fetch the current ranking
        String rankingAsString = mPreferences.getString(PREF_KEY_RANKING, null);

        List<Score> ranking = null;

        if (null == rankingAsString) {
            // Create a new score list, since it doesn't exist yet
            ranking = new ArrayList<>();
        } else {
            // Deserialize the score list
            Type targetClassType = new TypeToken<ArrayList<Score>>() { }.getType();
            ranking = new Gson().fromJson(rankingAsString, targetClassType);
        }

        return ranking;
    }

    public void saveScores(List<Score> ranking) {
        // Sort the list, higher score first
        Collections.sort(ranking, new Comparator<Score>() {
            @Override
            public int compare(Score lhs, Score rhs) {
                int score1 = lhs.getScore();
                int score2 = rhs.getScore();
                return (score1 > score2) ? -1 : (score1 < score2) ? 1 : 0;
            }
        });

        // Keep only the five bettor scores
        if (ranking.size() > RANKING_ENTRIES) {
            ranking = ranking.subList(0, RANKING_ENTRIES);
        }

        // Serialize the score list and save it to the preferences
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        mPreferences.edit().putString(PREF_KEY_RANKING, gson.toJson(ranking)).apply();
    }
}
