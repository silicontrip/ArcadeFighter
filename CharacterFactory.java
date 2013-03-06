import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import org.xml.sax.*;
import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.beans.*;


public class CharacterFactory {

Character[] allCharacters;

public Character[] newCharacter() {
	return allCharacters;
}

public CharacterFactory() {

	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			URL xmlURL = ArcadeFighter.class.getResource("Character.xml");
	
			if (xmlURL == null) { 
				System.out.println("CharacterFactory: xmlURL is null"); 
			} else {
		
				XMLDecoder d = new XMLDecoder(
							new BufferedInputStream(
								xmlURL.openStream()));
				allCharacters  = (Character[]) d.readObject();
				// System.out.println(allCharacters);
				d.close();
			}
			
		} catch (ParserConfigurationException pce) {
			System.out.println("CharacterFactory: Parser Configuration Error " + pce);
		} catch (IOException ioe) {
			System.out.println("CharacterFactory: IO Error " + ioe);
		}
	}

}