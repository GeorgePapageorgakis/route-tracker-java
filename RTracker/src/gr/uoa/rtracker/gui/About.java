package gr.uoa.rtracker.gui;

/**The About activity should be defined in About.java. All it needs to do is
override onCreate( ) and call setContentView( )*/

import com.deitel.routetracker.R;
import android.app.Activity;
import android.os.Bundle;

public class About extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutgui);
	}
}

