public class AngledAttack implements AskAttackStrategy {

	int ENERGY;
	int STRATEGY;
	Angle ang;

	public AngledAttack (int e, int s) {
		ENERGY = e;
		STRATEGY = s;
	}

	public Angle getAngle () { return ang; }

	public String toString() { return "AngledAttack("+ENERGY+","+STRATEGY+")"; }

	public Card askAttack(Player a, Player d, Deck p) {
                
		int valid = 0;
		Deck ok=new Deck();
		Card leastCost=null;
		Card mostDamage=null;
		int damage = 0;
		int cost = 20;

		int high = 0;
		int medium = 0;
		int low = 0;
			
		if (a.getEnergy() < ENERGY) { return new none(); }
				

		// summ angles 
		for (int i=0; i < d.getDefenseDiscard().size(); i++) {
			ang = d.getDefenseDiscard().getCard(i).getAngle();
			if (ang.isHigh()) { high++; }
			if (ang.isMedium()) { medium++; }
			if (ang.isLow()) { low++; }
		}
		

		// choose angle
		// ang = new Angle(false,true,false);
		if ((high >= medium) && (high >= low)) { ang = new Angle(true,false,false); }
		if ((low >= medium) && (low >= high)) { ang = new Angle(false,false,true); }
		if ((medium >= high) && (medium >= low)) { ang = new Angle(false,true,false); }
		// *should default to medium* 
        
//		System.out.println ("High: " + high + " Medium: " + medium + " Low: " + low + " (" + ang + ")");

		
		for(int i=0; i < a.getAttackHand().size(); i++ ) {
			Card c = a.getAttackHand().getCard(i);
			if ((c.isValid(a,d,p)) &&
				(a.getEnergy() - c.getCost() >= ENERGY) &&
				(c.getAngle().compare(ang))) {
				valid ++;
				ok.add(c);
				if (cost > c.getCost()) {
					cost = c.getCost();
					leastCost = c;
				}
				if (damage < c.getDamage()) {
					damage = c.getDamage();
					mostDamage = c;
				}
			}
		}
					
				
		if (valid > 1) {
			if (STRATEGY == 1) { 
				return mostDamage; 
			}
			return leastCost;
		}
                
		if (valid == 0) {
			// System.out.println("Computer does not play an Attack card");
			return new none(); 
		}
                
		return ok.getCard(0);
	}
}


