package com.ssynhtn.wrapcontentviewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * Created by huangtongnao on 2019/6/21.
 * Email: huangtongnao@gmail.com
 */
public class ViewPagerEdgeEffect extends EdgeEffect {
    private @NonNull ViewPager viewPager;
    private boolean isLeft;

    public ViewPagerEdgeEffect(Context context, @NonNull ViewPager viewPager, boolean isLeft) {
        super(context);
        this.viewPager = viewPager;
        this.isLeft = isLeft;
    }

    public static void fixViewPager(Context context, @NonNull ViewPager viewPager) {
        ViewPagerEdgeEffect left = new ViewPagerEdgeEffect(context, viewPager, true);
        ViewPagerEdgeEffect right = new ViewPagerEdgeEffect(context, viewPager, false);

        try {
            Field leftField = ViewPager.class.getDeclaredField("mLeftEdge");
            leftField.setAccessible(true);
            leftField.set(viewPager, left);

            Field rightField = ViewPager.class.getDeclaredField("mRightEdge");
            rightField.setAccessible(true);
            rightField.set(viewPager, right);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean draw(Canvas canvas) {
        int saveCount = canvas.save();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && viewPager.getClipToPadding()) {
            if (isLeft) {
                canvas.translate(-2 * viewPager.getPaddingTop(), 0);
            } else {
                canvas.translate(2 * viewPager.getPaddingTop(), getLastOffset() * (viewPager.getPaddingLeft() + viewPager.getPaddingRight()));
            }
        } else {
            setSize(viewPager.getHeight(), viewPager.getWidth());
            if (isLeft) {
                canvas.translate(-2 * viewPager.getPaddingTop() -viewPager.getPaddingBottom(), 0);
            } else {
                canvas.translate(viewPager.getPaddingTop(), getLastOffset() * (viewPager.getPaddingLeft() + viewPager.getPaddingRight()));
            }
        }

        boolean result = super.draw(canvas);
        canvas.restoreToCount(saveCount);
        return result;
    }

    private Field lastOffsetField;
    private float getLastOffset() {
        if (lastOffsetField == null) {
            try {
                lastOffsetField = ViewPager.class.getDeclaredField("mLastOffset");
                lastOffsetField.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (lastOffsetField != null) {
            try {
                return lastOffsetField.getFloat(viewPager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }
}
