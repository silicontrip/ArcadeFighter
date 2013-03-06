import java.util.*;

public class RandomDefense implements AskDefenseStrategy {


private static final Random rand = new java.util.Random(java.lang.System.currentTimeMillis());


	public Card askDefense(Player a, Player d, Deck p) {
		int valid = 0;
	
		for(int i=0; i < d.getDefenseHand().size(); i++ ) {
			if (d.getDefenseHand().getCard(i).isValid(a,d,p)) {
				valid ++;
			}
		}
		
		if (valid > 1) {
			return (Card) d.getDefenseHand().get(rand.nextInt(d.getDefenseHand().size()));
		}
		
		if (valid == 0) {
			//System.out.println("Computer does not play a Defense card");
 			return new none(); 
		}
		
		for(int i=0; i < d.getDefenseHand().size(); i++ ) {
			if (d.getDefenseHand().getCard(i).isValid(a,d,p)) {
					return d.getDefenseHand().getCard(i);
				}
		}
		return new none();
		
	}
}

