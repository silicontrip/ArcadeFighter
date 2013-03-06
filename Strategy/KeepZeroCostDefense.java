public class KeepZeroCostDefense implements AskDiscardDefenseStrategy {

	public Card askDiscardDefense (Deck d) {
	
		// search for openstance
		for (int i = 0; i < d.size(); i++) {
			if (d.getCard(i).getName().equalsIgnoreCase("openstance")) { return new none(); }
		}
		for (int i = 0; i < d.size(); i++) {
			if (d.getCard(i).getCost() > 0) { return d.getCard(i); }
		}
		return new none();
		
		
	}

}
