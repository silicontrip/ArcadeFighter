public class DefenseCard extends Card  {
	
	public DefenseCard() {
		cardType = DEFENSE;
		cardImageName = new String ("defenseCardImage.png");
	}
	
	public DefenseCard(String name, int move, Angle angle, int cost, int stun, int damage) {
		cardType = DEFENSE;
		cardImageName = new String ("defenseCardImage.png");

		this.name = name;
		cardMove = move;
		cardAngles = angle;
		this.cost = cost; fixedCost = cost;
		this.stun = stun;
		this.damage = damage;
	}


	public boolean isValid (Player a, Player d, Deck p) {
	
		if (d.getEnergy() < cost) { return false; }
		if (!this.getAngle().compare(p.getTopAttack().getAngle())) { return false; }
		if (!p.getTopAttack().canUse(a,d,p,(Card)this)) { return false; }
		
		return true;
	}
	
	public void doDamage (Player a, Player d, Deck p) {
		//relative Damage
		p.getTopAttack().addStun(this.stun);
		p.getTopAttack().addStun(this.cost);
				
		p.getTopAttack().addDamage(this.damage);
	}
		
		public void doDamageResolution (Player a, Player d, Deck p) { ; }
		
		public void play (Player a, Player d, Deck p) {
		
			playCount ++;
			d.getDefenseDiscard().add(this);
			d.getDefenseHand().remove(this);
			
			Card copy = (Card)this.clone();
			
			p.add(copy);
		}

}

