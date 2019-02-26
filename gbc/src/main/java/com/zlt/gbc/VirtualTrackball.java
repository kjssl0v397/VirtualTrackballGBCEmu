package com.zlt.gbc;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class VirtualTrackball extends RelativeLayout {

    private Context context;
    private CircleTrackball ball;
    private VirtualPad pad;
    private IAction actionTrackball;
    private boolean isDpad;

    public VirtualTrackball(Context context) {
        super(context);
        initView(context);
    }

    public VirtualTrackball(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VirtualTrackball(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VirtualTrackball(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public IAction getActionTrackball() {
        return actionTrackball;
    }

    public void setActionTrackball(IAction actionTrackball) {
        this.actionTrackball = actionTrackball;
    }

    private void initView(Context context) {
        this.context = context;
        pad = new VirtualPad(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        pad.setLayoutParams(params);
        pad.setVisibility(GONE);
        addView(pad);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ball==null)
            ball = (CircleTrackball) getChildAt(1);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ball.onTouchEvent(event);
                checkEvent(ball,event);
                break;
            case MotionEvent.ACTION_MOVE:
                checkEvent(ball,event);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                ball.setX(getPivotX()-ball.getWidth()/2);
                ball.setY(getPivotY()-ball.getHeight()/2);
                if (actionTrackball!=null){
                    actionTrackball.onCancel();
                }
                if (pad!=null)
                    pad.resetColor();
                break;
        }
        return true;
    }

    private void checkEvent(View ball,MotionEvent event){
        int xBall = (int) event.getX()-ball.getWidth()/2;
        int yBall = (int) event.getY()-ball.getHeight()/2;
        if (xBall<0)
            xBall = 0;
        if (yBall<0)
            yBall = 0;
        if (event.getX()>=(getWidth()-ball.getWidth()/2))
            xBall = getWidth()-ball.getWidth();
        if (event.getY()>=(getHeight()-ball.getWidth()/2))
            yBall = getHeight()-ball.getHeight();

        ball.setX(xBall);
        ball.setY(yBall);
        int pos = getPos((int)event.getX(),(int)event.getY());
        doAction(pos);
    }

    private void doAction(int action){
        if (pad!=null)
            pad.resetColor();
        try {
            switch (action) {
                case ActionTrackball.ACTION_TOPLEFT:
                    if (!isDpad)
                        actionTrackball.onTopLeft();
                    break;
                case ActionTrackball.ACTION_TOP:
                    if (pad!=null)
                        pad.setBackgroundClicked(action);
                    actionTrackball.onTop();
                    break;
                case ActionTrackball.ACTION_TOPRIGHT:
                    if (!isDpad)
                        actionTrackball.onTopRight();
                    break;
                case ActionTrackball.ACTION_LEFT:
                    if (pad!=null)
                        pad.setBackgroundClicked(action);
                    actionTrackball.onLeft();
                    break;
                case ActionTrackball.ACTION_CENTER:
                    actionTrackball.onCenter();
                    break;
                case ActionTrackball.ACTION_RIGHT:
                    if (pad!=null)
                        pad.setBackgroundClicked(action);
                    actionTrackball.onRight();
                    break;
                case ActionTrackball.ACTION_BOTTOMLEFT:
                    if (!isDpad)
                        actionTrackball.onBottomLeft();
                    break;
                case ActionTrackball.ACTION_BOTTOM:
                    if (pad!=null)
                        pad.setBackgroundClicked(action);
                    actionTrackball.onBottom();
                    break;
                case ActionTrackball.ACTION_BOTTOMRIGHT:
                    if (!isDpad)
                        actionTrackball.onBottomRight();
                    break;
            }
        }catch (Exception e){
        }
    }

    private int getPos(int x, int y){
        int xPos = -1,yPos = -1;
        if (x<0) xPos = 1;
        if (y<0) yPos = 1;
        if (x>=getWidth()-ball.getWidth()/2) xPos = 3;
        if (y>=getWidth()-ball.getHeight()/2) yPos = 3;
        if (xPos==-1){
            xPos = x/(getWidth()/3)+1;
        }
        if (yPos==-1){
            yPos = y/(getHeight()/3)+1;
        }
        //Key 3*3
        return 3*(yPos-1)+xPos;
    }

    public interface IAction{
        void onTop();
        void onBottom();
        void onLeft();
        void onRight();
        void onTopLeft();
        void onTopRight();
        void onBottomLeft();
        void onBottomRight();
        void onCenter();
        void onCancel();
    }

    public abstract class ActionTrackball implements IAction{
        public static final int ACTION_TOPLEFT = 1;
        public static final int ACTION_TOP = 2;
        public static final int ACTION_TOPRIGHT = 3;
        public static final int ACTION_LEFT = 4;
        public static final int ACTION_CENTER = 5;
        public static final int ACTION_RIGHT = 6;
        public static final int ACTION_BOTTOMLEFT = 7;
        public static final int ACTION_BOTTOM = 8;
        public static final int ACTION_BOTTOMRIGHT = 9;

    }

    private int backgroundTrackball = R.drawable.bg_trackball;
    private int backgroundDpad = R.color.transparent;

    public void setBackgroundTrackball(int backgroundTrackball) {
        this.backgroundTrackball = backgroundTrackball;
    }

    public void setBackgroundDpad(int backgroundDpad) {
        this.backgroundDpad = backgroundDpad;
    }

    public void changeToTrackball(){
        if (ball==null)
            ball = (CircleTrackball) getChildAt(1);
        setBackgroundResource(backgroundTrackball);
        ball.setVisibility(VISIBLE);
        pad.setVisibility(GONE);
        isDpad = false;
    }
    public void changeToDpad4(){
        if (ball==null)
            ball = (CircleTrackball) getChildAt(1);
        setBackgroundResource(backgroundDpad);
        pad.resizePad(getWidth()/3);
        ball.setVisibility(GONE);
        pad.setVisibility(VISIBLE);
        isDpad = true;
    }

    public boolean isDpad() {
        return isDpad;
    }


}
