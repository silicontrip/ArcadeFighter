public class NoBlockAttackCard extends AttackCard  {

	int noMoveType = BLOCK;
	
	public void setNoMoveType(int mt) { noMoveType = mt; }
	public int getNoMoveType() {return noMoveType; }

	public boolean canUse (Player a, Player d, Deck p, Card c) {
		return (c.getMove() != noMoveType);
	}
}

