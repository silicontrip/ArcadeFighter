public class NoneCanUse implements CanUseStrategy {
	public boolean canUse (Card c, Player a, Player d, Deck p, Card u) {
		return true;
	}
}
