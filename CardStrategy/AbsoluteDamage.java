public class AbsoluteDamage implements DamageStrategy {
	public void doDamage (Card c, Player a, Player d, Deck p) {
		//Absolute Damage
		p.getTopAttack().setStun(c.getCost());
		p.getTopAttack().setDamage(c.getDamage());
	}
}
