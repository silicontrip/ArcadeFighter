public class DefensePlay implements PlayStrategy {

		public void play (Card c, Player a, Player d, Deck p) {
		
			c.playCount();
			d.getDefenseDiscard().add(c);
			d.getDefenseHand().remove(c);
			
			Card copy = (Card)c.clone();
			
			p.add(copy);
		}
}
