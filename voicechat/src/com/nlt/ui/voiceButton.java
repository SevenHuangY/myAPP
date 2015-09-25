package com.nlt.ui;


import com.nlt.audio.audioManager;
import com.nlt.audio.audioManager.AudioStatesListener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class voiceButton extends Button implements AudioStatesListener
{
	private static final int STATUS_NORMAL = 0;
	private static final int STATUS_RECORDING = 1;
	private static final int STATUS_WANT_TO_CANCEL = 2;
	private int currentStatus = 0;
	
	private Context context;
	private dialogManager dm;
	
	private int width, height;
	private boolean isRecording = false;
	private final String TAG = "test";
	private float recordTime = 0f;
	
	private audioManager mAudioManager;
	
	private static final int RECORDING = 0x1010;
	private static final int UPDATE_VOLUME = 0x1011;
	private static final int DISMISS_DIALOG = 0x1012;
	
	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch(msg.what)
			{
				case RECORDING:
					dm.show();
					// 开始录音,并启动线程获取声音大小和计时
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							while(isRecording)
							{
								try
								{								
									recordTime += 0.1f;
									mHandler.sendEmptyMessage(UPDATE_VOLUME);
									Thread.sleep(100);
								}
								catch (InterruptedException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						}
					}).start();
					updateButtonStatus();
					break;
				case UPDATE_VOLUME:
					if(isRecording && (currentStatus != STATUS_WANT_TO_CANCEL))
					{
						int i = mAudioManager.getVolumeLevel(MaxVolumeLv);
//					Log.e(TAG, "lv: " + i);
						dm.updateVoiceLevel(i);
					}
					break;
				case DISMISS_DIALOG:				
					dm.dismiss();			
					break;
			}		
		}		
	};
	
	private int MaxVolumeLv = 7;
	
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
		mAudioManager.setOnAudioStatesListener(this);
		
		setOnLongClickListener(new OnLongClickListener()
		{		
			@Override
			public boolean onLongClick(View v)
			{
				mAudioManager.record();			
				return true;
			}
		});
	
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		
//		Log.e(TAG, "w: " + width + " h: " + height);
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
				break;			
			case MotionEvent.ACTION_MOVE:
				judgeStatus(x, y);			
				updateButtonStatus();
				break;
			case MotionEvent.ACTION_UP:	
				if(!isRecording)
				{	
					mAudioManager.cancel();
					dm.updateStatus(3);
					dm.show();
					mHandler.sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);
				}
				else
				{
					// 判断是取消录音还是保存录音还是录音时间过短
					if(currentStatus == STATUS_WANT_TO_CANCEL)
					{
						mAudioManager.cancel();
						mHandler.sendEmptyMessage(DISMISS_DIALOG);
					}
					else
					{
						if(recordTime < 1.0f)
						{
							dm.updateStatus(3);
							mHandler.sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);
							mAudioManager.cancel();
						}
						else
						{
							mHandler.sendEmptyMessage(DISMISS_DIALOG);
							mAudioManager.stop();
						}
						
					}					
				}			
				reset();
				currentStatus = STATUS_NORMAL;
				updateButtonStatus();	
				break;
		}
		return super.onTouchEvent(event);
	}

	
	private void reset()
	{
		// TODO Auto-generated method stub
		isRecording = false;
		recordTime = 0f;
	}

	private void judgeStatus(int x, int y)
	{
		// TODO Auto-generated method stub
		 
		if(y > 0 && y < height)
		{
			if(currentStatus != STATUS_RECORDING)
			{
				currentStatus = STATUS_RECORDING;
				dm.updateStatus(currentStatus);
			}
		}
		else
		{
			if(currentStatus != STATUS_WANT_TO_CANCEL)
			{
				currentStatus = STATUS_WANT_TO_CANCEL;
				dm.updateStatus(currentStatus);
			}
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

	@Override
	public void wellPrepared()
	{
		// TODO Auto-generated method stub
		isRecording = true;
		mHandler.sendEmptyMessage(RECORDING);
		
	}

	
}
