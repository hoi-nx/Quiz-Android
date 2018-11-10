package com.mteam.multichoicequiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.mteam.multichoicequiz.DBHelper.DBHelper;
import com.mteam.multichoicequiz.adapter.AnswerSheetAdapter;
import com.mteam.multichoicequiz.adapter.FragmentAdapter;
import com.mteam.multichoicequiz.common.Common;
import com.mteam.multichoicequiz.model.CurrentQuestion;

import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int time_play = Common.TOTAL_TIME;
    boolean isAnswerModeview = false;
    RecyclerView rc_answer_sheet;
    CountDownTimer countDownTimer;
    private AnswerSheetAdapter answerSheetAdapter;
    private TextView tv_right_answer, tv_timer;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //
        takeQuestion();
        if (Common.questionList.size() > 0) {

            //show text timeer
            tv_timer = findViewById(R.id.tv_timer);
            tv_right_answer = findViewById(R.id.tv_question_right);
            tv_right_answer.setVisibility(View.VISIBLE);
            tv_timer.setVisibility(View.VISIBLE);
            tv_right_answer.setText(new StringBuilder(String.format("%d/%d",Common.right_answer,Common.questionList.size())));
            countTime();
            rc_answer_sheet = findViewById(R.id.rc_anwser);
            rc_answer_sheet.setHasFixedSize(true);
            //rc_answer_sheet.setLayoutManager(new GridLayoutManager(this,2));
            if (Common.questionList.size() > 5) {
                rc_answer_sheet.setLayoutManager(new GridLayoutManager(this, Common.questionList.size() / 2));
            }
            answerSheetAdapter = new AnswerSheetAdapter(Common.listAnswerSheet);
            rc_answer_sheet.setAdapter(answerSheetAdapter);
        }


        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab_answer);
        getFragmentList();

        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),this,Common.listFragment);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getFragmentList() {
        for(int i=0;i<Common.questionList.size();i++){
            Bundle bundel=new Bundle();
            bundel.putInt("index",i);
            QuestionFragment questionFragment=new QuestionFragment();
            questionFragment.setArguments(bundel);
            Common.listFragment.add(questionFragment);
        }
    }


    private void countTime() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(Common.TOTAL_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tv_timer.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    time_play -= 1000;


                }

                @Override
                public void onFinish() {

                }
            }.start();
        } else {
            countDownTimer.cancel();
            countDownTimer = new CountDownTimer(Common.TOTAL_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tv_timer.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    time_play -= 1000;


                }

                @Override
                public void onFinish() {

                }
            }.start();
        }

    }

    private void takeQuestion() {
        Common.questionList = DBHelper.getInstance(this).getListQuestionByCategory(Common.categorySelected.getId());
        Log.d("", "takeQuestion: "+Common.questionList.size());
        if (Common.questionList.size() == 0) {
            new MaterialStyledDialog.Builder(this).setTitle("Oppa").setIcon(R.drawable.ic_menu_send)
                    .setDescription("We dont have any question in this" + Common.categorySelected.getName() + " category")
                    .setPositiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();

        }else {
            if(Common.listAnswerSheet.size()>0){
                Common.listAnswerSheet.clear();
            }
            for(int i=0;i<Common.questionList.size();i++){
                Common.listAnswerSheet.add(new CurrentQuestion(i,Common.ANSWER_TYPE.NO_ANSWER));
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
