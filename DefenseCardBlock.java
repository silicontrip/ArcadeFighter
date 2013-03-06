public class DefenseCardBlock extends DefenseCard  {

	Angle reduceAngle;
	int reduceDamage;

	public DefenseCardBlock () { ; }

	public DefenseCardBlock (String name, int move, Angle angle, int cost, int stun, int damage, int reduce, Angle ra)
	{
		cardType = DEFENSE;
		cardImageName = new String ("defenseCardImage.png");

		
		this.name = name;
		cardMove = move;
		cardAngles = angle;
		this.cost = cost;
		fixedCost = cost;
		this.stun = stun;
		this.damage = damage;
		reduceAngle = ra;
		reduceDamage = reduce;
		
		special = new String[4];

		special[0] = "Reduce the damage of";
		special[1] = "the attack by another";
		special[2] = "1 if the angle of the";
		special[3] = "attack is " + reduceAngle;  
	}

	public void setReduceAngle(Angle ra) { reduceAngle = ra; }
	public Angle getReduceAngle() { return reduceAngle; }
	
	public void setReduceDamage(int rd) { reduceDamage = rd; }
	public int getReduceDamage() { return reduceDamage; }

	public void doDamage (Player a, Player d, Deck p) {
		// if the angle is low reduce the damage by an additional 1

		if (p.getTopAttack().getAngle().compare(reduceAngle)) {
			this.damage -= reduceDamage;
		}
			
		super.doDamage(a,d,p);
	}
}

