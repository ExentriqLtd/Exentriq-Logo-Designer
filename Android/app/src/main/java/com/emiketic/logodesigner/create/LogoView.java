package com.emiketic.logodesigner.create;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.emiketic.logodesigner.R;

/**
 * Created by stoufa on 09/07/15.
 */
public class LogoView extends View {
    boolean mShowLabel;

    public LogoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LogoView,
                0, 0);

        try {
            mShowLabel = a.getBoolean(R.styleable.LogoView_showLabel, true);
        } finally {
            a.recycle();
        }
    }

    public boolean isShowLabel() {
        return mShowLabel;
    }

    public void setShowLabel(boolean showLabel) {
        mShowLabel = showLabel;
        invalidate();
        requestLayout();
    }
}
