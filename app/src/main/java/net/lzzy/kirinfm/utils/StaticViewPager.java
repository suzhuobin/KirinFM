package net.lzzy.kirinfm.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author Administrator
 */
public class StaticViewPager extends ViewPager {
    private boolean slide = false;

    public StaticViewPager(@NonNull Context context) {
        super(context);
    }

    public StaticViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void enableSlide(boolean enabled) {
        slide = enabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (slide) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (slide) {
            return super.onTouchEvent(ev);
        }
        return true;
    }
}
