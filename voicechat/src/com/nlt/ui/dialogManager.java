package com.nlt.ui;

import com.nlt.voicechat.R;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class dialogManager
{
	private Context context;
	private Dialog noticeDialog;
	private ImageView leftImg, rightImg;
	private TextView textInfo;
	private int resid = 0;
	
	public dialogManager(Context context)
	{
		this.context = context;
		init();
	}
	
	private void init()
	{
		noticeDialog = new Dialog(context, R.style.dialog);
		LayoutInflater layout = LayoutInflater.from(context);
		View view = layout.inflate(R.layout.dialog_layout, null);
		noticeDialog.setContentView(view);
		
		leftImg = (ImageView) view.findViewById(R.id.dialog_left_img);
		rightImg = (ImageView) view.findViewById(R.id.dialog_right_img);
		textInfo = (TextView) view.findViewById(R.id.dialog_text);
		
	}
	
	public void show()
	{
		noticeDialog.show();
	}
	
	public void dismiss()
	{
		noticeDialog.dismiss();
		
	}
	
	public void updateVoiceLevel(int lv)
	{
		if(noticeDialog != null && noticeDialog.isShowing())
		{
			
			leftImg.setVisibility(View.VISIBLE);
			rightImg.setVisibility(View.VISIBLE);
			
			resid = context.getResources().getIdentifier("v" + lv, "drawable", context.getPackageName());
			rightImg.setImageResource(resid);		
			
		}
	}
	
	public void updateStatus(int status)
	{
		switch(status) 
		{
			case 1: // recording
				leftImg.setVisibility(View.VISIBLE);
				leftImg.setImageResource(R.drawable.recorder);
				if(resid > 0)
					rightImg.setImageResource(resid);
				textInfo.setText("手指上滑，取消发送");
				break;
			case 2: //cancel
				leftImg.setVisibility(View.GONE);
				rightImg.setImageResource(R.drawable.cancel);
				textInfo.setText("松开手指，取消发送");
				break;
			case 3: //to short
				leftImg.setVisibility(View.GONE);
				rightImg.setImageResource(R.drawable.voice_to_short);
				textInfo.setText("录制时间太短！");
				break;
		}
	}
}
