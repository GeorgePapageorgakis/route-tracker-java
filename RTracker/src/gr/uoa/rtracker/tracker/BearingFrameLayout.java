// BearingFrameLayout.java
// Rotates MapView according to device's bearing.
package gr.uoa.rtracker.tracker;

import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.Display;
import android.widget.FrameLayout;

public class BearingFrameLayout extends FrameLayout{
	private int scale = 0; 		// amount to scale layout
	private MapView mapView; 	// displays Google maps
	private float bearing = 0f; // compass bearing
   
	// returns layout parameters for MapView
	public LayoutParams getChildLayoutParams() 	{
		Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
		int w = display.getWidth();
		int h = display.getHeight();
		scale = (int) Math.sqrt((w * w) + (h * h));

		return new LayoutParams(scale, scale);
   } // end method getChildLayoutParams

   // public constructor for BearingFrameLayout
	public BearingFrameLayout(Context context, String apiKey){
		super(context); 						// call super constructor   
		mapView = new MapView(context, apiKey); // create new MapView      
		mapView.setClickable(true); 			// allow user interactions with the map
		mapView.setEnabled(true); 				// enables the MapView to generate events
		mapView.setSatellite(false); 			// display map image
		mapView.setBuiltInZoomControls(true); 	// enable zoom controls
		// set MapView's layout
		mapView.setLayoutParams(getChildLayoutParams()); 
		addView(mapView); 						// add MapView to this layout
    } // end BearingFrameLayout constructor

	// rotates the map according to bearing
	@Override
	protected void dispatchDraw(Canvas canvas) {
		if (bearing >= 0){ // if the bearing is greater than 0
			// get canvas dimensions
			int canvasWidth = canvas.getWidth();
			int canvasHeight = canvas.getHeight();

			// dimensions of the scaled canvas
			int width = scale;
			int height = scale;

			// center of scaled canvas
			int centerXScaled = width / 2;
			int centerYScaled = height / 2;

			// center of screen canvas
			int centerX = canvasWidth / 2;
			int centerY = canvasHeight / 2;

			// move center of scaled area to center of actual screen
			canvas.translate(-(centerXScaled - centerX), 
			-(centerYScaled - centerY));

			// rotate around center of screen
			canvas.rotate(-bearing, centerXScaled, centerYScaled);
		} // end if
		super.dispatchDraw(canvas); // draw child Views of this layout
	} // end method dispatchDraw

	// set the compass bearing
	public void setBearing(float bearing){
		this.bearing = bearing;
	} // end method setCompassBearing

	// return the MapView
	public MapView getMapview(){
		return mapView;
	} // end method getMapView
} // end class BearingFrameLayout