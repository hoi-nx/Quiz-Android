package com.mteam.multichoicequiz.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mteam.multichoicequiz.QuestionFragment;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    Context context;
    List<QuestionFragment>list;
    public FragmentAdapter(FragmentManager fm,Context context,List<QuestionFragment>list) {
        super(fm);
        this.context=context;
        this.list=list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return new StringBuilder("Question").append(position+1).toString();

    }
}
