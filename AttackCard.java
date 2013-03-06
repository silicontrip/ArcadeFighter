public class AttackCard extends Card  {

	public AttackCard() {
		cardType = ATTACK;
		cardImageName = new String("attackCardImage.png");

	}
	
	public AttackCard (String name, int move, Angle angle, int cost, int stun, int damage )
	{
		this.name = name;
		cardType = ATTACK;
		cardMove = move;
		cardAngles = angle;
		this.cost = cost;
		fixedCost = cost;
		this.stun = stun;
		this.damage = damage;
		cardImageName = new String("attackCardImage.png");

	}

	
	public int getStun() {
		if (stun>0) {
			return stun;
		} else {
			return 0;
		}
	}

	public int getDamage() {
		if (damage>0) {
			return damage;
		} else {
			return 0;
		}
	}

	public boolean isValid (Player a, Player d, Deck p) {
		return (a.getEnergy() >= cost) ;
	}

	public void play (Player a, Player d, Deck p) {
	
		Card c;
		Angle an; 
		playCount++;
		
		// remove from hand
		c=a.getAttackHand().remove(this);
		// add to discard
		a.getAttackDiscard().add(c);
		
		// Create copy
		Card copy = (Card) c.clone();
		// choose Angle
		an = a.askAngle(this.getAngle());
		copy.setAngle(an);
		// add to played history
		p.add(copy);
		
	}

	public void doDamage (Player a, Player d, Deck p) { ; }
	public void doDamageResolution (Player a, Player d, Deck p) { ; }

}

