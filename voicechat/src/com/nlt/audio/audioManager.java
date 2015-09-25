package com.nlt.audio;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.media.MediaRecorder;
import android.media.MediaRouter.VolumeCallback;
import android.os.Environment;
import android.util.Log;
import android.content.Context;

public class audioManager
{
	private Context context;
	private MediaRecorder mRecorder;
	private String mDir = "/voiceChat/";
	private File file;
	private final String TAG = "test";
	private boolean isRecording = false;
	
	public audioManager(Context context)
	{
		this.context = context;		
	}
	
	public interface AudioStatesListener
	{
		void wellPrepared();
	}
	
	private AudioStatesListener mListener;
	public void setOnAudioStatesListener(AudioStatesListener listener)
	{
		mListener = listener;
	}
	
	private void init()
	{
		File path = Environment.getExternalStorageDirectory(); 
		file = new File(path.getPath() + mDir + getFileName());
		if (!file.exists())
		{
			File dir = new File(file.getParent());
			dir.mkdirs();
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	}
		
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setOutputFile(file.toString());
	}
	
	public void record()
	{
		try 
		{
			init();
			mRecorder.prepare();
			mRecorder.start();
			isRecording = true;
			mListener.wellPrepared();
		}
        catch (IllegalStateException e) 
        {
			e.printStackTrace();
		}
        catch (IOException e) 
        {
			e.printStackTrace();
		}
	}

	public void stop()
	{
		if(isRecording)
		{
			mRecorder.stop();
			mRecorder.release();
			isRecording = false;
		}
		
	}
	
	public void cancel()
	{
		stop();
		if(file != null)
			file.delete();
	}

	public int getVolumeLevel(int maxLv)
	{
		int lv;
		try
		{
			lv = maxLv * mRecorder.getMaxAmplitude() / 32768 + 1;
		}
		catch(IllegalStateException e)
		{
			lv = 1;
		}
		return lv; 
	}
	
	private String getFileName()
	{
		return UUID.randomUUID().toString() + ".amr";
	}

}
