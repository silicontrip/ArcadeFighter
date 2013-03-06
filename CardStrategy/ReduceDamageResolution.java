public class ReduceDamageResolution implements DamageResolutionStrategy {

	public void doDamageResolution (Card c, Player a, Player d, Deck p) {
		// System.out.println("current: " + d.getNextEnergy() + " add " + c.getNextEnergy());
		if (d != null) {
			d.addNextEnergy(c.getNextEnergy());		
		}
		// System.out.println("after: " + d.getNextEnergy() );

	}
}
