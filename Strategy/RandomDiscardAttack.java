import java.util.*;


class RandomDiscardAttack implements AskDiscardAttackStrategy {

private static final Random rand = new java.util.Random(java.lang.System.currentTimeMillis());


	public Card askDiscardAttack (Deck d) {
	
		int r = rand.nextInt(2);
		if (r == 1) {
			if (d.size()>0) {
				return (Card) d.get(rand.nextInt(d.size()));
			}
		}
		return new none();
	}

}
