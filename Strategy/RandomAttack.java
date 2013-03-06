import java.util.*;

public class RandomAttack implements AskAttackStrategy {

	private static final Random rand = new java.util.Random(java.lang.System.currentTimeMillis());

	public String toString() { return "RandomAttack"; }

	public Card askAttack( Deck p) {
		return (Card) p.get(rand.nextInt(p.size()));		
	}

}

