public class BestDefense implements AskDefenseStrategy {

	public String toString() {return "BestDefense";}

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
					best.getTopDefense().doDamage(null,null,best);
					best.getTopAttack().doDamageResolution(null,null,best);
				
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

