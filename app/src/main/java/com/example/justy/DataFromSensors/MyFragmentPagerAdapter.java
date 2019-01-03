package com.example.justy.DataFromSensors;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private int count;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        count = 4;
    }


    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new DayFragmenter();
        } else if (i == 1) {
            return new WeekFragmenter();
        } else if (i == 2) {
            return new MonthFragmenter();
        } else {
            return new YearFragmenter();
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.dayTabName);
            case 1:
                return context.getString(R.string.weekTabName);
            case 2:
                return context.getString(R.string.monthTabName);
            case 3:
                return context.getString(R.string.yearTabName);
            default:
                return null;
        }
    }
}
