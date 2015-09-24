package com.nlt.ui;


import com.nlt.audio.audioManager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class voiceButton extends Button
{
	private static final int STATUS_NORMAL = 0;
	private static final int STATUS_RECORDING = 1;
	private static final int STATUS_WANT_TO_CANCEL = 2;
	private int currentStatus = 0;
	
	private Context context;
	private dialogManager dm;
	
	private int width, height;
	
	private final String TAG = "test";
	
	private audioManager mAudioManager;
	
	public voiceButton(Context context)
	{
		this(context, null);
		
	}
	
	public voiceButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		dm = new dialogManager(context);
		mAudioManager = new audioManager(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		
		Log.e(TAG, "w: " + width + " h: " + height);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch(action)
		{
			case MotionEvent.ACTION_DOWN:
				currentStatus = STATUS_RECORDING;
				dm.show();
				// 开始录音,并启动线程获取声音大小和计时
				updateButtonStatus();
				break;			
			case MotionEvent.ACTION_MOVE:
				judgeStatus(x, y);
				dm.updateStatus(currentStatus);
				updateButtonStatus();
				break;
			case MotionEvent.ACTION_UP:
				dm.dismiss();
				// 判断是取消录音还是保存录音还是录音时间过短
				if(currentStatus == STATUS_WANT_TO_CANCEL)
				{
					
				}
				else
				{
//					dm.updateStatus(3);
				}
				currentStatus = STATUS_NORMAL;
				updateButtonStatus();
				break;
		}
		return super.onTouchEvent(event);
	}

	
	private void judgeStatus(int x, int y)
	{
		// TODO Auto-generated method stub
		 
		if(y > 0 && y < height && x > 0 && x < width)
		{
			currentStatus = STATUS_RECORDING;
		}
		else
		{
			currentStatus = STATUS_WANT_TO_CANCEL;
		}
	}

	private void updateButtonStatus()
	{
		// TODO Auto-generated method stub
		switch(currentStatus)
		{
			case STATUS_NORMAL:
				setText("按住 说话");
				break;
			case STATUS_RECORDING:
				setText("松开 结束");
				break;
			case STATUS_WANT_TO_CANCEL:
				setText("松开手指，取消发送");
				break;
		}
	}

	
}
