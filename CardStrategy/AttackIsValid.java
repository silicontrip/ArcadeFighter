public class AttackIsValid implements IsValidStrategy {
	public boolean isValid (Card c, Player a, Player d, Deck p) {
		return (a.getEnergy() >= c.getCost()) ;
	}
}
