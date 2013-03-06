public class RepeatedAttack implements AskAttackStrategy {

	int ENERGY;
	int STRATEGY;
	Angle ang;

	public RepeatedAttack (int e, int s) {
		ENERGY = e;
		STRATEGY = s;
	}

	public Angle getAngle () { return ang; }

	public String toString() { return "NotRepeatedAttack("+ENERGY+","+STRATEGY+")"; }

	public Card askAttack(Player a, Player d, Deck p) {
                
		int valid = 0;
		Deck ok=new Deck();
		Card leastCost=null;
		Card mostDamage=null;
		int damage = 0;
		int cost = 20;

		int tempEnergy = ENERGY;
			
				
		Card last  = p.getTopAttack();


		// choose angle
		ang = new Angle(true,true,true);
		if (last != null) {
			if (last.getDamage() > 0) {
				if (last.getAngle().isHigh()) { ang = new Angle(false,true,true); }
				if (last.getAngle().isMedium()) { ang = new Angle(true,false,true); }
				if (last.getAngle().isLow()) { ang = new Angle(true,true,false); }

				//ang = last.getAngle();
				tempEnergy = 0;
			}
		}
		
		if (a.getEnergy() < tempEnergy) { return new none(); }
        
		for(int i=0; i < a.getAttackHand().size(); i++ ) {
			Card c = a.getAttackHand().getCard(i);
			if ((c.isValid(a,d,p)) &&
				(a.getEnergy() - c.getCost() >= tempEnergy) &&
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


