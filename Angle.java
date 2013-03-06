import java.io.*;

public class Angle implements Serializable, Cloneable {

	boolean high;
	boolean medium;
	boolean low;
	
		
	public Angle (boolean h, boolean m, boolean l) {
		high = h; medium = m; low = l;
	}
	
	public Angle () {;}
	
	public String toString() { return getAngle(); }
	public String getAngle () {
		
		String  an = new String("");
		if (this.isHigh()) { an=an+"H"; }
		if (this.isMedium()) { an=an+"M"; }
		if (this.isLow()) { an=an+"L"; }
		return an;
	}

	public void setAngle(String strangle) {
	
		high = false;
		medium = false;
		low = false;
		
		if (strangle.indexOf('H') >= 0) { high = true; }
		if (strangle.indexOf('M') >= 0) { medium = true; }
		if (strangle.indexOf('L') >= 0) { low = true; }
		 
	}
	
	public boolean isHigh() { return high; }
	public boolean isMedium() { return medium; }
	public boolean isLow() { return low; }
	
	public Angle intersect(Angle a) {
		return new Angle (this.isHigh() && a.isHigh(),this.isMedium() && a.isMedium(), this.isLow() && a.isLow());
	}
		
	
/*
	public void setHigh(boolean h) {  high = h; }
	public void setMedium(boolean m) { medium = m; }
	public void setLow(boolean l) { low = l; }
*/	
	public boolean compare (Angle a) {	
		return (a.high && this.high) || ( a.medium && this.medium) || (a.low && this.low) ;
	}
}

