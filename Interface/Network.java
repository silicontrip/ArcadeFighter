import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;


class Network implements Interface {


	final int PORT = 5555;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	ServerSocket listen;
	Socket comm;

	public Network() {

		try {
		
		// System.out.println("Listen");
	
		
			listen = new ServerSocket(PORT);
		
			System.out.println("Listening on: " + listen.toString());
		// this will block :-(

		//System.out.println("accept");


			comm = listen.accept();
		
// System.out.println("new Object Output stream");
			oos = new ObjectOutputStream(comm.getOutputStream());
		
			if (oos == null) {
				System.out.println("OOS is null");
			}
		
		
		// 	System.out.println("new Object Input stream");
			ois = new ObjectInputStream(comm.getInputStream());
			
			if (ois == null) {
				System.out.println("OIS is null");
			}

			
		} catch (IOException ioe) {
			System.out.println("network IO error " + ioe);
		}
			

	}

	public String getName() { return "network"; }
	public void print (String s) {

		try {
			oos.writeObject("print");
			oos.writeObject(s);
		} catch (IOException ioe) {
			System.out.println("p IO error " + ioe);
		}
	} 
	  
	public void showCharacters (Player a, Player d) { 

		try {
			oos.reset();
			oos.writeObject("showCharacters");
			oos.writeObject(a);
			oos.writeObject(d);
		} catch (IOException ioe) {
			System.out.println("sc IO error " + ioe);
		}
	}
	
	public void showDeck (Deck d) { 

		try {
			oos.reset();
			oos.writeObject("showDeck");
			oos.writeObject(d);
		} catch (IOException ioe) {
			System.out.println("IO error " + ioe);
		}

	}
	

	public Card askAttack(Player a, Player d, Deck p) {


		try {
		
			oos.reset();
			oos.writeObject("askAttack");
//			System.out.println("Attack Hand: " + a.getAttackHand().size());

			oos.writeObject(a);
			oos.writeObject(d);
			oos.writeObject(p);

			return (Card)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("aat IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("aat Class error " + cnfe);
		}

		return new none();
	}
		
	public Card askDefense(Player a, Player d, Deck p) {

		try {
			oos.reset();
			oos.writeObject("askDefense");
			oos.writeObject(a);
			oos.writeObject(d);
			oos.writeObject(p);

			return (Card)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("ad IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ad Class error " + cnfe);
		}
		return new none();

	}


	public Card askCard (String s, Deck d) {

		try {
			oos.reset();
			oos.writeObject("askCard");
			oos.writeObject(s);
			oos.writeObject(d);

			return (Card)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("aca IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("aca Class error " + cnfe);
		}
		return new none();

	}
	
	public Angle askAngle (Angle a) {

		try {
			oos.writeObject("askAngle");
			oos.writeObject(a);
	
			return (Angle)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("aa IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("aa Class error " + cnfe);
		}

		return new Angle(false,false,false);

	}
	
	public Character askCharacter ( Character[] c) {

		try {
		
			if (oos == null) { System.out.println("OOS is null"); }
		
			oos.writeObject("askCharacter");
			oos.writeObject(c);
		
			return (Character) ois.readObject();
		} catch (IOException ioe) {
			System.out.println("ac IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return null;

	}
	
	public boolean askBool (String s) {

		Boolean b;

		try {
			oos.writeObject("askBool");
			oos.writeObject(s);
		
		//System.out.println("Waiting for Boolean");

			b = (Boolean) ois.readObject();
		//System.out.println("Read Boolean");
			return b.booleanValue();
		} catch (IOException ioe) {
			System.out.println("ab IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		return false;
	}

}
