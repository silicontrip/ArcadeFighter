public class AnotherCardIsValid implements IsValidStrategy {
	public boolean isValid (Card c, Player a, Player d, Deck p) {
			Card copy;
// check to see if there are any non zero cost valid cards.
			for (int i=0; i < d.getDefenseHand().size(); i++) {
				copy = d.getDefenseHand().getCard(i);
				if (copy.getCost() > 0) {
					copy.setCost(0);
					if (copy.isValid(a,d,p)){
						copy.resetCost();
						return true;
					}
					copy.resetCost();
				}
			}
			return false;
		}
}
