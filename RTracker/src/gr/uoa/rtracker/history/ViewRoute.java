package gr.uoa.rtracker.history;

import gr.uoa.rtracker.overlay.ViewMap;
import gr.uoa.rtracker.sql.DatabaseManager;
import java.util.ArrayList;

import com.deitel.routetracker.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ViewRoute extends Activity{
	private long rowID;
	private TextView timeTextView;
	private TextView distanceTextView;
	private TextView dateTextView;
	//private TextView fuelTextView;
	private TextView caloriesTextView;
	private TextView speedTextView;
	//private TextView altitudeTextView;
	//private TextView bearingTextView;
	private Button mapButton;
	//private ArrayList<ArrayList<Object>> suntetagmenes;
	
	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		Log.i("aTrace","1 starting aTask with rowID"+this);
		setContentView(R.layout.view_route);
		//Log.i("aTrace","starting Task with rowID"+this);
		timeTextView = (TextView) findViewById(R.id.timeTextView);
		distanceTextView = (TextView) findViewById(R.id.distanceTextView);
		dateTextView = (TextView)  findViewById(R.id.dateTextView);
		//fuelTextView = (TextView) findViewById(R.id.fuelTextView);
		caloriesTextView = (TextView)findViewById(R.id.caloriesTextView);
		speedTextView = (TextView) findViewById(R.id.speedTextView);
		//altitudeTextView = (TextView) findViewById(R.id.altitudeTextView);
		//bearingTextView = (TextView) findViewById(R.id.bearingTextView);
		mapButton = (Button) findViewById(R.id.viewButton);
		
		//Log.i("aTrace","2 starting create with rowID "+ RouteHistoryGui.ROW_ID);
		Bundle extras = getIntent().getExtras();
		rowID = extras.getLong(RouteHistoryGui.ROW_ID);
		Log.i("aTrace","2 starting create with rowID "+ rowID);
		if (rowID == 0) {
			Toast.makeText(getApplicationContext(), "value does not exist", Toast.LENGTH_SHORT).show();
			finish();
		}
		mapButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent inten = new Intent(ViewRoute.this,ViewMap.class);
				inten.putExtra("ROW_ID", rowID);
				startActivity(inten);
			}
		});
		/*	AsyncTask<Long,Object,Cursor> LoadMap = new   AsyncTask<Long,Object,Cursor>()
		{
			DatabaseManager manager = new DatabaseManager(ViewRoute.this);
			@Override
			protected Cursor doInBackground(Long... params){
				manager.open();
				return manager.getCoordinates(params[0]);
			}

			@Override
			protected void onPostExecute(Cursor result) {
				// TODO Auto-generated method stub
			   suntetagmenes = new ArrayList<ArrayList<Object>>();
				result.moveToFirst();
				if (!result.isAfterLast()){
					do{
						ArrayList<Object> location = new ArrayList<Object>();
						location.add(result.getLong(1));
						location.add(result.getLong(2));
						suntetagmenes.add(location);
						
					}
					while (result.moveToNext());
				}
				
			}
		};
		LoadMap.execute(rowID);
		}
		});*/	
	}
	
	@Override 
	protected void onResume(){
		super.onResume();
		new LoadRouteTask().execute(rowID);
		Log.i("Trace","3 starting Task with rowID"+this);
	}
	
	private class LoadRouteTask extends AsyncTask<Long, Object, Cursor>	{
		DatabaseManager databaseManager = new DatabaseManager(ViewRoute.this);
	
		@Override 
		protected Cursor doInBackground(Long... params){
			databaseManager.open();
			return databaseManager.getRoute(params[0]);
		}	
		protected void onPostExecute(Cursor result)	{
			super.onPostExecute(result);
			result.moveToFirst();
			
			if(result.moveToFirst() == false){
				Log.i("trace","cursor is null");
			}else{
				Log.i("trace","mpla mpla" + result.getString(1));
			}
			
			Log.i("trace","mpla mpla" + result.getString(1));
			int time = result.getColumnIndex("total_time");
			int distance = result.getColumnIndex("total_distance");
			int date = result.getColumnIndex("date");
			//int fuel = result.getColumnIndex("fuel_saved");
			int calories = result.getColumnIndex("calories");
			int speed = result.getColumnIndex("speed");
			//int altitude = result.getColumnIndex("altitude");
			//int bearing = result.getColumnIndex("bearing");
			
			
			timeTextView.setText(result.getString(time));
			distanceTextView.setText(result.getString(distance));
			dateTextView.setText(result.getString(date));
		//	fuelTextView.setText(result.getString(fuel));
			caloriesTextView.setText(result.getString(calories));
			speedTextView.setText(result.getString(speed));
			//altitudeTextView.setText(result.getString(altitude));
		//	bearingTextView.setText(result.getString(bearing));		
			result.close();
			databaseManager.close();
		}
	}
	
	 @Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.view_route_menu, menu);
		return true;
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item)	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ViewRoute.this);
		
		builder.setMessage("Are you sure?");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which){
				final DatabaseManager database = new DatabaseManager(ViewRoute.this);			
				AsyncTask<Long,Object,Object> deleteTask = new AsyncTask<Long,Object,Object>(){
					@Override 
					protected Object doInBackground(Long... params){
					database.open();
						database.deleteRoute(params[0]);
						database.close();
						return null;
					}
					
					@Override 
					protected void onPostExecute(Object result){
						finish();
					}
				};
				deleteTask.execute(rowID);
			}
		});		
		builder.setNegativeButton("No", null);
	    builder.show();
		return super.onOptionsItemSelected(item);		
	}
}
