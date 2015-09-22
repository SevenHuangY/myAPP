package com.nlt.ui;


import com.nlt.voicechat.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class voiceButton extends Button
{
	private static final int STATUS_NORMAL = 0;
	private static final int STATUS_RECORDING = 1;
	private static final int STATUS_END = 2;
	private static final int STATUS_CANCEL = 3;
	private int currentStatus = 0;
	
	private Context context;
	private Dialog noticeDialog;
	
	private int width, height;
	
	private final String TAG = "test";
	
	public voiceButton(Context context)
	{
		this(context, null);
		
	}
	
	public voiceButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
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
				dialogShow();
				changeButtonStatus();
				break;			
			case MotionEvent.ACTION_MOVE:
				judgeStatus(x, y);
				changeButtonStatus();
				break;
			case MotionEvent.ACTION_UP:
				
				currentStatus = STATUS_NORMAL;
				changeButtonStatus();
				break;
		}
		return super.onTouchEvent(event);
	}

	

	private void dialogShow()
	{
		// TODO Auto-generated method stub
	
		LayoutInflater layout = LayoutInflater.from(context);
		View view = layout.inflate(R.layout.dialog_layout, null);
		noticeDialog = new AlertDialog.Builder(context).setView(view).create();
//		noticeDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		noticeDialog.show();
	}

	private void judgeStatus(int x, int y)
	{
		// TODO Auto-generated method stub
		 
		if(y > 0 && y < height && x > 0 && x < width)
		{
			currentStatus = STATUS_END;
		}
		else
		{
			currentStatus = STATUS_CANCEL;
		}
	}

	private void changeButtonStatus()
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
			case STATUS_CANCEL:
				setText("松开手指，取消发送");
				break;
			case STATUS_END:
				setText("松开 结束");
				break;
		}
	}

	
}
