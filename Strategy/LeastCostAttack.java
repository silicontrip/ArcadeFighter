public class LeastCostAttack implements AskAttackStrategy {

	public String toString() { return "LeastCostAttack"; }

	public Card askAttack(Deck p) {
                
		int cost = 20; 
		Card leastCost=null;
		
		for(int i=0; i < p.size(); i++ ) {
			if (cost > p.getCard(i).getCost()) {
				cost = p.getCard(i).getCost();
				leastCost = p.getCard(i);
			}
		}
		return leastCost; 
	}
}

