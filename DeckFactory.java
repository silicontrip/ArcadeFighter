import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import org.xml.sax.*;
import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.util.*;
import java.beans.*;


public class DeckFactory {

	HashMap deckMap;
	CardFactory cf;
	
	
	public Deck newDeck (String deckName) {
		
		Deck d = new Deck();
		d.addAll((Deck)deckMap.get(deckName));
		
		return d; 
	}
	
	public Deck getAllCards() { return cf.getAllCards(); }
	
	public void buildDeck (String deckName, HashMap hm) { 
		Deck preDeck = new Deck();
		ArrayList al = (ArrayList)hm.get(deckName);
				
		for (int cards=0; cards < al.size(); cards++) {
			preDeck.add(cf.newCard((String)al.get(cards)));
		}
		deckMap.put(deckName,preDeck);
	}
	
	public DeckFactory () {
	
		Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		HashMap hm =   new HashMap();
		deckMap = new HashMap();
		cf = new CardFactory();


		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			URL xmlURL = ArcadeFighter.class.getResource("Deck.xml");
	
			if (xmlURL == null) { System.out.println("Deck xmlURL is null"); }
	
			try {
				doc = db.parse(xmlURL.openStream());
			
				XMLDecoder d = new XMLDecoder(
					new BufferedInputStream(
						xmlURL.openStream()));
						
				hm  = (HashMap) d.readObject();
				
				buildDeck("attack1",hm);
				buildDeck("defense1",hm);
				buildDeck("attack2",hm);
				buildDeck("defense2",hm);

				
			} catch (IOException ioe) {
				System.out.println("Deck XML IO error");
			}  catch (SAXException saxe) {
				System.out.println("Deck XML SAX error");
			}
		
		} catch (ParserConfigurationException e) {
			System.out.println("Deck XML DocumentBuilder error");
		}

/*
	try {
		XMLEncoder e = new XMLEncoder(
                          new BufferedOutputStream(
                              new FileOutputStream("Test.xml")));
       e.writeObject(hm);
       e.close();
	} catch (FileNotFoundException fnfe) {
		System.out.println("deckfactory: file not found " + fnfe);
	}
*/	

	}


}
