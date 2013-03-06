public class IncreasedDefenseCostCanUse implements CanUseStrategy {

	public boolean canUse (Card c, Player a, Player d, Deck p,Card u) {
		// defenses cost 2 more to play against this attack
		//ArcadeFighter a = Main.getGame();
		return (d.getEnergy() >= u.getCost()+c.getDefenseCost());
	}
}
