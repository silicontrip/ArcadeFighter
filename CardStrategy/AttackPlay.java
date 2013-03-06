public class AttackPlay implements PlayStrategy {
	public void play (Card c, Player a, Player d, Deck p) {
	
		Angle an; 
		c.playCount();
		
		// remove from hand
		a.getAttackHand().remove(c);
		// add to discard
		a.getAttackDiscard().add(c);
		
		// Create copy
		Card copy = (Card) c.clone();
		// choose Angle
		an = a.askAngle(c.getAngle());
		copy.setAngle(an);
		// add to played history
		p.add(copy);
		
	}
}
