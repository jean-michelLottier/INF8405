package com.inf8402.tps.tp1.bejeweled.activity;


import android.graphics.Rect;
import android.os.Bundle;




import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.inf8402.tps.tp1.bejeweled.service.IMenuService;

public abstract class IActivity extends FragmentActivity {

	protected IMenuService menuService;

	protected long pressStartTime=0;                
	protected float pressedX=0;
	protected float pressedY=0;
	protected long pressDuration=0;
	protected static final int MAX_CLICK_DURATION = 700;
	protected static final int MAX_CLICK_DISTANCE=10;

	protected OnTouchListener singleButtonListener = new OnTouchListener(){

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// TODO Auto-generated method stub
			view.requestFocus();
        	if(event.getAction()==MotionEvent.ACTION_UP)
        	{
        		pressDuration = System.currentTimeMillis() - pressStartTime;
				if (pressDuration < MAX_CLICK_DURATION && distance(pressedX, pressedY, event.getX(), event.getY()) < MAX_CLICK_DISTANCE)
				{
					buttonManager(view.getId());
				}
			}
        	else if(event.getAction()==MotionEvent.ACTION_DOWN)
        	{
        		pressStartTime = System.currentTimeMillis();                
                pressedX = event.getX();
                pressedY = event.getY();
        	}
			return true;
		}
	};
	protected OnTouchListener multipleButtonsListener = new OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(v instanceof LinearLayout)
			{
				LinearLayout layout = (LinearLayout)v;
				
			    for(int i =0; i< layout.getChildCount(); i++)
			    {
	
			        View view = layout.getChildAt(i);
			        Rect outRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
			        if(view instanceof ImageView)
			        {
				        if(outRect.contains((int)event.getX(), (int)event.getY()))
				        {
							view.requestFocus();
				        	if(event.getAction()==MotionEvent.ACTION_UP)
				        	{
				        		pressDuration = System.currentTimeMillis() - pressStartTime;
								if (pressDuration < MAX_CLICK_DURATION && distance(pressedX, pressedY, event.getX(), event.getY()) < MAX_CLICK_DISTANCE)
								{
									buttonManager(view.getId());
								}
							}
				        	else if(event.getAction()==MotionEvent.ACTION_DOWN)
				        	{
				        		pressStartTime = System.currentTimeMillis();                
				                pressedX = event.getX();
				                pressedY = event.getY();
				        	}
				        }
			        }
				}
			}
			return true;
	}};
	
	abstract void buttonManager(int id);
	
	protected int distance(float x1, float y1, float x2, float y2) {
		// TODO Auto-generated method stub
		float dx = x1 - x2;
	    float dy = y1 - y2;
	    float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
		return (int) (distanceInPx / getResources().getDisplayMetrics().density);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

}
