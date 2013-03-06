public class PreviousAngleIsValid implements IsValidStrategy {
	public boolean isValid (Card c, Player a, Player d, Deck p) {
		// play this card if the previous attack was at the same angle as this attack

		if (!p.getTopAttack().getAngle().compare(p.getTopAttack(2).getAngle())) {return false;}
		if (d.getEnergy() < c.getCost()) { return false; }
		if (!c.getAngle().compare(p.getTopAttack().getAngle())) { return false; }
		if (!p.getTopAttack().canUse(a,d,p,c)) { return false; }
		
		return true;

	}
}
