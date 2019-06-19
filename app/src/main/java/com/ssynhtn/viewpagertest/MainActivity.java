package com.ssynhtn.viewpagertest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager(), 8));
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));

    }

    class Adapter extends FragmentPagerAdapter {

        List<Integer> sizeList;

        public Adapter(FragmentManager fm, int size) {
            super(fm);
            sizeList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                sizeList.add(i * 2 + 4);
            }
        }

        @Override
        public Fragment getItem(int i) {
            return SimpleListFragment.newInstance(sizeList.get(i));
        }

        @Override
        public int getCount() {
            return sizeList.size();
        }
    }
}
