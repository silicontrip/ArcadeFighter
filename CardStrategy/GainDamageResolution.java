public class GainDamageResolution implements DamageResolutionStrategy {

	public void doDamageResolution (Card c, Player a, Player d, Deck p) {
		if (p.getTopAttack().getDamage() > 0) { 
			p.getTopAttack().addCost(c.getAddCost());
		}

	}
}
