public class LeastDamageDiscardAttack implements AskDiscardAttackStrategy {

	public Card askDiscardAttack (Deck d) {

		Card leastDamage=null;

		int dmax=0;
		int dmin=20;

		if (d.size() == 0 ) { return new none(); }

		for(int i=0; i < d.size(); i++ ) {
			if (dmin > d.getCard(i).getDamage()) {
				dmin = d.getCard(i).getDamage();
				leastDamage = d.getCard(i);
			}
			if (dmax < d.getCard(i).getDamage()){
				dmax =  d.getCard(i).getDamage();
			}
		}

		if (dmin == dmax) {
			return new none();
		}
			return leastDamage;	
	}	

}

