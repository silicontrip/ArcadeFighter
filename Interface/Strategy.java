import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/* AI player interface with selectable strategies */

public class Strategy implements Interface {

AskDefenseStrategy ads;
AskAttackStrategy aas;
AskDiscardDefenseStrategy add;
AskDiscardAttackStrategy ada;
AskAngleStrategy aa;
AskCharacterStrategy ac;
AskBoolStrategy ab;

Angle chosenAngle;
int energy;

private static final Random rand = new java.util.Random(java.lang.System.currentTimeMillis());

public Strategy (AskDefenseStrategy ads, AskAttackStrategy aas, AskDiscardDefenseStrategy add,
	AskDiscardAttackStrategy ada,  AskAngleStrategy aa,  AskCharacterStrategy ac, AskBoolStrategy ab, int energy) {
	
		this.ads = ads;
		this.aas = aas;
		this.add = add;
		this.ada = ada;
		this.aa = aa;
		this.ac = ac;
		this.ab = ab;
		this.energy = energy;

}

	public  Card askCard (String s, Deck d) {
		return askCard(d);
	}
	
	public static Card askCard (Deck d) {
		if (d.size() > 0) {
			int r = rand.nextInt(d.size());
			return (Card) d.get(r);
		}
		return new none();
	}


public  Card askAttack(Player a, Player d, Deck p) {
// AN attack is made up of an angle and a card

	chosenAngle = aa.askAngle(a,d,p);
	
// System.out.println("Chosen Angle: " + chosenAngle);
		
	if (a.getEnergy() < energy) { return new none(); }

	// get me a list of cards that are valid

	Deck ok = new Deck();

	for(int i=0; i < a.getAttackHand().size(); i++ ) {
		Card c = a.getAttackHand().getCard(i);
		if ((c.isValid(a,d,p)) &&
			(a.getEnergy() - c.getCost() >= energy) &&
			(c.getAngle().compare(chosenAngle))) {
			ok.add(c);
		}
	}

// System.out.println("size: " + ok.size());

	// get me a card using a particular card strategy
	if (ok.size()>0) {
		Card c = aas.askAttack(ok);
		//	System.out.println("Card: " + c);
			return c;

	} else {
		return new none();
	}
}
public  Card askDefense(Player a, Player d, Deck p) { return ads.askDefense(a,d,p); }
public  Character askCharacter( Character[] c) { return ac.askCharacter("",c); }
public  Character askCharacter(String s, Character[] c) { return ac.askCharacter(s,c); }
public  boolean askBool(String s) { return ab.askBool(s); }

public  Angle askAngle(Angle a) {

		int possible=0;
		Angle[] aa = new Angle[3];
			
		a = chosenAngle.intersect(a);
		 			
		if (a.isHigh()) { aa[possible] = new Angle(true,false,false); possible++; }
		if (a.isMedium()) { aa[possible] = new Angle(false,true,false); possible++ ; }
		if (a.isLow()) { aa[possible] = new Angle(false,false,true); possible++ ; }

		if (possible > 0) {
			int choice = rand.nextInt(possible);
			return aa[choice];
		}
				
		return new Angle(false,false,false);			

}

public  void print(String s) {;}
public  String getName() {return "ai:" + aas + ":" +ads; }
public  String toString() {return "ai:" + aas + ":" +ads; }
public  void showCharacters(Player a, Player d) {;}
public  void showDeck(Deck d){ ; }

public Card askDiscardAttack (Deck d) {	return ada.askDiscardAttack(d); }
public Card askDiscardDefense (Deck d) { return add.askDiscardDefense(d); }


}
