package gr.uoa.rtracker.sql;

import java.util.List;

import gr.uoa.rtracker.tracker.Route;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.location.Location;

public class DatabaseManager {
    private CustomSQLiteOpenHelper helper;
    private SQLiteDatabase database; 
	
	public DatabaseManager(Context context)	{
		helper = new CustomSQLiteOpenHelper(context,"Bicycling",null,1);
	}
	
	public void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	public void close()	{
		if (database != null){
			database.close();
		}
	}
	
	public long insertRoute(String date, double time ,double distance,  double avspeed, double calories){
		long last_id;
		ContentValues newRoute = new ContentValues();
		newRoute.put("date", date);
		newRoute.put("total_time", time);
		newRoute.put("total_distance", distance);
		newRoute.put("speed", avspeed);
		newRoute.put("calories", calories);		
		
		open();
		last_id = database.insert("route",null,newRoute);
		close();
		return last_id;
	}

	public void deleteRoute(long id){
		open();
		database.delete("route","_id=" + id,null);
		close();	
	}
	
	public void clearAll(){
		open();
		database.delete("route", null, null);
		close();
	}
	
	public Cursor getAllRoutes(){
		return database.query("route", new String[] {"_id","date"}, null, null, null, null, "date");
	}
	
	public Cursor getRoute(long id)	{
		return database.query("route", null, "_id=" + id, null, null, null, null );
		
	}
	
	public Cursor getCoordinates(long id){
		return database.query("coordinates",null, "id_route=" + id, null, null, null, null);
	}
	
	public Cursor getAchievement(){
		return database.query("route",new String[] {"sum(total_time)","sum(total_distance)","max(speed)","sum(calories)"} , null, null, null, null, null);
	}

	public Cursor getTotal(String megethos){
		return database.query("route", new String[] {"sum(megethos)"}, null, null, null, null, null);
	}
	
	public void  insertCoordinates(List<Location> locations, long id_route){
		open();		
		for (int i = 0; i < locations.size(); i++) {
			Location loc = locations.get(i);
			ContentValues xy = new ContentValues();
			xy.put("latitude",loc.getLatitude());
			xy.put("longitude", loc.getLongitude());
			xy.put("id_route", id_route);
			database.insert("coordinates", null, xy);
		}		
		close();	
	}
	//public ...getCoordinates()
	/*public void  insertCoordinates(Route route, long id_route)
	{
		ContentValues xy = new ContentValues();
		xy.put("latitude",latitude);
		xy.put("longitude", longitude);
		xy.put("id_route", id_route);
		open();
		database.insert("coordinates", null, xy);
		close();
	}*/
	
	private class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
		public CustomSQLiteOpenHelper(Context context,String name, CursorFactory factory, int version){
			super(context,name, factory, version);	
		}
		@Override 
		public void onOpen(SQLiteDatabase db){
			super.onOpen(db);
			if (!db.isReadOnly()){
				db.execSQL("PRAGMA foreign_keys=ON");
			}
		}
		@Override 
		public void onCreate(SQLiteDatabase db){
			String queryString = "Create table route("+
						"_id integer primary key autoincrement,"+
						"date text not null , total_time real,"+
						"total_distance real,"+
						" speed real,"+
						" calories real);";
			db.execSQL(queryString);
				
			String query = "Create table coordinates("+
						"idxy integer primary key autoincrement,"+
						"latitude real not null,"+
						"longitude real not null,"+
						"id_route integer not null,"+
						"foreign key(id_route) references route(_id) on delete cascade on update cascade);";
			db.execSQL(query);	
		}
		@Override 
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			
		}
	}
}