interface PlayStrategy {
	play (Card c, Player a, Player d,  Deck d);
}

class PlayDiscardStrategy implements PlayStrategy {

	public void play (Card c, Player a, Player d, Deck p) {
	
		Deck f;
		Deck t;
		
		if (c.getType() == ATTACK) {
			f = a.getAttackHand();
			t = a.getAttackDiscard();
		} 
		if (c.getType() == DEFENSE) {
			f = d.getDefenseHand();
			t = d.getDefenseDiscard();
		}
		
		f.remove(c);
		t.add(c);

	}
}	

class PlayRemoveStrategy implements PlayStrategy {

	public void play (Card c, Player a, Player d, Deck p) {
	
		Deck f;
		
		if (c.getType() == ATTACK) {
			f = a.getAttackHand();
		} 
		if (c.getType() == DEFENSE) {
			f = d.getDefenseHand();
		}
		
		f.remove(c);
	}
}	




class PlayRetainStrategy implements PlayStrategy {

		int retainCost = 2;

		pubic void setCost (int c)
		{
			if (c>0) { retainCost = c; }
		}

		public void play (Card c, Player a, Player d, Deck p) {
		// may pay an additional energy to avoid discard
		
			boolean retain=false;
		// open stance reduces the cost of retaining to zero so might as well keep it.
			if (retainCost > 0) {
				if (cost + retainCost <= d.getEnergy() ) {
					retain = d.askBool("Pay additional " + retainCost + " energy to avoid discarding");
				}
			} else {
				retain = true;
			}
			
			playCount ++;
			
			Card copy = (Card)c.clone();

			if (!retain) {
				d.getDefenseDiscard().add(c);
				d.getDefenseHand().remove(c);
			} else {
				copy.addCost(retainCost);
			}
			
			p.add(copy);

		}
}

class PlayAngleStrategy implements PlayStrategy {

	public void play (Card c, Player a, Player d, Deck p) {

		// Create copy
		Card copy = (Card) c.clone();
		// choose Angle
		an = a.askAngle(this.getAngle());
		copy.setAngle(an);
		// add to played history
		p.add(copy);
		
	}
}

class PlayHistoryStrategy implements PlayStrategy {

	public void play (Card c, Player a, Player d, Deck p) {
		Card copy = (Card)c.clone();
		p.add(copy);
	}
}

class PlayOpenStanceStrategy implements PlayStrategy {

	public void play (Card c, Player a, Player d, Deck p) {
		// Choose another defense
		// that defense cost is reduced to zero
				
		do {
			select = d.askDefense(a,d,p);
		} while (!select.isValidDefense(a,d,p));

		// Reduce cost to zero -- here?
		copy = (Card)select.clone();
		copy.setCost(0);
		copy.play(a,d,p); // choose to pay 1 extra to retain
	
	}
}
