package gr.uoa.rtracker.history;

import gr.uoa.rtracker.sql.DatabaseManager;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.deitel.routetracker.R;

public class RouteHistoryGui extends ListActivity{
   public static final String ROW_ID ="_id"; 
   private ListView contactListView; 
   private CursorAdapter contactAdapter; 
   
   @Override
   public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		contactListView = getListView(); 
			contactListView.setOnItemClickListener(new OnItemClickListener(){
			@Override 
			public void onItemClick(AdapterView<?> arg0,View arg1,int arg2, long arg3){
				try {
					Intent route = new Intent(RouteHistoryGui.this, ViewRoute.class);
					route.putExtra(ROW_ID, arg3);
					startActivity(route);	   
				}
				catch (Exception e) {
					e.getCause();
					finish();
				}
			}
			});    
		String[] from = new String[] { "date" };
		int[] to = new int[] { R.id.routeTextView } ; 
		contactAdapter = new SimpleCursorAdapter(
		RouteHistoryGui.this, R.layout.route_text_view, null, from, to);
		setListAdapter(contactAdapter); 
    } 

	@Override
	protected void onResume(){
      super.onResume(); 
       new GetRouteTask().execute((Object[]) null);
    } 

    @Override
    protected void onStop(){
		Cursor cursor = contactAdapter.getCursor(); 
		if (cursor != null) 
			cursor.deactivate(); 
		contactAdapter.changeCursor(null); 
		super.onStop();
    } 

	private class GetRouteTask extends AsyncTask<Object, Object, Cursor> {
		DatabaseManager databaseConnector = 
		new DatabaseManager(RouteHistoryGui.this);
		@Override
		protected Cursor doInBackground(Object... params){
			databaseConnector.open();
			return databaseConnector.getAllRoutes(); 
		} 
	
		@Override
		protected void onPostExecute(Cursor result)	{
			contactAdapter.changeCursor(result); 
			databaseConnector.close();
		} 
	}  
}
