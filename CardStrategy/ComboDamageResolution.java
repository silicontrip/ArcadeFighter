public class ComboDamageResolution implements DamageResolutionStrategy {

	public  void doDamageResolution (Card c, Player a, Player d, Deck p) {

			// null player means that the AI is testing the attack
		if (a != null) {
				// pay 3 extra energy to triple the damage

			if ( a.askBool ("Pay " + c.getAddCost() + " extra for " + c.getMulDamage() + " times Damage")) {
				p.getTopAttack().addCost(c.getAddCost());
				p.getTopAttack().mulDamage(c.getMulDamage());
			}
		} else {
			p.getTopAttack().addCost(c.getAddCost());
			p.getTopAttack().mulDamage(c.getMulDamage());
		}
	}
}
