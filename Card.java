import java.awt.*;
import javax.swing.*;
import java.io.*;


// enums not available in java 1.4 grrr... arg
// public enum Type { ATTACK, DEFENSE };
// public enum Move { BLOCK, MISC, MOVE, KICK, PUNCH, SPECIAL };



public class Card implements Serializable, Cloneable  {

	public static final int ATTACK = 1;
	public static final int DEFENSE = 2;

	public static final int BLOCK = 1;
	public static final int MISC = 2;
	public static final int MOVE = 3;
	public static final int KICK = 4;
	public static final int PUNCH = 5;
	public static final int SPECIAL = 6;

// Standard Properties
	String name;
	int cardType; // Attack Defense
	int cardMove; // Move Block Misc 
	public Angle cardAngles;
	public int cost;  // reduce energy in attacker
	public int stun; // reduce energy in defender
	public int damage; // reduce life in defender
	int fixedCost;
	boolean fixedCostSet=false;
	int played=0;
	String cardImageName;
	String special;

// Properties for special cards
	int noMoveType;
	int nextEnergy;
	int addCost;
	Angle reduceAngle;
	int reduceDamage;
	int retainCost;
	// int addCost ;
	int mulDamage ;
	int defenseCost;

// Strategy for Methods
	PlayStrategy playStrategy;
	IsValidStrategy isValidStrategy;
	DamageStrategy doDamageStrategy;
	DamageResolutionStrategy doDamageResolutionStrategy;
	CanUseStrategy canUseStrategy;
	
	public PlayStrategy getPlayStrategy() { return playStrategy; }
	public IsValidStrategy getIsValidStrategy() { return isValidStrategy; }
	public DamageStrategy getDamageStrategy() { return doDamageStrategy; }
	public DamageResolutionStrategy getDamageResolutionStrategy() { return doDamageResolutionStrategy; }
	public CanUseStrategy getCanUseStrategy() { return canUseStrategy; }
	
	public void setPlayStrategy(PlayStrategy ps) { playStrategy = ps; }
	public void setIsValidStrategy(IsValidStrategy ivs) { isValidStrategy = ivs; }
	public void setDamageStrategy(DamageStrategy ds) { doDamageStrategy = ds; }
	public void setDamageResolutionStrategy(DamageResolutionStrategy drs) { doDamageResolutionStrategy = drs; }
	public void setCanUseStrategy(CanUseStrategy cus) { canUseStrategy = cus; }

//getters and setters
	public int getMove() {return cardMove; }
	public void setMove(int m) { cardMove = m; }
	public int getType() { return cardType; }
	public void setType(int t) { cardType = t; }

	public String[] getSpecial() {
		if (special == null) { return null; }
		return special.split("\\n");
	} 
	public void setSpecialText(String sp) { special = sp; }
	public String getSpecialText() {return special ;}

	public void setCardImageName (String cin) { cardImageName = cin; }
	public String getCardImageName () { return cardImageName; }

	public String getName() { return name; }
	public void setName(String n) { name = n; }
	
	// public int getFixedCost() {return fixedCost;}
	// public void setFixedCost(int i) {fixedCost=i;}

// for special cards        
	public void setNoMoveType(int mt) { noMoveType = mt; }
	public int getNoMoveType() {return noMoveType; }
	public void setNextEnergy (int e) { nextEnergy = e; }
	public int getNextEnergy() { return nextEnergy; }
	public void setAddCost(int c) { addCost = c; }
	public int getAddCost() { return addCost; }     
	public void setReduceAngle(Angle ra) { reduceAngle = ra; }
	public Angle getReduceAngle() { return reduceAngle; }
	public void setReduceDamage(int rd) { reduceDamage = rd; }
	public int getReduceDamage() { return reduceDamage; }
	public void setRetainCost(int i) { if (i>=0) { retainCost = i; } }
	public int getRetainCost() { return retainCost; }
	public void setMulDamage(int c) { mulDamage = c; }
	public int getMulDamage() { return mulDamage; }
	public void setDefenseCost(int i) { if (i>=0) { defenseCost = i; } }
	public int getDefenseCost() { return defenseCost; }


// Normal Methods
	public void doDamage (Player a, Player d, Deck p) { doDamageStrategy.doDamage(this,a,d,p) ; }
	public void doDamageResolution (Player a, Player d, Deck p) { doDamageResolutionStrategy.doDamageResolution(this,a,d,p); }
	public void play (Player a, Player d, Deck p) { playStrategy.play(this,a,d,p); } 
	public boolean isValid (Player a, Player d, Deck p) { return isValidStrategy.isValid(this,a,d,p); }
	public boolean canUse (Player a, Player d, Deck p, Card c) { return canUseStrategy.canUse(this,a,d,p,c); }
	
	public boolean isAttack () {return cardType == ATTACK;}
	public boolean isDefense () {return cardType == DEFENSE;}
	public boolean isCard () {return true;}
	
	public  String getMoveString(){		
		if (cardMove == BLOCK) { return "Block";}
		if (cardMove == MISC) { return "Misc";}
		if (cardMove == MOVE) { return "Move";}
		if (cardMove == KICK) { return "Kick";}
		if (cardMove == PUNCH) { return "Punch";}
		if (cardMove == SPECIAL) { return "Special";}
		return "";
	}
	
// Standard Card operators, getters & setters
	public int getCost() {
		if (cost>0) {
			return cost;
		} else {
			return 0;
		}
	}
	public int getStun() {return stun;}
	public int getDamage() {return damage;}
	public void setCost(int i) {cost=i; if (!fixedCostSet) { fixedCost = i; fixedCostSet = true; } }
	public void resetCost() {cost = fixedCost;}
	public void setStun(int i) {stun=i;}
	public void setDamage(int i) {damage=i;}
	public Angle getAngle() {return cardAngles;}
	public void setAngle(Angle a) {
		// System.out.println ("Card: " + this.getName() +" = " + a);
		cardAngles = a;
	}

	public int getCount() { return played; }
	
	public void addCost(int i) { cost += i; if (cost<0) { cost=0; } }
	public void addStun(int i) { stun += i; if (stun<0) { stun=0; }  }
	public void addDamage(int i) { damage += i; if (damage<0) { damage=0; } }
	public void reduceDamage(int i) { damage -= i;  }
	public void mulDamage(int i) { damage *= i; }
		
	public String toString () {
		return(this.getName() + " " + this.getAngle().getAngle() + " C:" + this.getCost() + " S:" + this.getStun() + " D:" + this.getDamage());
	}

			
	public String getCard () {
		return(this.getName() + " " + this.getAngle().getAngle() + " C:" + this.getCost() + " S:" + this.getStun() + " D:" + this.getDamage());
	}
	
	public Image getImage() {
		
		java.net.URL imageURL = ArcadeFighter.class.getResource(cardImageName);
		if (imageURL != null) {
			return new ImageIcon(imageURL).getImage();
		}
		return null;
	}

	public void playCount() { played ++; }
	public Card () {;}

	public Card (String name, int type, int move, Angle angle, int cost, int stun, int damage,
	 String imageName, String specialText, PlayStrategy ps, IsValidStrategy ivs, DamageStrategy ds, DamageResolutionStrategy drs, CanUseStrategy cus)
	{
		this.name = name;
		cardType = type;
		cardMove = move;
		cardAngles = angle;
		this.cost = cost;
		fixedCost = cost; fixedCostSet = true;
		this.stun = stun;
		this.damage = damage;
		cardImageName = imageName;
		special = specialText;
		
		playStrategy = ps;
		isValidStrategy = ivs;
		doDamageStrategy = ds;
		doDamageResolutionStrategy = drs;
		canUseStrategy = cus;

	}


// this is needed even though we've implemented the Clonable interface	
	public Object clone() { 
		try {
			return super.clone(); 
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Not Supported: " + e);
        }
		return null;
	}

}

class none extends Card  {

	public none() {
		name = "none";
		cardType = 0;
		cardMove = 0;
		cardAngles = new Angle(false,false,false);
		cost = 0; fixedCost = 0; fixedCostSet = true;
		stun = 0;
		damage = 0;
	}

	public boolean isCard () {return false;}
	public boolean isValid (Player a, Player d, Deck p) { return true; }
	public void play(Player a, Player d, Deck p) {;}
	public void doDamage(Player a, Player d, Deck p) {;}
	public void doDamageResolution(Player a, Player d, Deck p) {;}
}
