package gr.uoa.rtracker.gui;

//import com.deitel.routetracker.R;
import gr.uoa.rtracker.history.RouteHistoryGui;
import gr.uoa.rtracker.overlay.ViewMap;
import gr.uoa.rtracker.tracker.RouteTrackerGui;

import com.deitel.routetracker.R;

import android.app.Activity;
import android.os.Bundle;

/** wire all this up to the About button in the bikegps class.*/
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/** need a few more imports to bring up the menu we defined */
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/** more inputs for the New route menu*/
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class AppGui extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maingui);
        
        // Set up click listeners for all the buttons
        View newButton = findViewById(R.id.new_route_button);
        newButton.setOnClickListener(this);
        View historyButton = findViewById(R.id.history_button);
        historyButton.setOnClickListener(this);
        View scheduleButton = findViewById(R.id.schedule_button);
        scheduleButton.setOnClickListener(this);
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
         
    }   
    
    public void onClick(View v) {
    	switch (v.getId()) {
	    	// More buttons go here (if any) ...
    	case R.id.new_route_button:
    		Intent i1 = new Intent(this,RouteTrackerGui.class);
        	startActivity(i1);
			break;
    	case R.id.history_button:
    		Intent i5 = new Intent(this,RouteHistoryGui.class);
        	startActivity(i5);
    		break;
    	case R.id.schedule_button:
    		Intent i6 = new Intent(this,ViewMap.class);
    		startActivity(i6);
    		break;
    	case R.id.about_button:
	    	Intent i = new Intent(this, About.class);
	    	startActivity(i);
	    	break;
    	case R.id.exit_button:
    		finish();
    		break;
	    }
    }
    
    /**getMenuInflater( ) returns an instance of MenuInflater that we use to read 
     * the menu definition from XML and turns it into a real view. When
     * the user selects any menu item, onOptionsItemSelected( ) will be called.*/

    /** override the .onCreateOptionsMenu() for options menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
    }
    
    /** When the user selects any menu item, onOptionsItemSelected( ) will be called */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.settings:
	    	startActivity(new Intent(this, Prefs.class));
	    	return true;
	    // More items go here (if any) ...
	    }
	    return false;
    }
  
    /**openNewGameDialog() takes care of creating the user interface for the difficulty list*/
    private static final String TAG = "4Bike" ;
   
    private void openNewRouteDialog() {
    	new AlertDialog.Builder(this).setTitle(R.string.new_route_title).setItems(R.array.mode,new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialoginterface,int i) {
    				startRoute(i);
    		}
    	})
    	.show();
    }
	
    private void startRoute(int i) {
    	Log.d(TAG, "clicked on " + i);	
    	// Start game here...

   	}
}