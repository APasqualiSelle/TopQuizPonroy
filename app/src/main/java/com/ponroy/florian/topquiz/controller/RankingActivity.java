package com.ponroy.florian.topquiz.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ponroy.florian.topquiz.R;
import com.ponroy.florian.topquiz.model.Ranking;
import com.ponroy.florian.topquiz.model.Score;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private LinearLayout mRow2LinearLayout;
    private LinearLayout mRow3LinearLayout;
    private LinearLayout mRow4LinearLayout;
    private LinearLayout mRow5LinearLayout;

    private TextView mName1Text;
    private TextView mName2Text;
    private TextView mName3Text;
    private TextView mName4Text;
    private TextView mName5Text;

    private TextView mScore1Text;
    private TextView mScore2Text;
    private TextView mScore3Text;
    private TextView mScore4Text;
    private TextView mScore5Text;

    private Button mSortByNameBtn;
    private Button mSortByScoreBtn;

    private List<Score> mScoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        bindViews();

        mSortByNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mScoreList, new Comparator<Score>() {
                    @Override
                    public int compare(Score lhs, Score rhs) {
                        String name1 = lhs.getUser().getFirstname();
                        String name2 = rhs.getUser().getFirstname();
                        return name1.compareTo(name2);
                    }
                });

                RankingActivity.this.showRanking();
            }
        });

        mSortByScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mScoreList, new Comparator<Score>() {
                    @Override
                    public int compare(Score lhs, Score rhs) {
                        int score1 = lhs.getScore();
                        int score2 = rhs.getScore();
                        return (score1 > score2) ? -1 : (score1 < score2) ? 1 : 0;
                    }
                });

                RankingActivity.this.showRanking();
            }
        });

        // Load scores. Won't change any further.
        mScoreList = new Ranking(this).loadScores();

        showRanking();
    }

    private void bindViews() {
        // Don't need to mess with row1, since it's always displayed.
        mRow2LinearLayout = (LinearLayout) findViewById(R.id.activity_ranking_row2_ll);
        mRow3LinearLayout = (LinearLayout) findViewById(R.id.activity_ranking_row3_ll);
        mRow4LinearLayout = (LinearLayout) findViewById(R.id.activity_ranking_row4_ll);
        mRow5LinearLayout = (LinearLayout) findViewById(R.id.activity_ranking_row5_ll);

        mName1Text = (TextView) findViewById(R.id.activity_ranking_name1_txt);
        mName2Text = (TextView) findViewById(R.id.activity_ranking_name2_txt);
        mName3Text = (TextView) findViewById(R.id.activity_ranking_name3_txt);
        mName4Text = (TextView) findViewById(R.id.activity_ranking_name4_txt);
        mName5Text = (TextView) findViewById(R.id.activity_ranking_name5_txt);

        mScore1Text = (TextView) findViewById(R.id.activity_ranking_score1_txt);
        mScore2Text = (TextView) findViewById(R.id.activity_ranking_score2_txt);
        mScore3Text = (TextView) findViewById(R.id.activity_ranking_score3_txt);
        mScore4Text = (TextView) findViewById(R.id.activity_ranking_score4_txt);
        mScore5Text = (TextView) findViewById(R.id.activity_ranking_score5_txt);

        mSortByNameBtn = (Button) findViewById(R.id.activity_ranking_sort_by_name_btn);
        mSortByScoreBtn = (Button) findViewById(R.id.activity_ranking_sort_by_score_btn);
    }

    private void showRanking() {
        // There must be at least one score
        mName1Text.setText(mScoreList.get(0).getUser().getFirstname());
        mScore1Text.setText(String.valueOf(mScoreList.get(0).getScore()));

        if (mScoreList.size() >= 2) {
            mRow2LinearLayout.setVisibility(View.VISIBLE);
            mName2Text.setText(mScoreList.get(1).getUser().getFirstname());
            mScore2Text.setText(String.valueOf(mScoreList.get(1).getScore()));
        }

        if (mScoreList.size() >= 3) {
            mRow3LinearLayout.setVisibility(View.VISIBLE);
            mName3Text.setText(mScoreList.get(2).getUser().getFirstname());
            mScore3Text.setText(String.valueOf(mScoreList.get(2).getScore()));
        }

        if (mScoreList.size() >= 4) {
            mRow4LinearLayout.setVisibility(View.VISIBLE);
            mName4Text.setText(mScoreList.get(3).getUser().getFirstname());
            mScore4Text.setText(String.valueOf(mScoreList.get(3).getScore()));
        }

        if (mScoreList.size() >= 5) {
            mRow5LinearLayout.setVisibility(View.VISIBLE);
            mName5Text.setText(mScoreList.get(4).getUser().getFirstname());
            mScore5Text.setText(String.valueOf(mScoreList.get(4).getScore()));
        }
    }
}
