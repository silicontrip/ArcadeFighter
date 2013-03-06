public class RemovePlay implements PlayStrategy {
	public void play (Card c, Player a, Player d, Deck p) {
		// Remove after play
		Card copy;
		Angle an;
		// ArcadeFighter a = Main.getGame();
		a.getAttackHand().remove(c);
		an = a.askAngle(c.getAngle());
		c.playCount();
		copy =(Card)c.clone();
		copy.setAngle(an);
		p.add(copy);
	}

}
