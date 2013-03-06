public class RelativeDamage implements DamageStrategy {

	public void doDamage (Card c, Player a, Player d, Deck p) {
		//relative Damage
		p.getTopAttack().addStun(c.getStun());
		p.getTopAttack().addStun(c.getCost());
		p.getTopAttack().addDamage(c.getDamage());
	}
}
