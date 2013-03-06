import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


interface Interface {

abstract Card askCard(String s, Deck d);
public abstract Card askAttack(Player a, Player d, Deck p);
public abstract Card askDefense(Player a, Player d, Deck p);
public abstract Character askCharacter(Character[] c);

public abstract boolean askBool(String s);
public abstract Angle askAngle(Angle a);
public abstract void print(String s);
public abstract String getName();
public abstract void showCharacters(Player a, Player d);
public abstract void showDeck(Deck d);

/*
public Card askDiscardAttack (Deck d) {	return askCard("Discard Attack", d); }
public Card askDiscardDefense (Deck d) { return askCard("Discard Defense", d); }
public Character askCharacter(String s, Character[] c) { return askCharacter(c); }
*/

}

class Cli implements Interface {

public String getName() { return "cli"; }

private String readln() {
	String ln = "";
	
	try {
		BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		ln = is.readLine();
		// if (ln.length() == 0 ) return null;
	} catch (IOException e) {
			System.out.println("IOException: " + e);
	}
	return ln;
}

private String getAngle (Angle a) {
		
	String  an = new String("");
	if (a.isHigh()) { an=an+"H"; }
	if (a.isMedium()) { an=an+"M"; }
	if (a.isLow()) { an=an+"L"; }
	return an;
}

public String getCard (Card c) {

	return(c.getName() + " " + getAngle(c.getAngle()) + " C:" + c.getCost() + " S:" + c.getStun() + " D:" + c.getDamage());
}




public void showCharacters (Player a, Player d) {

	//ArcadeFighter a = Main.getGame();
	System.out.print (a.getName() + " L:" + a.getLife() + " E:" + a.getEnergy() + ". ");
	System.out.println (d.getName() + " L:" + d.getLife() + " E:" + d.getEnergy());

}

public void print (String s) { 
	System.out.println (s);
}



public Card askCard (String s, Deck d) {

	String name;
	System.out.print (s + " "); 
	
	for (int i=0; i<d.size(); i++) {
		System.out.print (getCard(d.getCard(i)));
		System.out.print (" ");
	}
	System.out.print("?");
	name = readln();
	while (true) {
		if (name.equalsIgnoreCase("none")) { return new none(); }
		for (int i=0; i<d.size(); i++) {
			if (name.equalsIgnoreCase(d.getCard(i).getName())) { return d.getCard(i); }
		}
	}

}

	
public void showDeck (Deck d) {

// 4 has been determined a nice history size
	for (int c=d.size()-1; (c>=d.size()-4)&&(c>=0); c--) {
			System.out.print (getCard(d.getCard(c)) + "|");
	}
	System.out.println("");
}



public Card askAttack (Player a, Player d, Deck p) {

	String name;

	System.out.print ("Select Attack (or none): ");
	for (int i=0; i<a.getAttackHand().size(); i++) {
		if (!a.getAttackHand().getCard(i).isValid(a,d,p)) { System.out.print ("("); }
		System.out.print (getCard(a.getAttackHand().getCard(i)));
		if (!a.getAttackHand().getCard(i).isValid(a,d,p)) { System.out.print (")"); }

			System.out.print (" ");
	}
	System.out.print("?");
	name = readln();
	while (true) {
		if (name.equalsIgnoreCase("none")) { return new none(); }
		for (int i=0; i<a.getAttackHand().size(); i++) {
			if (name.equalsIgnoreCase(a.getAttackHand().getCard(i).getName())) { return a.getAttackHand().getCard(i); }
		}
	}


}

public Card askDefense (Player a, Player d, Deck p) {

	String name;

	System.out.print ("Select Defense (or none): ");

	for (int i=0; i<d.getDefenseHand().size(); i++) {
		if (!d.getDefenseHand().getCard(i).isValid(a,d,p)) { System.out.print ("("); }
		System.out.print (getCard(d.getDefenseHand().getCard(i)));
		if (!d.getDefenseHand().getCard(i).isValid(a,d,p)) { System.out.print (")"); }

			System.out.print (" ");
	}
	System.out.print("?");
	name = readln();
	while (true) {
		if (name.equalsIgnoreCase("none")) { return new none(); }
		for (int i=0; i<d.getDefenseHand().size(); i++) {
			if (name.equalsIgnoreCase(d.getDefenseHand().getCard(i).getName())) { return d.getDefenseHand().getCard(i); }
		}
	}


}

public Character askCharacter (Character[] c) 
{

	String name;
		while (true) {

		System.out.print ("Choose a Character ");
	
		for (int i=0; i<c.length; i++) {
			System.out.print (c[i].getName() + " A:" + c[i].getAttack() + " D:" + c[i].getDefense() + " L:" + c[i].getLife() + " E:" + c[i].getEnergy() + ". " );
			System.out.print (" ");
		}
		System.out.print("?");
		name = readln();
		for (int i=0; i<c.length; i++) {
			if (name.equalsIgnoreCase(c[i].getName())) { 
				// System.out.println (c[i].getName() + " Chosen");
				return c[i]; 
			}
		}
	}

}

public Angle askAngle (Angle a) {

	int possible=0;
	
	if (a.isHigh()) { possible++; }
	if (a.isMedium()) { possible++; }
	if (a.isLow()) { possible++; }

	if (possible > 1) {
		if (a.isHigh()) { System.out.print("High "); }
		if (a.isMedium()) { System.out.print("Medium "); }
		if (a.isLow()) { System.out.print("Low "); }
		System.out.print("?");
		char parse='0';
		do {
			String choice = readln();
			parse = choice.charAt(0);
			//System.out.println ("Entered: " + parse);
		} while ( (parse != 'h') && (parse != 'H') && (parse != 'm') && (parse != 'M') && (parse != 'l') && (parse != 'L'));
		
		//System.out.println ("chosen: " + parse);

		
		if ((parse == 'h') || (parse == 'H')) { return new Angle(true,false,false); }
		if ((parse == 'm') || (parse == 'M')) { return new Angle(false,true,false); }
		if ((parse == 'l') || (parse == 'L')) { return new Angle(false,false,true); }
		
	} else {
		return a;
	}
	return a;
}

public boolean askBool(String s) {

	System.out.print(s);
	System.out.print(" (Y/N)?");
	char parse='0';
	do {
		String choice = readln();
		parse = choice.charAt(0);
	} while ( (parse != 'Y') && (parse != 'y') && (parse != 'N') && (parse != 'n'));
	
	if ((parse == 'Y') || (parse == 'y')) { return true; }
	if ((parse == 'N') || (parse == 'n')) { return false; }

	return false;
}

}



