
public class CheatingAngle implements AskAngleStrategy, AskAttackStrategy {

	Card chosenCard;
	Angle chosenAngle;
	
	public Angle askAngle ( Player a, Player d, Deck p) {
		cheat (a,d,p);
		return chosenAngle;
	}

	public Card askAttack (Deck p) {
		// in theory chosenCard should've been set by the ask Angle call
				// cheat (a,d,p);
		// not if they are two seperate instances of this object :-(
		return chosenCard;
	}

	public  void cheat (Player a, Player d, Deck p) {
	
		Deck testDeck = new Deck();
		int bestDamage = 0;
		int leastCost = 20;

		// needed for testing Ready
		if (p.getTopAttack() != null) {
			testDeck.add(p.getTopAttack());
		}
				
		for (int atc=0; atc < a.getAttackHand().size(); atc++) {
	
			// System.out.println("*** atc: " + atc);
	
			Card att;
			int damage;
						
			for (int ani=0; ani<3; ani++) {
				
				 damage = a.getAttackHand().getCard(atc).getDamage();

				
				att = (Card) a.getAttackHand().getCard(atc).clone();
			
				Angle ang=null;
				
				if (ani==1) { ang=new Angle (true,false,false); }
				if (ani==0) { ang=new Angle (false,true,false); }
				if (ani==2) { ang=new Angle (false,false,true); }

				if (att.getAngle().compare(ang)) {
				
								// System.out.println("angle: " + ang);

				
					att.setAngle(ang);
					
					testDeck.add(att);
					
					for (int dfc=0; dfc < d.getDefenseHand().size(); dfc++) {
					
						// System.out.println("dfc: " + dfc);
					
						Card def;
						def = (Card) d.getDefenseHand().getCard(dfc).clone();
						if (def.isValid(a,d,testDeck)) {
						
							 // System.out.println("running test: " + att + " vs " + def );

							testDeck.add(def);
							a.getDamage(testDeck);
							testDeck.getTopAttack().doDamage(null,null,testDeck);
							testDeck.getTopDefense().doDamage(null,null,testDeck);
							testDeck.getTopAttack().doDamageResolution(null,null,testDeck);
							testDeck.getTopDefense().doDamageResolution(null,null,testDeck);
							
							
							// find the least damage this attack would do
							if (testDeck.getTopAttack().getDamage() < damage) {
								damage = testDeck.getTopAttack().getDamage();
								// System.out.println("Worst Damage: " +damage);

							}
						//	d.resetNextEnergy();
							testDeck.removeTop();
						}
							
					}
					
					if (damage > bestDamage) {
						bestDamage = damage;
						// System.out.println("Best of the Worst Damage: " + damage);
						chosenCard = a.getAttackHand().getCard(atc);
						chosenAngle = ang;
						// System.out.println(chosenCard + " @ " + chosenAngle);

					}
					// should check for equal damage but least cost.
					if ((damage == bestDamage) && (a.getAttackHand().getCard(atc).getCost() < leastCost)) {
						bestDamage = damage;
						leastCost = a.getAttackHand().getCard(atc).getCost();
						// System.out.println("lesser cost: " + leastCost);
						chosenCard = a.getAttackHand().getCard(atc);
						chosenAngle = ang;
						// System.out.println(chosenCard + " @ " + chosenAngle);

					}
				
					testDeck.removeTop();
					//testDeck should be empty by now	
				}
				
			}
		}
	}
}

