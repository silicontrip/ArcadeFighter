import java.io.*;
import java.net.*;


public class NetworkClient {

	final static int PORT = 5555;

	public static void main(String[] args) {
		
		Interface intf = null;
		Socket comm;
		
		if (args.length < 2) { 
			System.out.println("Requires an interface and destination address.  Where interface is gui, cli or computer"); 
		} else {
		
			
			
			try {
			
				comm = new Socket(args[1],PORT);
				ObjectInputStream ois = new ObjectInputStream(comm.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(comm.getOutputStream());

				InterfaceFactory intff = new InterfaceFactory();
		
		
				intf = intff.newInterface(args[0]);

				if (intf != null ) {
				
				while (true) {
					
					try {
						String message = (String) ois.readObject();
										
						// System.out.println("Message: " + message);
										
						if (message.equalsIgnoreCase("askCard")) {
							Card c;
							String s = (String) ois.readObject();
							Deck d = (Deck) ois.readObject();
						
							c = intf.askCard(s,d);
						
							oos.writeObject(c);
						}
					if (message.equalsIgnoreCase("askAttack")) {
						Card c;
						Player attacker = (Player) ois.readObject();
						//System.out.println("Attack Cards: " + attacker.getAttackHand().size());
						Player defender = (Player) ois.readObject();
						Deck played = (Deck) ois.readObject();
						
						c = intf.askAttack(attacker,defender,played);
						
						oos.writeObject(c);
					}
					if (message.equalsIgnoreCase("askDefense")) {
						Card c;
						Player attacker = (Player) ois.readObject();
						Player defender = (Player) ois.readObject();
						Deck played = (Deck) ois.readObject();
						
						c = intf.askDefense(attacker,defender,played);
						
						oos.writeObject(c);
					}
					if (message.equalsIgnoreCase("askBool")) {
						boolean b;
						String s = (String) ois.readObject();
								
						b =  intf.askBool(s);
						
						oos.writeObject(new Boolean(b));
					}
					if (message.equalsIgnoreCase("askAngle")) {
						Angle ra;
						
						Angle aa = (Angle) ois.readObject();
						
						ra = intf.askAngle(aa);
						
						oos.writeObject(ra);
					}
					if (message.equalsIgnoreCase("askCharacter")) {
						Character rc;
						
						Character[] ac = (Character[]) ois.readObject();
						
						rc = intf.askCharacter(ac);
						
						oos.writeObject(rc);
					}
					if (message.equalsIgnoreCase("print")) {
						String s = (String) ois.readObject();
								
						intf.print(s);
					}
					if (message.equalsIgnoreCase("showCharacters")) {
						
						Player a = (Player) ois.readObject();
						Player d = (Player) ois.readObject();

						intf.showCharacters(a,d);
					}
					if (message.equalsIgnoreCase("showDeck")) {
						
						Deck d = (Deck) ois.readObject();

						intf.showDeck(d);
					}
					
					} catch (ClassNotFoundException cnfe) {
						System.out.println ("Class not found (network protocol error) occured: " + cnfe);
					}
				
				}
				} else {
					System.out.println("An invalid interface was chosen");
				}
				
			} catch (IOException ioe) {
					System.out.println ("main IOException occured: " + ioe);
			}

		}

	}

}