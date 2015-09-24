package com.nlt.audio;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.content.Context;

public class audioManager
{
	private Context context;
	private MediaRecorder mRecorder;
	private File file;
	private final String TAG = "test";
	
	public audioManager(Context context)
	{
		String name = "1234.raw";
		
			
		File path = Environment.getExternalStorageDirectory(); 
		Log.e(TAG, "path: " + path);
		file = new File(path.getPath() + "/voiceChat/1234.raw");
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
		
		Log.e(TAG, "init");
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
			mRecorder.prepare();
			mRecorder.start();
			Log.e(TAG, "start");
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
		Log.e(TAG, "stop");
		mRecorder.stop();
		mRecorder.release();
	}
}
