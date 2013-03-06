public class DefenseCardMove extends DefenseCard  {
	
		int retainCost;
	
		public DefenseCardMove () {
			;
		}
	
		public DefenseCardMove (String name, int move, Angle angle, int cost, int stun, int damage, int retain)
		{
				this.name=name;
                cardType = DEFENSE;
                cardMove = move;
                cardAngles = angle;
                this.cost = cost; fixedCost = cost;
                this.stun = stun;
                this.damage = damage;
				retainCost = retain;
				special = new String[3];

				special[0]="You may pay an extra";
				special[1]=retain +" energy to avoid";
				special[2]="discarding this card.";
		}

	
		public void setRetainCost(int i)
		{
			if (i>=0) { retainCost = i; }
		}
	
		public int getRetainCost() { return retainCost; }
	
		public void play (Player a, Player d, Deck p) {
		// may pay an additional energy to avoid discard
		
			Card copy = (Card)this.clone();
			playCount ++;

		// choose retain
			boolean retain=false;
		// open stance reduces the cost of retaining to zero so might as well keep it.
			if (retainCost > 0) {
				if (cost + retainCost <= d.getEnergy() ) {
					retain = d.askBool("Pay additional " + retainCost + " energy to avoid discarding");
				}
			} else {
				retain = true;
			}

		// perform retain
			if (!retain) {
				d.getDefenseDiscard().add(this);
				d.getDefenseHand().remove(this);
			} else {
				copy.addCost(retainCost);
			}
			
			p.add(copy);

		}
		
		public void doDamage (Player a, Player d, Deck p) {
			//Absolute Damage
			p.getTopAttack().setStun(this.cost);
			p.getTopAttack().setDamage(this.damage);
		}
}

