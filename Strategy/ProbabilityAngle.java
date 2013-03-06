
public class ProbabilityAngle implements AskAngleStrategy, AskAttackStrategy {

	Card chosenCard;
	Angle chosenAngle;
	
	public Angle askAngle ( Player a, Player d, Deck p) {
		probability (a,d,p);
		return chosenAngle;
	}

	public Card askAttack (Deck p) {
		// in theory chosenCard should've been set by the ask Angle call
		// cheat (a,d,p);
		// not if they are two seperate instances of this object :-(
		return chosenCard;
	}

	public  void probability (Player a, Player d, Deck p) {
	
		Deck testDeck = new Deck();
		Deck probableDeck = new Deck();

		float inHandOdds;
		float predictedDamage;
		float bestDamage = 0;
		int leastCost = 20;

		// needed for testing Ready
		if (p.getTopAttack() != null) {
			testDeck.add(p.getTopAttack());
		}
		
		// Add the defense stack and the defense hand (this is not exactly cheating)
		probableDeck.addAll(d.getDefenseStack());
		probableDeck.addAll(d.getDefenseHand());
		
		inHandOdds =  1;
		if ( probableDeck.size() > 0 ) { 
			inHandOdds = d.getDefenseHand().size() / probableDeck.size();
		}	
//System.out.println("Begin search... " + a.getAttackHand().size() + "/" + a.getAttackHand().size());
				
						
		for (int atc=0; atc < a.getAttackHand().size(); atc++) {
	
		//	System.out.println("*** atc: " + atc);
	
			Card att;
						
			for (int ani=0; ani<3; ani++) {
				
				att = (Card) a.getAttackHand().getCard(atc).clone();
			
				Angle ang=null;
				
				if (ani==0) { ang=new Angle (true,false,false); }
				if (ani==1) { ang=new Angle (false,true,false); }
				if (ani==2) { ang=new Angle (false,false,true); }

//System.out.println("*** atc: " + atc + " @ " + ani + "card " + att.getName() + " @ "+att.getAngle() );


				if (att.getAngle().compare(ang)) {
				
					//	System.out.println("chosen ang: "  + ani);

				
					predictedDamage = 0;
				
					att.setAngle(ang);
					
					testDeck.add(att);
					
					for (int dfc=0; dfc < probableDeck.size(); dfc++) {
					
					//	System.out.println("dfc: " + dfc);
					
							
						Card def;
						def = (Card) probableDeck.getCard(dfc).clone();
						
						predictedDamage +=  (testDeck.getTopAttack().getDamage() * (1- inHandOdds)) ;

						
						if (def.isValid(a,d,testDeck)) {
						
						//	System.out.println("running test: " + att.getName() + " vs " + def.getName() );



							testDeck.add(def);
							a.getDamage(testDeck);
							testDeck.getTopAttack().doDamage(null,null,testDeck);
							testDeck.getTopDefense().doDamage(null,null,testDeck);
							testDeck.getTopAttack().doDamageResolution(null,null,testDeck);
							testDeck.getTopDefense().doDamageResolution(null,null,testDeck);
							
							predictedDamage +=  (testDeck.getTopAttack().getDamage() *  inHandOdds) ;
							
							// d.resetNextEnergy();
							
							testDeck.removeTop();
						}
							
					}
					
					if (predictedDamage > bestDamage) {
						bestDamage = predictedDamage;
					//	System.out.println("Best probable Damage: " + predictedDamage);
						chosenCard = a.getAttackHand().getCard(atc);
						chosenAngle = ang;
					//	System.out.println("chosen:" + chosenCard.getName() + " @ " + chosenAngle);

					}
					// should check for equal damage but least cost.
					if ((predictedDamage == bestDamage) && (a.getAttackHand().getCard(atc).getCost() < leastCost)) {
						bestDamage = predictedDamage;
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
		
	//	System.out.println ("End search...");
		
	}
	
	
}

