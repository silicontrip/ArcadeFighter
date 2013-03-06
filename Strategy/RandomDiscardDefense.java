import java.util.*;


public class RandomDiscardDefense implements AskDiscardDefenseStrategy {

private static final Random rand = new java.util.Random(java.lang.System.currentTimeMillis());


	public Card askDiscardDefense (Deck d) {
	
		if (d.size() > 0) {
			int r = rand.nextInt(2);
			if (r == 1) {
				return (Card) d.get(rand.nextInt(d.size()));
			}
		}
		return new none();
	}

}
