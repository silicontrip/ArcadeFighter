public class IncreaseCostDamageResolution implements DamageResolutionStrategy {

	public  void doDamageResolution (Card c,Player a, Player d, Deck p) {
		p.getTopAttack().addStun(c.getDefenseCost());
	}
}
