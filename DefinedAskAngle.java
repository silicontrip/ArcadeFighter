import java.util.*;


public class DefinedAskAngle implements AskAngleStrategy {

private static final Random rand = new java.util.Random(java.lang.System.currentTimeMillis());

	Angle definedAngle;

	public void setAngle (Angle a) { definedAngle = a; } 

	public  Angle askAngle (Angle a) {
        
		int possible=0;
		Angle[] aa = new Angle[3];

		if (definedAngle.compare(a)) { a = definedAngle; }
	
		if (a.isHigh()) { aa[possible] = new Angle(true,false,false); possible++; }
		if (a.isMedium()) { aa[possible] = new Angle(false,true,false); possible++ ; }
		if (a.isLow()) { aa[possible] = new Angle(false,false,true); possible++ ; }

		if (possible > 0) {
			int choice = rand.nextInt(possible);
			return aa[choice];
		}

		return new Angle(false,false,false);	
	}
}
