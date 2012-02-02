package edu.upenn.cis350.gpx;

/* Willy Huang
 * CIS 350 HW #2
 * Feb 2, 2012
 */

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;

public class GPXcalculatorTest extends junit.framework.TestCase {
	double lat1;
	double lat2;
	double lat3;
	double lat4;
	double lon1;
	double lon2;
	double lon3;
	double lon4;
	double lat5;
	double lon5;
	double seg1dist;
	double seg2dist;
	Date time;
	
	@Before
	public void setUp() throws Exception {
		Date time = new Date();
		lat1 = 39.95;
		lon1= -75.16;
		lat2 = 40.00;
		lon2 = -75.00;
		lat3 = 33.39;
		lon3 = -118.40;
		lat4 = 33.30;
		lon4 = -118.50;
		lat5 = 33.40;
		lon5 = -118.55;
		seg1dist = Math.sqrt(Math.pow(lat2-lat1, 2) + Math.pow(lon2-lon1, 2));
		seg2dist = Math.sqrt(Math.pow(lat4-lat3, 2) + Math.pow(lon4-lon3, 2));
		seg2dist += Math.sqrt(Math.pow(lat5-lat4, 2) + Math.pow(lon5-lon4, 2));
	}
	
	// - (check for) Incorrect distance
	public void testCalculateDistance() {
		GPXtrkpt point1 = new GPXtrkpt(lat1, lon1, time);
		GPXtrkpt point2 = new GPXtrkpt(lat2, lon2, time);
		GPXtrkpt point3 = new GPXtrkpt(lat3, lon3, time); 
		GPXtrkpt point4 = new GPXtrkpt(lat4, lon4, time);
		GPXtrkpt point5 = new GPXtrkpt(lat5, lon5, time);
		ArrayList<GPXtrkpt> seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		ArrayList<GPXtrkpt> seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		seg2.add(point4);
		seg2.add(point5);
		GPXtrkseg segment1 = new GPXtrkseg(seg1);
		GPXtrkseg segment2 = new GPXtrkseg(seg2);
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		GPXtrk track = new GPXtrk("Philly and LA", trk);
		
		
		double totaldist = seg1dist + seg2dist;
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == totaldist);
		
	}
	// - GPXtrk is null 
	public void testGPXtrkNull() {
		GPXtrk track = null;
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == -1);
	}
	// - GPXtrk contains null segments (they should contribute 0 distance)
	public void testGPXtrksegNull() {
		GPXtrkpt point1 = new GPXtrkpt(lat1, lon1, time);
		GPXtrkpt point2 = new GPXtrkpt(lat2, lon2, time);
		ArrayList<GPXtrkpt> seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		GPXtrkseg segment1 = new GPXtrkseg(seg1);
		GPXtrkseg segment2 = null;
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		GPXtrk track = new GPXtrk("Philly and null", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg1dist);
		
		GPXtrkpt point3 = new GPXtrkpt(lat3, lon3, time); 
		GPXtrkpt point4 = new GPXtrkpt(lat4, lon4, time);
		GPXtrkpt point5 = new GPXtrkpt(lat5, lon5, time);
		ArrayList<GPXtrkpt> seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		seg2.add(point4);
		seg2.add(point5);
		segment2 = new GPXtrkseg(seg2);
		segment1 = null;
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("Null and LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg2dist);
		
	}
	// - GPXtrk is empty
	public void testGPXtrkEmpty() {
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		GPXtrk track = new GPXtrk("Empty track", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == -1);
	}
	// - A GPXtrkseg is empty
	public void testGPXtrksegEmpty() {
		GPXtrkpt point1 = new GPXtrkpt(lat1, lon1, time);
		GPXtrkpt point2 = new GPXtrkpt(lat2, lon2, time);
		ArrayList<GPXtrkpt> seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		GPXtrkseg segment1 = new GPXtrkseg(seg1);
		ArrayList<GPXtrkpt> seg2 = new ArrayList<GPXtrkpt>();
		GPXtrkseg segment2 = new GPXtrkseg(seg2);
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		GPXtrk track = new GPXtrk("Philly and empty", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg1dist);
		
		GPXtrkpt point3 = new GPXtrkpt(lat3, lon3, time); 
		GPXtrkpt point4 = new GPXtrkpt(lat4, lon4, time);
		GPXtrkpt point5 = new GPXtrkpt(lat5, lon5, time);
		seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		seg2.add(point4);
		seg2.add(point5);
		segment2 = new GPXtrkseg(seg2);
		seg1 = new ArrayList<GPXtrkpt>();
		segment1 = new GPXtrkseg(seg1);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("Empty and LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg2dist);
		
		seg2 = new ArrayList<GPXtrkpt>();
		segment2 = new GPXtrkseg(seg2);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("Empty and Empty", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == 0);
	}
	
	// - A GPXtrkseg contains only one point
	public void testGPXtrksegOnePt() {
		GPXtrkpt point1 = new GPXtrkpt(lat1, lon1, time);
		GPXtrkpt point2 = new GPXtrkpt(lat2, lon2, time);
		GPXtrkpt point3 = new GPXtrkpt(lat3, lon3, time); 
		GPXtrkpt point4 = new GPXtrkpt(lat4, lon4, time);
		GPXtrkpt point5 = new GPXtrkpt(lat5, lon5, time);
		
		ArrayList<GPXtrkpt> seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		GPXtrkseg segment1 = new GPXtrkseg(seg1);
		ArrayList<GPXtrkpt> seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3); // One point
		GPXtrkseg segment2 = new GPXtrkseg(seg2);
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		GPXtrk track = new GPXtrk("Philly and 1 point in LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg1dist);

		seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		seg2.add(point4);
		seg2.add(point5);
		segment2 = new GPXtrkseg(seg2);
		seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1); // One point
		segment1 = new GPXtrkseg(seg1);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("1 point in Philly and LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg2dist);
		
		seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		segment2 = new GPXtrkseg(seg2);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("1 point in each", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == 0);
	}
	// - GPXtrkseg contains null points
	public void testGPXtrksegNullPts() {
		GPXtrkpt point1 = new GPXtrkpt(lat1, lon1, time);
		GPXtrkpt point2 = new GPXtrkpt(lat2, lon2, time);
		GPXtrkpt point3 = new GPXtrkpt(lat3, lon3, time); 
		GPXtrkpt point4 = new GPXtrkpt(lat4, lon4, time);
		GPXtrkpt point5 = new GPXtrkpt(lat5, lon5, time);
		GPXtrkpt nullPt = null;
		
		ArrayList<GPXtrkpt> seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		GPXtrkseg segment1 = new GPXtrkseg(seg1);
		ArrayList<GPXtrkpt> seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		seg2.add(point4);
		seg2.add(nullPt); // Null point
		GPXtrkseg segment2 = new GPXtrkseg(seg2);
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		GPXtrk track = new GPXtrk("Philly and 1 null point in LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg1dist);

		seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		seg2.add(point4);
		seg2.add(point5);
		segment2 = new GPXtrkseg(seg2);
		seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		seg1.add(nullPt); // NullPoint
		segment1 = new GPXtrkseg(seg1);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("Null point in Philly and LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg2dist);
		
		seg2.add(nullPt);
		segment2 = new GPXtrkseg(seg2);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("1 null point in each", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == 0);
	}
	
	// - A GPXtrkpt has latitude greater than 90 or less than -90
	public void testGPXtrkptLat() {
		GPXtrkpt point1 = new GPXtrkpt(lat1, lon1, time);
		GPXtrkpt point2 = new GPXtrkpt(lat2, lon2, time);
		GPXtrkpt point3 = new GPXtrkpt(lat3, lon3, time); 
		GPXtrkpt point4 = new GPXtrkpt(lat4, lon4, time);
		GPXtrkpt point5 = new GPXtrkpt(lat5, lon5, time);
		GPXtrkpt over90 = new GPXtrkpt(95, lon1, time);
		GPXtrkpt under90 = new GPXtrkpt(-100, lon5, time);
		
		ArrayList<GPXtrkpt> seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		GPXtrkseg segment1 = new GPXtrkseg(seg1);
		ArrayList<GPXtrkpt> seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(over90); // bad lat
		GPXtrkseg segment2 = new GPXtrkseg(seg2);
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		GPXtrk track = new GPXtrk("Philly and bad lat in LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg1dist);

		seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		seg2.add(point4);
		seg2.add(point5);
		segment2 = new GPXtrkseg(seg2);
		seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(under90); // Bad lat
		segment1 = new GPXtrkseg(seg1);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("Bad lat in Philly and LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg2dist);
		
		seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(over90);
		segment2 = new GPXtrkseg(seg2);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("Bad lat in each", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == 0);
	}
	// - A GPXtrkpt has longitude greater than 180 or less than -180
	public void testGPXtrkptLon() {
		GPXtrkpt point1 = new GPXtrkpt(lat1, lon1, time);
		GPXtrkpt point2 = new GPXtrkpt(lat2, lon2, time);
		GPXtrkpt point3 = new GPXtrkpt(lat3, lon3, time); 
		GPXtrkpt point4 = new GPXtrkpt(lat4, lon4, time);
		GPXtrkpt point5 = new GPXtrkpt(lat5, lon5, time);
		GPXtrkpt over180 = new GPXtrkpt(lat1, 196.54, time);
		GPXtrkpt under180 = new GPXtrkpt(lat4, -314.15, time);
		
		ArrayList<GPXtrkpt> seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		GPXtrkseg segment1 = new GPXtrkseg(seg1);
		ArrayList<GPXtrkpt> seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(over180); // bad lon
		GPXtrkseg segment2 = new GPXtrkseg(seg2);
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		GPXtrk track = new GPXtrk("Philly and bad lat in LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg1dist);

		seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(point3);
		seg2.add(point4);
		seg2.add(point5);
		segment2 = new GPXtrkseg(seg2);
		seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(under180); // Bad lat
		segment1 = new GPXtrkseg(seg1);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("Bad lat in Philly and LA", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == seg2dist);
		
		seg2 = new ArrayList<GPXtrkpt>();
		seg2.add(over180);
		segment2 = new GPXtrkseg(seg2);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		trk.add(segment2);
		track = new GPXtrk("Bad lat in each", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == 0);
	}
	
	// Boundary condition: two points are the same
	public void testDuplciatePts() {
		GPXtrkpt point1 = new GPXtrkpt(lat1, lon1, time);
		GPXtrkpt point2 = new GPXtrkpt(lat2, lon2, time);
		GPXtrkpt point1again = new GPXtrkpt(lat1, lon1, time);
		ArrayList<GPXtrkpt> seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point1again);
		GPXtrkseg segment1 = new GPXtrkseg(seg1);
		ArrayList<GPXtrkseg> trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		GPXtrk track = new GPXtrk("Same point back to back in one segment", trk);
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == 0);
		
		seg1 = new ArrayList<GPXtrkpt>();
		seg1.add(point1);
		seg1.add(point2);
		seg1.add(point1again);
		segment1 = new GPXtrkseg(seg1);
		trk = new ArrayList<GPXtrkseg>();
		trk.add(segment1);
		track = new GPXtrk("Point 1 to 2 to 1", trk);
		double totaldist = seg1dist + seg1dist;
		assertTrue(GPXcalculator.calculateDistanceTraveled(track) == totaldist); // should behave normally
	
	}


	@After
	public void tearDown() throws Exception {
	}

}
