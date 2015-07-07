package gr.uoa.rtracker.overlay;

import java.util.ArrayList;
import java.util.List;

import gr.uoa.rtracker.history.RouteHistoryGui;
import gr.uoa.rtracker.overlay.RouteOverlay;
import gr.uoa.rtracker.sql.DatabaseManager;
import gr.uoa.rtracker.tracker.BearingFrameLayout;
import gr.uoa.rtracker.utils.Utils;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.deitel.routetracker.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ViewMap extends MapActivity {
	private MapView mapView; 				// displays a Google map
	private MapController mapController; 	// manages map pan/zoom
	private RouteOverlay routeOverlay; 		// Overlay that shows route on map
	private BearingFrameLayout bearingFrameLayout; // rotates the MapView
	private static final int MAP_ZOOM = 18; // Google Maps supports 1-21

	// Called when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_map);

		// create new MapView using your Google Maps API key
		bearingFrameLayout = new BearingFrameLayout(this, 
								getResources().getString(R.string.google_maps_api_key));

		// add bearingFrameLayout to mainLayout
		FrameLayout mainLayout = (FrameLayout) findViewById(R.id.viewMapLayout);
		mainLayout.addView(bearingFrameLayout, 0);

		// get the MapView and MapController
		mapView = bearingFrameLayout.getMapview();
		mapController = mapView.getController(); // get MapController
		mapController.setZoom(MAP_ZOOM); 		// zoom in the map

		// create map Overlay
		routeOverlay = new RouteOverlay(); 

		// add the RouteOverlay overlay
		mapView.getOverlays().add(routeOverlay);

	} // end onCreate

	// called when Activity becoming visible to the user
	@Override
	public void onStart() {
		super.onStart(); // call super's onStart method

		bearingFrameLayout.invalidate(); // redraw the BearingFrameLayout
		Bundle extras = getIntent().getExtras();
		long routeID = extras.getLong("ROW_ID");
		Log.i("Trace", "routeID = " + routeID);
		//long id = Long.getLong(RouteHistoryGui.ROW_ID);
		Load load = new Load();
		load.execute(routeID); //TODO

		/*List<Location> locs = Calcs.getLocations();
		for (int i = 0; i < locs.size(); i++) {
		   updateLocation(locs.get(i));	   
		}*/	   
	} // end method onStart

	// called when Activity is no longer visible to the user
	@Override
	public void onStop(){
		super.onStop(); // call the super method
	} // end method onStop

	// update location on map   
	public void updateLocation(Location location){
	   if (location != null) { // location not null
			// add the given Location to the route
			routeOverlay.addPoint(location); 

			// get the latitude and longitude
			Double latitude = location.getLatitude() * 1E6;
			Double longitude = location.getLongitude() * 1E6;

			// create GeoPoint representing the given Locations
			GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());

			// move the map to the current location
			mapController.animateTo(point);
			// update the compass bearing
			//bearingFrameLayout.setBearing(location.getBearing());
			bearingFrameLayout.invalidate(); // redraw based on bearing
	   } // end if   
	} // end method updateLocation

	// Google terms of use require this method to return
	// true if you're displaying route information like driving directions
	@Override
	protected boolean isRouteDisplayed() {
		return false; // we aren't displaying route information
	} // end method isRouteDisplayed

	// create the Activity's menu from a menu resource XML file
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.route_tracker_menu, menu);
		return true;
	} // end method onCreateOptionsMenu

	// handle choice from options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		// perform appropriate task based on 
		switch (item.getItemId()) 
		{
			case R.id.mapItem: 					// the user selected "Map"
				mapView.setSatellite(false); 	// display map image
				return true;
			case R.id.satelliteItem: 			// the user selected "Satellite"
				mapView.setSatellite(true); 	// display satellite image
				return true;
			default:
				return super.onOptionsItemSelected(item);
		} // end switch
	} // end method onOptionsItemSelected
		 
	class Load extends AsyncTask<Long,Object,Cursor> {
		DatabaseManager manager = new DatabaseManager(ViewMap.this);
		@Override
		protected Cursor doInBackground(Long... params){
			manager.open();
			return manager.getCoordinates(params[0]);
		}
		@Override
		protected void onPostExecute(Cursor result) {
			List<Location> locations = new ArrayList<Location>();
			result.moveToFirst();
			if (!result.isAfterLast()){
			do {
				Location location = new Location("");
				location.setLatitude(result.getDouble(1));
				location.setLongitude(result.getDouble(2));
				locations.add(location);
				updateLocation(location);
			}while (result.moveToNext());
			}
		}
	};
}