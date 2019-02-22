package com.zlt.gbc;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class VirtualKeyboard extends RelativeLayout {

    private Context context;
    private CircleTrackball ball;
    private ActionTrackball actionTrackball;

    public VirtualKeyboard(Context context) {
        super(context);
        initView(context);
    }

    public VirtualKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VirtualKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VirtualKeyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public ActionTrackball getActionTrackball() {
        return actionTrackball;
    }

    private void initView(Context context) {
        this.context = context;
        ball = (CircleTrackball) ball;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View ball = getChildAt(0);
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
                Log.e("VirtualKeyPos","UP");
                ball.setX(getPivotX()-ball.getWidth()/2);
                ball.setY(getPivotY()-ball.getHeight()/2);
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
        if (actionTrackball!=null){
            doAction(pos);
        }
    }

    private void doAction(int action){
        switch (action){
            case ActionTrackball.ACTION_TOPLEFT:
                actionTrackball.onTopLeft();
                break;
            case ActionTrackball.ACTION_TOP:
                actionTrackball.onTop();
                break;
            case ActionTrackball.ACTION_TOPRIGHT:
                actionTrackball.onTopRight();
                break;
            case ActionTrackball.ACTION_LEFT:
                actionTrackball.onLeft();
                break;
            case ActionTrackball.ACTION_CENTER:
                actionTrackball.onCenter();
                break;
            case ActionTrackball.ACTION_RIGHT:
                actionTrackball.onRight();
                break;
            case ActionTrackball.ACTION_BOTTOMLEFT:
                actionTrackball.onBottomLeft();
                break;
            case ActionTrackball.ACTION_BOTTOM:
                actionTrackball.onBottom();
                break;
            case ActionTrackball.ACTION_BOTTOMRIGHT:
                actionTrackball.onBottomRight();
                break;
        }
    }

    private int getPos(int x, int y){
        View ball = getChildAt(0);
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
    }

    public class ActionTrackball implements IAction{
        public static final int ACTION_TOPLEFT = 1;
        public static final int ACTION_TOP = 2;
        public static final int ACTION_TOPRIGHT = 3;
        public static final int ACTION_LEFT = 4;
        public static final int ACTION_CENTER = 5;
        public static final int ACTION_RIGHT = 6;
        public static final int ACTION_BOTTOMLEFT = 7;
        public static final int ACTION_BOTTOM = 8;
        public static final int ACTION_BOTTOMRIGHT = 9;
        @Override
        public void onTop() {

        }

        @Override
        public void onBottom() {

        }

        @Override
        public void onLeft() {

        }

        @Override
        public void onRight() {

        }

        @Override
        public void onTopLeft() {

        }

        @Override
        public void onTopRight() {

        }

        @Override
        public void onBottomLeft() {

        }

        @Override
        public void onBottomRight() {

        }

        @Override
        public void onCenter() {

        }
    }
}
