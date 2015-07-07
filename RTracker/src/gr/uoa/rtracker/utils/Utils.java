package gr.uoa.rtracker.utils;

import java.util.ArrayList;
import java.util.List;
import com.deitel.routetracker.R;
import android.location.Location;

public class Utils {
	static public List<Location> getLocations(){
		List<Location> locations = new ArrayList<Location>();	
		
		Location loc1 = new Location("");
		loc1.setLatitude(37.999173);
		loc1.setLongitude(23.812614);
		locations.add(loc1);
		
		Location loc2 = new Location("");
		loc2.setLatitude(37.999430);
		loc2.setLongitude(23.811826);
		locations.add(loc2);
		
		Location loc3 = new Location("");
		loc3.setLatitude(37.999701);
		loc3.setLongitude(23.811053);
		locations.add(loc3);
		
		Location loc4 = new Location("");
		loc4.setLatitude(37.999202);
		loc4.setLongitude(23.810876);
		locations.add(loc4);
		
		Location loc5 = new Location("");
		loc5.setLatitude(37.998737);
		loc5.setLongitude(23.810688);
		locations.add(loc5);
		
		Location loc6 = new Location("");
		loc6.setLatitude(37.998323);
		loc6.setLongitude(23.810506);
		locations.add(loc6);
		
		Location loc7 = new Location("");
		loc7.setLatitude(37.998416);
		loc7.setLongitude(23.810989);
		locations.add(loc7);
		
		Location loc8 = new Location("");
		loc8.setLatitude(37.998724);
		loc8.setLongitude(23.811632);
		locations.add(loc8);
		
		Location loc9 = new Location("");
		loc9.setLatitude(37.998965);
		loc9.setLongitude(23.812163);
		locations.add(loc9);
		
		Location loc10 = new Location("");
		loc10.setLatitude(37.999173);
		loc10.setLongitude(23.812614);
		locations.add(loc10);
		
		return locations;
	}
	
	static public String getTimeStringFromMillis(long millis){
		String time = String.format("%dHR %dMIN %dSEC", 
						millis/(1000*60*60), millis%(1000*60*60)/(1000*60), 
							millis%(1000*60*60)%(1000*60)/1000);
		return time;
	}	
}