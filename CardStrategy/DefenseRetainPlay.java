public class DefenseRetainPlay implements PlayStrategy {

	public void play (Card c, Player a, Player d, Deck p) {
		// may pay an additional energy to avoid discard
		
			Card copy = (Card)c.clone();
			c.playCount();

		// choose retain
			boolean retain=false;
		// open stance reduces the cost of retaining to zero so might as well keep it.
			if (c.getRetainCost() > 0) {
				if (c.getCost() + c.getRetainCost() <= d.getEnergy() ) {
					retain = d.askBool("Pay additional " + c.getRetainCost() + " energy to avoid discarding");
				}
			} else {
				retain = true;
			}

		// perform retain
			if (!retain) {
				d.getDefenseDiscard().add(c);
				d.getDefenseHand().remove(c);
			} else {
				copy.addCost(c.getRetainCost());
			}
			
			p.add(copy);

		}
}
