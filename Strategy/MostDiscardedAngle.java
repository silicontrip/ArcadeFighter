
public class MostDiscardedAngle implements AskAngleStrategy {

	public  Angle askAngle (Player a, Player d, Deck p) {
	
		boolean high = false;
		boolean medium = false;
		boolean low = false;
		
		int highCount = 0;
		int mediumCount = 0;
		int lowCount = 0;
		
		Angle ang=null;
	
		for (int i=0; i < d.getDefenseDiscard().size(); i++) {
			ang = d.getDefenseDiscard().getCard(i).getAngle();
			if (ang.isHigh()) { highCount++; }
			if (ang.isMedium()) { mediumCount++; }
			if (ang.isLow()) { lowCount++; }
		}
		
		// choose angle
		// ang = new Angle(false,true,false);
		if ((highCount >= mediumCount) && (highCount >= lowCount)) { high  = true; }
		if ((lowCount >= mediumCount) && (lowCount >= highCount)) { low = true; }
		if ((mediumCount >= highCount) && (mediumCount >= lowCount)) { medium = true; }
	
	
		return new Angle (high,medium,low);

	
	}
}

