import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;


class Player implements Serializable {

	transient protected Interface userInterface;
	protected Character fighter;
	Deck attackStack;
	Deck defenseStack;
	Deck attackHand;
	Deck defenseHand;
	Deck attackDiscard;
	Deck defenseDiscard;
	int life;
	int energy;
	int nextEnergy;

public Player (Interface i, Deck as, Deck ds) {

	userInterface = i;
	
	attackStack = as;
	defenseStack = ds;

	attackHand = new Deck();
	attackDiscard = new Deck();
	defenseHand = new Deck();
	defenseDiscard = new Deck();

}

public Player (Deck as, Deck ds) {
	
	attackStack = as;
	defenseStack = ds;

	attackHand = new Deck();
	attackDiscard = new Deck();
	defenseHand = new Deck();
	defenseDiscard = new Deck();

}


public int getEnergy () { return energy;}
public int getLife () { return life;}
public int getAttack() { return fighter.getAttack(); }
public int getDefense() { return fighter.getDefense(); }
public void getDamage(Deck d) { fighter.getDamage(d); }
public String getName() { return fighter.getName(); }
public Deck getAttackStack() {return attackStack; }
public Deck getAttackHand() { return attackHand; }
public Deck getAttackDiscard() { return attackDiscard; }
public Deck getDefenseStack() { return defenseStack; }
public Deck getDefenseHand() { return defenseHand; }
public Deck getDefenseDiscard() { return defenseDiscard; }

// Encapsulates the interface
public Card askDiscardAttack(Deck d)  {return userInterface.askCard("Discard Attack", d);}
public Card askDiscardDefense(Deck d)  {return userInterface.askCard("Discard Defense", d);}

public Card askAttack(Player a, Player d, Deck p) { return userInterface.askAttack(a,d,p); }
public Card askDefense(Player a, Player d, Deck p) { return userInterface.askDefense(a,d,p); }
public boolean askBool(String s) { return userInterface.askBool(s);}
public Angle askAngle(Angle a) {return userInterface.askAngle(a);}
public Character askCharacter( Character[] c) { return userInterface.askCharacter(c); }
public Character askCharacter( String s, Character[] c) { return userInterface.askCharacter(c); }

public void print(String s) { userInterface.print(s); }
public void showCharacters(Player a, Player d) { userInterface.showCharacters(a,d); }
public void showDeck(Deck d) { userInterface.showDeck(d); }
public Interface getInterface() { return userInterface; }

public Character getCharacter() { return fighter; }
public Image getImage() { return fighter.getImage(); }

public void setEnergy(int e) { if (e>=0) {energy = e;} }

public void setFighter(Character c) { 
	fighter = c; 
	life = fighter.getLife();
	energy = fighter.getEnergy();
}


public void startHealth() {
	life = fighter.getLife();
	energy = fighter.getEnergy();
}


public void doDamage (int l, int e) {

	if (l>0) {
		life -= l;
	}
	if (e>0) {
		energy -= e;
	}
	if (energy < 0) {
		energy = 0;
	}
}

public boolean isDead () {	return (life<=0); }

public void startTurn () {	
	energy += nextEnergy;
}

public void addNextEnergy (int i) {
	nextEnergy += i;
}

public void resetNextEnergy () {
	nextEnergy = fighter.getEnergy();
}

public int getNextEnergy () {
	return nextEnergy ;
}


public void drawAttack() {
	draw(this.getAttack()-this.getAttackHand().size(),this.getAttackStack(),this.getAttackHand(),this.getAttackDiscard());
}

public void drawDefense() {

	draw(this.getDefense()-this.getDefenseHand().size(),this.getDefenseStack(),this.getDefenseHand(),this.getDefenseDiscard());
}


public void draw (int cards, Deck from, Deck to, Deck discard) {

	// this.showDeck(from);

	// System.out.println("draw: cards="+cards);

	for (int count=0;count<cards;count++) {

		if (from.size() == 0) {
			from.addAll(discard);
			discard.clear();
			from.shuffle();
		}
		to.add(from.removeTop());

	}

}


}
	
