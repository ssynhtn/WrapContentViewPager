package com.ssynhtn.wrapcontentviewpager;

import android.content.Context;
import android.database.DataSetObserver;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WrapContentViewPager extends ViewPager {

    private static final String TAG = WrapContentViewPager.class.getSimpleName();

    boolean dataChangedFlag;
    int lastHeightWithoutPadding;

    DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            dataChangedFlag = true;
            Log.d(TAG, "dataChanged");
        }
    };

    public WrapContentViewPager(Context context) {
        this(context, null);
    }

    public WrapContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        PagerAdapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(dataSetObserver);
        }

        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerDataSetObserver(dataSetObserver);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int heightWithoutPadding = lastHeightWithoutPadding;
        Log.d(TAG, "starting height " + heightWithoutPadding);
        if (dataChangedFlag) {
            heightWithoutPadding = 0;
            dataChangedFlag = false;

            Log.d(TAG, "data was changed, starting height set to 0");
        }

        //下面遍历所有child的高度
        int childWidthSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.getMode(widthMeasureSpec));
        int childHeightSpec;
        Log.d(TAG, "parent height mode was " + modeStr(heightMode));
        if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(Math.max(0, heightSize - getPaddingTop() - getPaddingBottom()), MeasureSpec.AT_MOST);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        setInLayout(true);
        populateSuper();
        setInLayout(false);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(childWidthSpec, childHeightSpec);

            LayoutParams childLayoutParams = (LayoutParams) child.getLayoutParams();
            clearNeedsMeasure(childLayoutParams);

            int h = child.getMeasuredHeight();
            Log.d(TAG, "child " + i + " measured height " + h);
            if (h > heightWithoutPadding) { //采用最大的view的高度。
                Log.d(TAG, "upgrading height to " + h);
                heightWithoutPadding = h;
            }
        }


        int height = heightWithoutPadding + getPaddingTop() + getPaddingBottom();
        Log.d(TAG, "desired height is " + heightWithoutPadding + " padding is " + (getPaddingTop() + getPaddingBottom()) + ", total " + height);
        if (heightMode == MeasureSpec.AT_MOST) {
            if (height > heightSize) {
                height = heightSize;
                Log.d(TAG, "constraint height to " + height + " cause spec says AT_MOST");
            }
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
            Log.d(TAG, "set height to " + height + " cause spec says EXACTLY");
        }

        lastHeightWithoutPadding = Math.max(0, height - getPaddingTop() - getPaddingBottom());
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    Field needsMeasure;
    private void clearNeedsMeasure(LayoutParams layoutParams) {
        if (needsMeasure == null) {
            try {
                needsMeasure = LayoutParams.class.getDeclaredField("needsMeasure");
                needsMeasure.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            if (needsMeasure != null) {
                needsMeasure.set(layoutParams, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Method pop;
    void populateSuper() {
        if (pop == null) {
            try {
                pop = ViewPager.class.getDeclaredMethod("populate");
                pop.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            if (pop != null) {
                pop.invoke(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Field inLayout;
    void setInLayout(boolean value) {
        assureInLayoutField();

        try {
            if (inLayout != null) {
                inLayout.set(this, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assureInLayoutField() {
        if (inLayout == null) {
            try {
                inLayout = ViewPager.class.getDeclaredField("mInLayout");
                inLayout.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String modeStr(int mode) {
        switch (mode) {
            case MeasureSpec.EXACTLY: return "EXACTLY";
            case MeasureSpec.AT_MOST: return "AT_MOST";
            default: return "UNSPECIFIED";
        }
    }
}
