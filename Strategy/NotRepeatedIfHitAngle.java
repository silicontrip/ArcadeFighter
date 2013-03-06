
public class NotRepeatedIfHitAngle implements AskAngleStrategy {

	public  Angle askAngle (Player a, Player d, Deck p) {
	
		Angle ang;

		Card last  = p.getTopAttack();

		// choose angle
		ang = new Angle(true,true,true);
		if (last != null) {
			if (last.getDamage() > 0) {
				if (last.getAngle().isHigh()) { ang = new Angle(false,true,true); }
				if (last.getAngle().isMedium()) { ang = new Angle(true,false,true); }
				if (last.getAngle().isLow()) { ang = new Angle(true,true,false); }
			}
		}
		return ang;
	
	}
}

