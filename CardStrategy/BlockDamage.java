public class BlockDamage implements DamageStrategy {

	public void doDamage (Card c, Player a, Player d, Deck p) {
		// if the angle is low reduce the damage by an additional 1

	// System.out.println("block damage: " + p.getTopAttack().getAngle() + " == " + c.getReduceAngle() + " by " + c.getReduceDamage());

		if (p.getTopAttack().getAngle().compare(c.getReduceAngle())) {
			c.reduceDamage(c.getReduceDamage());
		}
		
		p.getTopAttack().addDamage(c.getDamage());

		p.getTopAttack().addStun(c.getStun());
		p.getTopAttack().addStun(c.getCost());
			
	}
}
