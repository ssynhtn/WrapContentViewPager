package com.ssynhtn.viewpagertest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.ssynhtn.wrapcontentviewpager.ViewPagerEdgeEffect;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerEdgeEffect.fixViewPager(this, viewPager);
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
