public class MostCostDiscardAttack implements AskDiscardAttackStrategy {

	public Card askDiscardAttack (Deck d) {

		Card mostCost=null;

		int dmax=0;
		int dmin=20;

		if (d.size() == 0 ) { return new none(); }

		for(int i=0; i < d.size(); i++ ) {
			if (dmin > d.getCard(i).getCost()) {
				dmin = d.getCard(i).getCost();
			}
			if (dmax < d.getCard(i).getCost()){
				dmax =  d.getCard(i).getCost();
				mostCost = d.getCard(i);
			}
		}

		if (dmin == dmax) {
			return new none();
		}
		return mostCost;	
	}	

}

