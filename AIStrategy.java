public interface AskDefenseStrategy {
	public Card askDefense(Player a, Player d, Deck p);	
}

class RandomDefense implements AskDefenseStrategy {

	public Card askDefense(Player a, Player d, Deck p) {
		// return askCard("Defense",Main.getGame().defender.getDefenseHand());
		int valid = 0;
	
		for(int i=0; i < d.getDefenseHand().size(); i++ ) {
			if (d.getDefenseHand().getCard(i).isValid(a,d,p)) {
				valid ++;
			}
		}
		
		if (valid > 1) {return askCard(d.getDefenseHand());}
		
		if (valid == 0) {
			//System.out.println("Computer does not play a Defense card");
 			return new none(); 
		}
		
		for(int i=0; i < d.getDefenseHand().size(); i++ ) {
			if (d.getDefenseHand().getCard(i).isValid(a,d,p)) {
					//System.out.println("Computer Chooses " + d.getDefenseHand().getCard(i).getName() + " for Defense" );
					return d.getDefenseHand().getCard(i);
				}
		}
		return new none();
		
	}
}

class BestDefense implements AskDefenseStrategy {

	public Card askDefense(Player a, Player d, Deck p) {
		
		Deck best = new Deck();
		Card chosen = null;
		int valid = 0;
	
		int stun=20;
		int damage=20;
		int sd = 40;
		Card openstance = null;
	
		for(int i=0; i < d.getDefenseHand().size(); i++ ) {
			if (d.getDefenseHand().getCard(i).isValid(a,d,p)) {
				best.clear();
				best.add(p.getTopAttack().clone());
				best.add(d.getDefenseHand().getCard(i).clone());
				
				if (d.getDefenseHand().getCard(i).getName().equalsIgnoreCase("openstance")) { 
					openstance = d.getDefenseHand().getCard(i);
				//	System.out.println(d.getDefenseHand().getCard(i).getName());
				} else {
				
					a.getDamage(best);
					best.getTopDefense().doDamage(a,d,best);
					best.getTopAttack().doDamageResolution(a,d,best);
				
				//	System.out.println(d.getDefenseHand().getCard(i).getName() + " - D:" + best.getTopAttack().getDamage() +" S:" + best.getTopAttack().getStun());
				
					if( sd > best.getTopAttack().getDamage() + best.getTopAttack().getStun() ) {
							damage = best.getTopAttack().getDamage();
							stun  = best.getTopAttack().getStun();
							sd = best.getTopAttack().getDamage() + best.getTopAttack().getStun();
							chosen = d.getDefenseHand().getCard(i);
					}
					if ((sd == best.getTopAttack().getDamage() + best.getTopAttack().getStun() ) &&
						best.getTopAttack().getName().equalsIgnoreCase("ready")) {
						chosen = d.getDefenseHand().getCard(i);
					}
				}
			}
		}
		
		if (chosen == null) {
			return new none();
		}
		if (openstance != null && (chosen.getCost()>0)) {
			return openstance;
		}
		// System.out.println(chosen.getName());
		// System.out.println("");

		return chosen;
		
	}

}

interface AskAttackStrategy {

	public Card askAttack(Player a, Player d, Deck p);

}

class RandomAttack implements AskAttackStrategy {

	public Card askAttack(Player a, Player d, Deck p) {
		
			int valid =0;
			Deck ok=new Deck();
			
			if (a.getEnergy() < 4) { return new none(); }
				
        
			for(int i=0; i < a.getAttackHand().size(); i++ ) {
					if ((a.getAttackHand().getCard(i).isValid(a,d,p))
					 //  && (a.getEnergy() - a.getAttackHand().getCard(i).getCost() >= 4)
						) {
							valid ++;
							ok.add(a.getAttackHand().getCard(i));
					}
			}
					
				
			// if there is more than 1 valid attack card choose randomly
		if (valid > 1) { return askCard(ok); }

		
		if (valid == 0) {
			// System.out.println("Computer does not play an Attack card");
 			return new none(); 
		}
		
		for(int i=0; i < a.getAttackHand().size(); i++ ) {
			if (a.getAttackHand().getCard(i).isValid(a,d,p)) {
					//System.out.println("Computer Chooses " + a.getAttackHand().getCard(i).getName() + " for Defense" );
					return a.getAttackHand().getCard(i);
				}
		}
		return new none();

		
		}

}

class OrderedAttack implements AskAttackStrategy {

	int ENERGY;
	int STRATEGY;

	public OrderedAttack (int e, int s) {
		ENERGY = e;
		STRATEGY = s;
	}

	public Card askAttack(Player a, Player d, Deck p) {
                
		int valid = 0;
		Deck ok=new Deck();
		Card leastCost=null;
		Card mostDamage=null;
		int damage = 0;
		int cost = 20;
			
		if (a.getEnergy() < ENERGY) { return new none(); }
				
        
		for(int i=0; i < a.getAttackHand().size(); i++ ) {
			if ((a.getAttackHand().getCard(i).isValid(a,d,p)) &&
				(a.getEnergy() - a.getAttackHand().getCard(i).getCost() >= ENERGY)) {
				valid ++;
				ok.add(a.getAttackHand().getCard(i));
				if (cost > a.getAttackHand().getCard(i).getCost()) {
					cost = a.getAttackHand().getCard(i).getCost();
					leastCost = a.getAttackHand().getCard(i);
				}
				if (damage < a.getAttackHand().getCard(i).getDamage()) {
					damage = a.getAttackHand().getCard(i).getDamage();
					mostDamage = a.getAttackHand().getCard(i);
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

interface AskDiscardDefense {
	public Card askDiscardDefense (Deck d);
}

class RandomDiscardDefense implements AskDiscardDefense {

	public Card askDiscardDefense (Deck d) {
	
		int r = rand.nextInt(2);
		if (r == 1) {
			return askCard(d);
		}
		return new none();
	}

}

class DuplicateCoverDiscardDefense implements AskDiscardDefense {
public Card askDiscardDefense (Deck d) { 

int high=4;
int medium=4;
int low=4;

	for(int i=0; i < d.size(); i++ ) {

		int angles=0;

		if (d.getCard(i).getAngle().isHigh()) { angles++; }
		if (d.getCard(i).getAngle().isMedium()) { angles++; }
		if (d.getCard(i).getAngle().isLow()) { angles++; }
		
		if (angles == 0) {
			// WTF???
			System.out.println("A card with no angles");
		}
		
		if (d.getCard(i).getAngle().isHigh() && (high > angles)) { high =  angles; }
		if (d.getCard(i).getAngle().isMedium() && (medium > angles)) { medium = angles; }
		if (d.getCard(i).getAngle().isLow() && (low > angles)) { low = angles; }
	}

// System.out.println ("High: " + high  + " medium: " + medium + " low: " + low);

	// dont discard if we have the 3 angles already covered
//	if (high>1 || medium>1 || low>1)  {

	for(int i=0; i < d.size(); i++ ) {
		int cHigh = 4;
		int cMedium = 4;
		int cLow = 4;

		for(int j=0; j < d.size(); j++ ) {

			if (j!=i) {
				int angles=0;
				if (d.getCard(j).getAngle().isHigh()) { angles++; }
				if (d.getCard(j).getAngle().isMedium()) { angles++; }
				if (d.getCard(j).getAngle().isLow()) { angles++; }
	
				if (d.getCard(j).getAngle().isHigh() && (cHigh > angles)) { cHigh =  angles; }
				if (d.getCard(j).getAngle().isMedium() && (cMedium > angles)) { cMedium = angles; }
				if (d.getCard(j).getAngle().isLow() && (cLow > angles)) { cLow = angles; }
			}
		}
				// removing this card doesn't affect the angle coverage.
		if ((cHigh == high) && (cMedium == medium) && (cLow == low)) {
			// don't throw away openstance if there is a move.
			if (!d.getCard(i).getName().equalsIgnoreCase("openstance") || !(high==1 || medium==1||low==1)) 
			{
					// System.out.println ("Discarding: " + d.getCard(i).getName());
					return d.getCard(i);
			}
		}
	}
//	}
	return new none();
}

}

interface AskDiscardAttackStrategy {

public Card askDiscardAttack (Deck d) ;

}

class RandomDiscardAttack implements AskDiscardAttackStrategy {

	public Card askDiscardAttack (Deck d) {
	
		int r = rand.nextInt(2);
		if (r == 1) {
			return askCard(d);
		}
		return new none();
	}

}

class LeastDamageDiscardAttack implements AskDiscardAttackStrategy {

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

class MostCostDiscardAttack implements AskDiscardAttack{

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

interface AskAngle {

public Angle askAngle(Angle a);

}

class RandomAskAngle implements AskAngle {

	public Angle askAngle (Angle a) {
        
		int possible=0;
		Angle[] aa = new Angle[3];
			
		if (a.isHigh()) { aa[possible] = new Angle(true,false,false); possible++; }
		if (a.isMedium()) { aa[possible] = new Angle(false,true,false); possible++ ; }
		if (a.isLow()) { aa[possible] = new Angle(false,false,true); possible++ ; }

		if (possible > 0) {
			int choice = rand.nextInt(possible);
			return aa[choice];
		}
				
		return new Angle(false,false,false);			
	}
}

interface AskCharacter {

public Character askCharacter(Character[] c);

}

class FixedAskCharacter implements AskCharacter {

	int choose;

	public FixedAskCharacter (int i) {if (i>=0) {choose = i;}}
	
	public Character askCharacter(Character[] c) {
	
		if (choose < c.length) {
			return c[choose];
		}
		return c[0];
	}
}

class RandomAskCharacter implements AskCharacter {

	public Character askCharacter (Character[] c) {	
		int choice = rand.nextInt(c.length);
		return c[choice];
	}

}

interface AskBool {

public boolean askBool (String s) ;

}

class RandomAskBool implements AskBool {

	public boolean askBool (String s) {
		return rand.nextInt(2)==1;
	}

}

class TrueAskBool implements AskBool {

	public boolean askBool (String s) {
		return true;
	}
	
}

class FalseAskBool implements AskBool {

	public boolean askBool (String s) {
		return false;
	}
	
}