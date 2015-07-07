package gr.uoa.rtracker.ach;

import gr.uoa.rtracker.sql.DatabaseManager;
import com.deitel.routetracker.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class ViewAch extends Activity
{	
	private TextView distanceTextView;
	private TextView timeTextView;
	private TextView speedTextView;
	private TextView caloriesTextView;
    private Button mapButton;
		
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.achievement);
	distanceTextView = (TextView) findViewById(R.id.totalkmTextView);
	timeTextView = (TextView) findViewById(R.id.totaltimeTextView);
	speedTextView = (TextView)  findViewById(R.id.maxspeedTextView);
	caloriesTextView = (TextView)findViewById(R.id.caloriesTextView);
	
	mapButton = (Button) findViewById(R.id.viewButton);
    mapButton.setOnClickListener(new OnClickListener()
    {
      	public void onClick(View v)
    	{
    		//TODO
    	}
    });
    
	}
	
	@Override 
	protected void onResume()
	{
		super.onResume();
		new AchievementTask().execute();
	}
	
	
	private class AchievementTask extends AsyncTask<Object, Object, Cursor>
	{
		DatabaseManager databaseManager = new DatabaseManager(ViewAch.this);
		
		@Override 
		protected Cursor doInBackground(Object ...params)
		{
			databaseManager.open();
			return databaseManager.getAchievement();
		}

		protected void onPostExecute(Cursor result)
		{
			super.onPostExecute(result);
			result.moveToFirst();
			
			if(result.moveToFirst() == false){
				Log.i("trace","cursor is null");
			}
			Log.i("trace","random test message" + result.getString(0)+ "what is this? "+result.getString(1));
	
			int time = result.getColumnIndex("sum(total_time)");
			int distance = result.getColumnIndex("sum(total_distance)");
			int calories = result.getColumnIndex("sum(calories)");
			int speed = result.getColumnIndex("max(speed)");
			Log.i("trace","The value time is " +time);
						
			timeTextView.setText(result.getString(time));
			distanceTextView.setText(result.getString(distance));
			caloriesTextView.setText(result.getString(calories));
			speedTextView.setText(result.getString(speed)); 
		
			result.close();
			databaseManager.close();
		}
	}
}
	