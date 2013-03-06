public class MostDamageAttack implements AskAttackStrategy {

	public String toString() { return "MostDamageAttack"; }

	public Card askAttack(Deck p) {
                
		int damage = 0; 
		Card mostDamage = null;
		
		for(int i=0; i < p.size(); i++ ) {
			if (damage < p.getCard(i).getDamage()) {
				damage = p.getCard(i).getDamage();
				mostDamage = p.getCard(i);
			}
		}
		return mostDamage; 
	}
}

