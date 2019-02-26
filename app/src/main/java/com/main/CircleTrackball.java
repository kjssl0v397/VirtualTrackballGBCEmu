package com.main;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class CircleTrackball extends View {

    public CircleTrackball(Context context) {
        super(context);
    }

    public CircleTrackball(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleTrackball(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleTrackball(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
