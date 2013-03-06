public class ReduceNoBlockAttackCard extends NoBlockAttackCard  {

	int nextEnergy;


	public ReduceNoBlockAttackCard() {
				cardType = ATTACK;
	}
		
		public ReduceNoBlockAttackCard (String name, int move, Angle angle, int cost, int stun, int damage, int reduce) {
		
			this.name = name;
			cardType = ATTACK;

			cardMove = move;
			cardAngles = angle;
			this.cost = cost;
			fixedCost = cost;
			this.stun = stun;
			this.damage = damage;
			cardImageName = new String("attackCardImage.png");

			nextEnergy = reduce;
			
			special = new String[6];

			special[0]="Blocks may not be used";
			special[1]="against this attack. The";
			special[2]="opponent gains 2 less";
			special[3]="energy next turn if they";
			special[4]="are damaged by this";
			special[5]="attack.";


		}

	public void setReduce (int e) { nextEnergy = e; }
	public int getReduce() { return nextEnergy; }
	public void doDamageResolution (Player a, Player d, Deck p) {
		d.addNextEnergy(nextEnergy);		
	}
	
}

