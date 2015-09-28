package com.nlt.voicechat;

import java.util.ArrayList;

import com.nlt.adapter.baseAdapter;
import com.nlt.baseStruct.audioFileStruct;
import com.nlt.ui.voiceButton;
import com.nlt.ui.voiceButton.audioFinishListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;

public class MainActivity extends Activity implements audioFinishListener
{

	private ListView mList;
	private baseAdapter mAdapter;
	private ArrayList<audioFileStruct> mData;
	private voiceButton voiceBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findView();
		voiceBtn.setAudioFinishListener(this);
		mData = new ArrayList<audioFileStruct>();

		mAdapter = new baseAdapter(this, mData);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
				Log.e("test", "item");
				View im = (View) view.findViewById(R.id.voice_anim_img);
				im.setBackgroundResource(R.anim.voice_anim);
				AnimationDrawable anim = (AnimationDrawable) im.getBackground();
				
				anim.start();
			}
			
		});
	}

	private void findView()
	{
		voiceBtn = (voiceButton) findViewById(R.id.voice_recorder_btn);
		mList = (ListView) findViewById(R.id.chatListView);

	}

	@Override
	public void finish(audioFileStruct file)
	{
		mData.add(file);
		mAdapter.notifyDataSetChanged();
		setListViewPos(mData.size() - 1);
	}

	private void setListViewPos(int pos)
	{
		if (android.os.Build.VERSION.SDK_INT >= 8)
		{
			mList.smoothScrollToPosition(pos);
		}
		else
		{
			mList.setSelection(pos);
		}
	}
}
