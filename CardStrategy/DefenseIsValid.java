public class DefenseIsValid implements IsValidStrategy {
	public boolean isValid (Card c, Player a, Player d, Deck p) {
	
		if (d.getEnergy() < c.getCost()) { return false; }
		if (!c.getAngle().compare(p.getTopAttack().getAngle())) { return false; }
		if (!p.getTopAttack().canUse(a,d,p,c)) { return false; }
		
		return true;
	}

}
