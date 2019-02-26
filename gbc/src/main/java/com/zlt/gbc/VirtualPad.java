package com.zlt.gbc;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class VirtualPad extends RelativeLayout {

    private Context context;
    private ImageView dpadUp,dpadDown,dpadLeft,dpadRight;

    private int bgNormalDpad = R.drawable.bg_dpad;
    private int bgClickDpad = R.drawable.bg_dpad_click;

    public void setBgNormalDpad(int bgNormalDpad) {
        this.bgNormalDpad = bgNormalDpad;
    }

    public void setBgClickDpad(int bgClickDpad) {
        this.bgClickDpad = bgClickDpad;
    }

    public VirtualPad(Context context) {
        super(context);
        initView(context);
    }

    public VirtualPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VirtualPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VirtualPad(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context){
        this.context = context;
        View v = LayoutInflater.from(context).inflate(R.layout.view_pad,null);
        dpadDown = v.findViewById(R.id.dpadDown);
        dpadUp = v.findViewById(R.id.dpadUp);
        dpadLeft = v.findViewById(R.id.dpadLeft);
        dpadRight = v.findViewById(R.id.dpadRight);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(params);
        addView(v);
    }

    public void setBackgroundClicked(int id){
        switch (id){
            case VirtualTrackball.ActionTrackball.ACTION_BOTTOM:
                dpadDown.setBackgroundResource(bgClickDpad);
                break;
            case VirtualTrackball.ActionTrackball.ACTION_TOP:
                dpadUp.setBackgroundResource(bgClickDpad);
                break;
            case VirtualTrackball.ActionTrackball.ACTION_LEFT:
                dpadLeft.setBackgroundResource(bgClickDpad);
                break;
            case VirtualTrackball.ActionTrackball.ACTION_RIGHT:
                dpadRight.setBackgroundResource(bgClickDpad);
                break;
        }
    }

    public void resetColor(){
        int res = bgNormalDpad;
        dpadDown.setBackgroundResource(res);
        dpadUp.setBackgroundResource(res);
        dpadLeft.setBackgroundResource(res);
        dpadRight.setBackgroundResource(res);
    }

}
