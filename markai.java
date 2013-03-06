
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.*;


class Markai extends Computer {

	public String getName() { return "mark"; }

	int times;
	int totattacks;
	int attacks;
	Angle AttackAngle;
	Random rand;
	static final int ENERGY = 4;

	public Markai() {
		 rand = new java.util.Random(java.lang.System.currentTimeMillis());
	}
	
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

	public Card askCard (String s, Deck d) {
		if (d.size() > 0) {
			int r = rand.nextInt(d.size());
			return (Card) d.get(r);
		}
		return new none();
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
					
				
                // if there is more than 1 valid attack card choose randomly
                if (valid > 1) {
					return mostDamage;
					//return askCard("A",ok);
				}
                
                if (valid == 0) {
                        // System.out.println("Computer does not play an Attack card");
                        return new none(); 
                }
                
				return ok.getCard(0);
		}

	
	
		public Angle askAngle (Angle a) {
        
                int possible=0;
                Angle[] aa = new Angle[3];
			
                if (a.isHigh()) { aa[possible] = new Angle(true,false,false); possible++; }
                if (a.isMedium()) { aa[possible] = new Angle(false,true,false); possible++ ; }
                if (a.isLow()) { aa[possible] = new Angle(false,false,true); possible++ ; }

                if (possible > 0) {
                        int choice = rand.nextInt(possible);
						// AttackAngle = aa[choice];
                        return aa[choice];
                }
				
                return new Angle(false,false,false);
			
        }

	
	public Character askCharacter ( Character[] c) {
	
		int choice = rand.nextInt(c.length);
		//System.out.println ("Computer Chooses " + c[choice].getName() );
		return c[choice];
	}
	
	public boolean askBool (String s) {
		return false;
	//	return rand.nextInt(2)==1 ;
	}

}

class MarkaiTest extends Markai {

	public String getName() { return "marktesting"; }

	public Card askDefense(Player a, Player d, Deck p) {
		
		Deck best = new Deck();
		Card chosen = null;
		int valid = 0;
	
		int stun=20;
		int damage=20;
		int sd = 40;
		Card openstance = null;
	
	
		// Predict remaining attacks(a.getEnergy(), a.getAttackHand().size(), d.getLife(), d.getEnergy());
		// Returns 0-
		
		// remainingPercent = 100 / (remainingAttacks + 1);
		// tLife = 4; if life < tLife then tLife = life
		// lifePercentage = 100 * damage / tLife (needs special case for combo)
		// tNrg = 4; if energy < tNrg then tNrg = energy
		// nrgPercentage = 100 * stun / tNrg
		
		//playCard = remainingPercentage
		// if lifePercentage > playCard then playCard=lifePercentage
		// if nrgPercentage > playCard then playCard=nrgPercentage
		// random = netInt(100);
		// if (random < playCard) {
		
	
		// chooseCard = 50 * (life - damage) / life + 50 * (energy - stun) / energy
		//
	
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

