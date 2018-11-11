package com.mteam.multichoicequiz;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.mteam.multichoicequiz.adapter.ResultAdapter;
import com.mteam.multichoicequiz.common.Common;

import java.util.concurrent.TimeUnit;

public class ResultActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private TextView tvTime, tvTotalQuestion;
    private Button btn_filter_total, btn_filter_right, btn_filter_wrong, btn_filter_no;

    private RecyclerView recyclerView;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Common.KET_BACK_RESULT)) {
                int question = intent.getIntExtra(Common.KEY_BACK_RESULT, -1);
                goBackWithQuestion(question);
            }

        }
    };

    private void goBackWithQuestion(int question) {
        Intent intent = new Intent();
        intent.putExtra(Common.KEY_BACK_RESULT, question);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Common.KET_BACK_RESULT));

        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("RESULT");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvTime = findViewById(R.id.tv_time);
        tvTotalQuestion = findViewById(R.id.tv_total_question);
        btn_filter_total = findViewById(R.id.btn_filter_total);
        btn_filter_right = findViewById(R.id.btn_filter_right);
        btn_filter_wrong = findViewById(R.id.btn_filter_wrong);
        btn_filter_no = findViewById(R.id.btn_filter_no_answer);

        recyclerView = findViewById(R.id.rc_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        ResultAdapter resultAdapter = new ResultAdapter(Common.listAnswerSheet, this);

        recyclerView.setAdapter(resultAdapter);

        tvTime.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(Common.timer),
                TimeUnit.MILLISECONDS.toSeconds(Common.timer)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Common.timer))));

        btn_filter_total.setText(""+Common.questionList.size());
        btn_filter_right.setText(""+Common.right_answer);
        btn_filter_wrong.setText(""+Common.wrong_count_answer);
        btn_filter_no.setText(""+Common.no_answer);
    }
}
