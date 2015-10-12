package com.nlt.voicechat;

import java.io.IOException;
import java.util.ArrayList;
import com.nlt.adapter.baseAdapter;
import com.nlt.baseStruct.audioFileStruct;
import com.nlt.ui.voiceButton;
import com.nlt.ui.voiceButton.audioFinishListener;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;

public class MainActivity extends Activity implements audioFinishListener
{

	private ListView mList;
	private baseAdapter mAdapter;
	private ArrayList<audioFileStruct> mData;
	private voiceButton voiceBtn;
	private MediaPlayer mPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findView();
		mPlayer = new MediaPlayer();
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
			
				final View im = (View) view.findViewById(R.id.voice_anim_img);
				im.setBackgroundResource(R.anim.voice_anim);
				final AnimationDrawable anim = (AnimationDrawable) im.getBackground();
				
				anim.start();
				
				try
				{
					mPlayer.reset();
					mPlayer.setDataSource(mData.get(position).filePath);
					mPlayer.setOnCompletionListener(new OnCompletionListener()
					{
						
						@Override
						public void onCompletion(MediaPlayer mp)
						{
							// TODO Auto-generated method stub
							anim.stop();
							im.setBackgroundResource(R.drawable.adj);
							mPlayer.stop();
						}
					});
					mPlayer.prepare();
					mPlayer.start();
				}
				catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (SecurityException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalStateException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		int last = mList.getLastVisiblePosition();
		int first = mList.getFirstVisiblePosition();
		
		Log.e("test", "first: " + first + " last: " + last + " pos: " + pos);
//		if(pos <= n)
//			return;
		
//		if (android.os.Build.VERSION.SDK_INT >= 8)
//		{
//			mList.smoothScrollToPosition(pos);
//		}
//		else
		{
			mList.setSelection(pos);
		}
	}
}
