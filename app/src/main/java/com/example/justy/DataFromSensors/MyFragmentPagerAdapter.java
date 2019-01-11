package com.example.justy.DataFromSensors;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private int numOfTabs;
    private Fragment fragment;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context, int numOfTabs) {
        super(fm);
        this.context = context;
        this.numOfTabs = numOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = new DayFragment();
                return fragment;
            case 1:
                fragment = new WeekFragment();
                return fragment;
            case 2:
                fragment = new MonthFragment();
                return fragment;
            case 3:
                fragment = new YearFragment();
                return fragment;
            default:
                fragment = null;
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
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
