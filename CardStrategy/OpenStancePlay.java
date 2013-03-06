public class OpenStancePlay implements PlayStrategy {

public void play (Card c, Player a, Player d, Deck p) {
		// Choose another defense
		// that defense cost is reduced to zero
	
		Card select;
		Card copy;
		
		//super.play(a,d,p);

		c.playCount();
		d.getDefenseDiscard().add(c);
		d.getDefenseHand().remove(c);
			
		copy = (Card)c.clone();
			
		p.add(copy);

		
		// set hand to zero cost
		for (int i=0; i < d.getDefenseHand().size(); i++) {
			d.getDefenseHand().getCard(i).setCost(0);
		}
		
		do {
			select = d.askDefense(a,d,p);
		} while (!select.isValid(a,d,p));

		// reset hand to proper cost
				for (int i=0; i < d.getDefenseHand().size(); i++) {
				d.getDefenseHand().getCard(i).resetCost();
		}


		// Reduce cost to zero -- here?
		copy = (Card)select.clone();
		copy.setCost(0);
		
		// special case cards
		select = p.getTopAttack();
		if (select.getName().equalsIgnoreCase("Rush")) {
			select.setDefenseCost(0);
		}
		if (copy.getName().equalsIgnoreCase("Jump") || 
		    copy.getName().equalsIgnoreCase("BackStep") ||
		    copy.getName().equalsIgnoreCase("Duck")
		) {
			copy.setRetainCost(0);
		}
		copy.play(a,d,p); // choose to pay 1 extra to retain
	}
}
