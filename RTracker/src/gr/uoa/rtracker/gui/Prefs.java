package gr.uoa.rtracker.gui;

import com.deitel.routetracker.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Prefs extends PreferenceActivity {
	/**The addPreferencesFromResource( ) method reads the settings definition 
	 * from XML and inflates it into views in the current activity. All the heavy 
	 * lifting takes place in the PreferenceActivity class.*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}
