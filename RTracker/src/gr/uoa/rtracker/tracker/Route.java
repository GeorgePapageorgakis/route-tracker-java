package gr.uoa.rtracker.tracker;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

public class Route {	
	private long startTime;
	private long totalTime;
	private long distanceTraveledinM;
	private List<Location> locations;
	private static final double MILLISECONDS_PER_HOUR = 1000 * 60 * 60;
	
	public Route(){
		locations = new ArrayList<Location>();
		startTime = 0;
		totalTime = 0;
		distanceTraveledinM = 0;	
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getDistanceTraveledinM() {
		return distanceTraveledinM;
	}

	public void setDistanceTraveledinM(long distanceTraveled) {
		this.distanceTraveledinM = distanceTraveled;
	}
	
	public void addLocation(Location location){
		locations.add(location);
	}
	
	public void removeLocation(int num){
		locations.remove(num);
	}
	
	public Location getLocation(int num){
		return locations.get(num);
	}
	
	public int getLocationsSize(){
		return locations.size();
	}
	
	public void clearLocations(){
		locations.clear();
	}
	
	public boolean isEmpty(){
		return locations.isEmpty();
	}

	public long getTotalTimeinMsec() {
		return totalTime;
	}
	
	public void setTotalTimeinMsec(long totalTime){
		this.totalTime = totalTime;
	}
	
	public double getTotalTimeinHours(){
		return (getTotalTimeinMsec()/MILLISECONDS_PER_HOUR);
	}

	public double getDistanceTraveledinKM() {
		return distanceTraveledinM/1000;
	}
	
	public double getAverageSpeedKPH(){
		return (getDistanceTraveledinKM()/getTotalTimeinHours());
	}
}
