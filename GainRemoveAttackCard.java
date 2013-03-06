public class GainRemoveAttackCard extends RemoveAttackCard  {

	int addCost;

	public GainRemoveAttackCard() { ; }
	
	public GainRemoveAttackCard (String name, int move, Angle angle, int cost, int stun, int damage, int gain) {
		
			this.name = name;
			cardType = ATTACK;
			cardMove = move;
			cardAngles = angle;
			this.cost = cost;
			fixedCost = cost;
			this.stun = stun;
			this.damage = damage;
			addCost = gain;
			cardImageName = new String("attackCardImage.png");

		special = new String[4];

		special[0] = "Gain 2 energy if the";
		special[1] = "attack damages the";
		special[2] = "opponent. Remove from";
		special[3] = "game after use.";
	}
	
	public void setAddCost(int c) { addCost = c; }
	public int getAddCost() { return addCost; }	
	public void doDamageResolution (Player a, Player d, Deck p) {
		// gain 2 energy if attack is successful
		if (p.getTopAttack().getDamage() > 0) { 
			p.getTopAttack().addCost(addCost);
		}
	}
}

