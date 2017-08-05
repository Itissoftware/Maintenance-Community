package com.mncomunity1.until;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView2 extends TextView {

    public MyTextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView2(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "THSarabunNew.ttf");
        setTypeface(tf);
    }

}