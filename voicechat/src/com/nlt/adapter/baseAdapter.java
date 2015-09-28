package com.nlt.adapter;

import java.util.ArrayList;

import com.nlt.baseStruct.audioFileStruct;
import com.nlt.voicechat.R;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class baseAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<audioFileStruct> mData;
	private int itemWidth = -1;
	
	public baseAdapter(Context context, ArrayList<audioFileStruct> data)
	{
		this.context = context;
		mData = data;
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
			holder.icon = (ImageView) convertView.findViewById(R.id.user_icon);
			holder.voiceAnim = (ImageView) convertView.findViewById(R.id.voice_anim_img);
			holder.voiceBtn = (Button) convertView.findViewById(R.id.voice_btn);
			holder.voiceTime = (TextView) convertView.findViewById(R.id.voice_time);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder) convertView.getTag();
		}
		
		// 根据时长设置btn宽度		        
        lp = (LayoutParams) holder.voiceBtn.getLayoutParams();
        if(itemWidth < 0)
        	itemWidth = lp.width;
		lp.width = itemWidth * mData.get(position).fileLength;
        holder.voiceBtn.setLayoutParams(lp);
        holder.voiceTime.setText("" + mData.get(position).fileLength);
		      
        holder.voiceBtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Log.e("test", "item:　" + position);
				Animation animation;
				// play
				
//				voiceAnim.setAnimation(animation);
				
			}
		});
        
        holder.voiceBtn.setOnLongClickListener(new OnLongClickListener()
		{
			
			@Override
			public boolean onLongClick(View v)
			{
				// option
				return false;
			}
		});
        
		return convertView;
	}
	
	class Holder
	{
		ImageView icon;
		Button voiceBtn;
		ImageView voiceAnim;
		TextView voiceTime;
	}
}
