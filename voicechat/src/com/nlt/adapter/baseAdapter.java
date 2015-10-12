package com.nlt.adapter;

import java.util.ArrayList;

import com.nlt.baseStruct.audioFileStruct;
import com.nlt.voicechat.R;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class baseAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<audioFileStruct> mData;
	private int minWidth;
	private int maxWidth;
	
	public baseAdapter(Context context, ArrayList<audioFileStruct> data)
	{
		this.context = context;
		mData = data;
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		minWidth = (int) (outMetrics.widthPixels * 0.1f);
		maxWidth = (int) (outMetrics.widthPixels * 0.7f);
		
	}
	
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		Holder holder = null;
		LayoutParams lp;
		if(convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item, null);
			holder = new Holder();
			holder.icon = (View) convertView.findViewById(R.id.user_icon);
			holder.voiceAnim = (View) convertView.findViewById(R.id.voice_anim_img);
			holder.voiceBtn = (View) convertView.findViewById(R.id.voice_btn);
			holder.voiceTime = (TextView) convertView.findViewById(R.id.voice_time);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder) convertView.getTag();
		}
		
		// 根据时长设置btn宽度		        
        lp = (LayoutParams) holder.voiceBtn.getLayoutParams();           
		lp.width = minWidth + ( maxWidth / 60 * mData.get(position).fileLength);
        holder.voiceBtn.setLayoutParams(lp);
        holder.voiceTime.setText("" + mData.get(position).fileLength + "\"");
		           
		return convertView;
	}
	
	class Holder
	{
		View icon;
		View voiceBtn;
		View voiceAnim;
		TextView voiceTime;
	}
}
