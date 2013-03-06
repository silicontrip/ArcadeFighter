import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Gui extends Interface  {

	JFrame frame;
	JButton bNone;
	JButton bHigh;
	JButton bMedium;
	JButton bLow;
	JButton bYes;
	JButton bNo;

	HashMap cardImage;

	JLabel statusLabel;

	Font comicFont;

	JPanel bkgPanel;
	JPanel viewPanel;
	JPanel playedPanel;
	JPanel statusPanel;
	JPanel controlPanel;
	JPanel characterPanel;
	JPanel dialogPanel;
	JPanel handPanel;
	JPanel statuscontrolPanel;


	Deck displayHand;
	Deck displayPlayed;
	Player displayTop;
	Player displayBottom;
	
	Character[] displayCharacter;
	JButton[] characterButton;
	JButton[] handButton;

	// PlayedPanel playedPanel;
	// CharacterPanel characterPanel;
	// HandPanel handPanel;

	boolean waitCard;
	boolean waitAngle;
	boolean waitBool;
	boolean waitCharacter;
	
	Card stateCard;
	Angle stateAngle;
	boolean stateBool;
	Character stateCharacter;

	public String getName() { return "gui"; }
	
	public Gui() {

		
		frame = new JFrame();
		frame.setTitle("Arcade Fighter");

		cardImage = new HashMap();

		comicFont = new Font("Comic Sans MS",0,10);

		bNone = new JButton("None");
		bNone.setEnabled(false);
		bNone.addActionListener(new CardListener());
		bHigh = new JButton("High");
		bHigh.setEnabled(false);
		bHigh.addActionListener(new AngleListener());
		bMedium = new JButton("Medium");
		bMedium.setEnabled(false);
		bMedium.addActionListener(new AngleListener());

		bLow = new JButton("Low");
		bLow.setEnabled(false);
		bLow.addActionListener(new AngleListener());

		bYes = new JButton("Yes");
		bYes.setEnabled(false);
		bYes.addActionListener(new BoolListener());

		bNo = new JButton("No");
		bNo.setEnabled(false);
		bNo.addActionListener(new BoolListener());


		handButton = new JButton[6]; // this is the max attack and defense of all characters...
		statusLabel = new JLabel();
//		statusLabel.setBorder(BorderFactory.createTitledBorder("Status"));

		bkgPanel = new JPanel();
		bkgPanel.setLayout(new BoxLayout(bkgPanel, BoxLayout.Y_AXIS));
		playedPanel = new JPanel();
		//playedPanel.setMinimumSize(new Dimension(150,210));
		statusPanel = new JPanel();
		controlPanel = new JPanel();
		viewPanel = new JPanel();


		characterPanel = new JPanel();
		characterPanel.setBorder(BorderFactory.createTitledBorder("Fighters"));

		//characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));

		characterPanel.setVisible(true);
		
		// characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
		dialogPanel = new JPanel();
		//dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
	
		handPanel = new JPanel();
		statuscontrolPanel = new JPanel();
		statuscontrolPanel.setLayout(new BoxLayout(statuscontrolPanel, BoxLayout.Y_AXIS));


		viewPanel.add(playedPanel);


		dialogPanel.add(bNone);
		dialogPanel.add(bHigh);
		dialogPanel.add(bMedium);
		dialogPanel.add(bLow);
		dialogPanel.add(bYes);
		dialogPanel.add(bNo);
		// dialogPanel.setBorder(BorderFactory.createTitledBorder("Control"));


		controlPanel.add(handPanel);

		statuscontrolPanel.add(statusLabel);
		statuscontrolPanel.add(dialogPanel);
		statuscontrolPanel.setBorder(BorderFactory.createTitledBorder("Status"));

	
		// statusPanel.add(characterPanel);
		// statusPanel.add(statuscontrolPanel);
		//statusPanel.add(statusLabel);
		//statusPanel.add(dialogPanel);

		viewPanel.setBorder(BorderFactory.createTitledBorder("History"));
		//bkgPanel.add(viewPanel);
		// statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));

		//bkgPanel.add(statusPanel);
		controlPanel.setBorder(BorderFactory.createTitledBorder("Hand"));

		// bkgPanel.add(controlPanel);
	
		// frame.getContentPane().add(bkgPanel);

		frame.getContentPane().add(BorderLayout.NORTH, viewPanel);
		frame.getContentPane().add(BorderLayout.WEST, characterPanel);
		frame.getContentPane().add(BorderLayout.CENTER, statuscontrolPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, controlPanel);
		//frame.getContentPane().add(BorderLayout.CENTER, statusLabel);


		frame.setSize(1150,768);
		frame.setVisible(true);
}

private void readln(String s) {
	String ln = "";
	
	System.out.println(s + " [enter]");
	
	try {
		BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		ln = is.readLine();
		// if (ln.length() == 0 ) return null;
	} catch (IOException e) {
			System.out.println("IOException: " + e);
	}
}

	Image getImage (Card c) {
		
		if (!cardImage.containsKey(c.getName())) {
			cardImage.put(c.getName(),c.getImage());
		}
		return (Image)cardImage.get(c.getName());
	}

public  Card askCard(String s, Deck d) { 

	Graphics g;
	String temp = new String();
	// JButton[] cardButton= new JButton[a.getAttackHand().size()];
	Image osg;
	ImageIcon[] icon = new ImageIcon[d.size()];
	
	// readln("start askCard");


	displayHand = d;
	
	if (displayHand.size()==0) { return new none(); }

	
	handPanel.removeAll();
	for (int i= 0; i< displayHand.size(); i++) {
		icon[i] = new ImageIcon(getImage(displayHand.getCard(i)));
		handButton[i] = new JButton(icon[i]);
		handButton[i].addActionListener(new CardListener());

		handPanel.add(handButton[i]);
	}
	
	//	frame.repaint();

//System.out.println("Number of cards: " + displayHand.size() );

	for (int i = 0; i < displayHand.size(); i++) {
		osg = handButton[i].createImage(150,210);
		g = osg.getGraphics();
		
	//	if (g != null) { System.out.println("g is not null ... yay!!!"); }
		 
	//	if (comicFont != null) { System.out.println("comicFont is not null ... phew!");}

		g.drawImage(getImage(displayHand.getCard(i)),0,0,handPanel);
				drawCard(displayHand.getCard(i),g);

		icon[i].setImage(osg);
		handButton[i].setIcon(icon[i]);
	}
	
	bNone.setEnabled(true);
	statusLabel.setText(s);
	waitCard = true;
	handPanel.validate();
	frame.repaint();
	while (waitCard) { 
		try {
			Thread.sleep(250); 
		} catch (InterruptedException ie) { ; }
	}

	bNone.setEnabled(false);
	// readln("end askCard");
	return stateCard;
	

}


void drawCard(Card c, Graphics g) {

		String temp = new String();
		String[] special;
		
		g.setFont(comicFont);
		g.drawString(c.getName(),40,24);

		if (c.isAttack()) { temp="Attack"; }
		if (c.isDefense()) { temp="Defense"; }
		g.drawString(temp,20,41);
		
		temp = c.getMoveString();
		g.drawString(temp,20,54);


		temp = "A: " + c.getAngle().getAngle();
		g.drawString(temp,20,68);
				
		temp = "Cost: " + c.getCost();
		g.drawString(temp,20,95);

		temp = "Stun: " + c.getStun();
		g.drawString(temp,20,108);

		temp = "Dam: " + c.getDamage();
		g.drawString(temp,20,121);
		
		special= c.getSpecial();
		if (special != null) {
			for (int i=0; i<special.length; i++) {
				temp = special[i];
				g.drawString(temp,19,140+i*11);
			}
		}
}

public  Card askDefense(Player a, Player d, Deck p) {

	Graphics g;
	String temp = new String();
	// JButton[] cardButton= new JButton[a.getAttackHand().size()];
	Image osg;
	ImageIcon[] icon = new ImageIcon[d.getDefenseHand().size()];
	
	// readln("start askDefense");

	//System.out.println("Cards: " + d.getDefenseHand().size());
	

	displayHand = d.getDefenseHand();
	
	handPanel.removeAll();
	for (int i= 0; i< displayHand.size(); i++) {
		//System.out.println("adding " + i + " " + displayHand.getCard(i).getName());

		icon[i] = new ImageIcon(getImage(displayHand.getCard(i)));
		handButton[i] = new JButton(icon[i]);
		handButton[i].addActionListener(new CardListener());
		if (!displayHand.getCard(i).isValid(a,d,p)) {
			handButton[i].setEnabled(false);
		}
		handPanel.add(handButton[i]);
	}
	
	//	frame.repaint();

//System.out.println("Number of cards: " + displayHand.size() );

	for (int i = 0; i < displayHand.size(); i++) {
			//System.out.println("image " + i);

		osg = handButton[i].createImage(150,210);
		g = osg.getGraphics();
		
	//	if (g != null) { System.out.println("g is not null ... yay!!!"); }
		 
	//	if (comicFont != null) { System.out.println("comicFont is not null ... phew!");}

		g.drawImage(getImage(displayHand.getCard(i)),0,0,handButton[i]);
		drawCard(displayHand.getCard(i),g);

		icon[i].setImage(osg);
		handButton[i].setIcon(icon[i]);
	}

	bNone.setEnabled(true);
	statusLabel.setText("Choose Your Defense");
	
	waitCard = true;
	handPanel.validate();
	frame.repaint();
	while (waitCard) { 
		try {
			Thread.sleep(250); 
		} catch (InterruptedException ie) { ; }
	}

	bNone.setEnabled(false);
// 	readln("end askDefense");
	return stateCard;
	
}
public  Card askAttack(Player a, Player d, Deck p) {


	Graphics g;
	String temp = new String();
	// JButton[] cardButton= new JButton[a.getAttackHand().size()];
	Image osg;
	ImageIcon[] icon = new ImageIcon[a.getAttackHand().size()];
	


	//readln("start askAttack");

	displayHand = a.getAttackHand();
	
	handPanel.removeAll();
	for (int i= 0; i< displayHand.size(); i++) {
		// System.out.println("adding card " + i );

		icon[i] = new ImageIcon(getImage(displayHand.getCard(i)));
		handButton[i] = new JButton(icon[i]);
		handButton[i].addActionListener(new CardListener());
		if (!displayHand.getCard(i).isValid(a,d,p)) {
			handButton[i].setEnabled(false);
		}
		handPanel.add(handButton[i]);
	}
	
		// frame.repaint();

//System.out.println("Number of cards: " + displayHand.size() );
		bNone.setEnabled(true);

	for (int i = 0; i < displayHand.size(); i++) {
		osg = handButton[i].createImage(150,210);
		g = osg.getGraphics();
		
		g.drawImage(getImage(displayHand.getCard(i)),0,0,handButton[i]);
		drawCard(displayHand.getCard(i),g);

		icon[i].setImage(osg);
		handButton[i].setIcon(icon[i]);
	}
	
	statusLabel.setText("Choose Your Attack");

	waitCard = true;
	handPanel.validate();
	frame.repaint();

	while (waitCard) { 
		try {
			Thread.sleep(250); 
		} catch (InterruptedException ie) { ; }
	}

	bNone.setEnabled(false);
//frame.repaint();
	//readln("end askattack");
	return stateCard;

}

void drawCharacter (Player p, Graphics g) {

		String temp = new String();

		g.setFont(comicFont);
		//g.drawImage(c[i].getImage(),0,0,characterButton[i]);
		temp= ""+p.getLife();
		g.drawString(temp,45,97);
		temp= ""+p.getEnergy();
		g.drawString(temp,88,97);
		temp="Attack: " + p.getAttack();
		g.drawString(temp,80,38);
		temp="Defense: " + p.getDefense();
		g.drawString(temp,80,48);

		g.drawString(p.getName(),80,16);

}
void drawCharacter (Character p, Graphics g) {

		String temp = new String();

		g.setFont(comicFont);
		//g.drawImage(c[i].getImage(),0,0,characterButton[i]);
		temp= ""+p.getLife();
		g.drawString(temp,45,97);
		temp= ""+p.getEnergy();
		g.drawString(temp,88,97);
		temp="Attack: " + p.getAttack();
		g.drawString(temp,80,38);
		temp="Defense: " + p.getDefense();
		g.drawString(temp,80,48);

		g.drawString(p.getName(),80,16);

}


public  Character askCharacter(Character[] c) {

	JFrame frame = new JFrame();
	JPanel characterPanel = new JPanel();
	Graphics g;
	String temp = new String();
	characterButton= new JButton[c.length];
	Image osg;
	ImageIcon[] icon = new ImageIcon[c.length];

	displayCharacter = c;
	
	for (int i = 0; i < c.length; i++) {
		icon[i]=new ImageIcon(c[i].getImage());
		characterButton[i] = new JButton(icon[i]);
		characterButton[i].addActionListener(new CharacterListener());
		characterPanel.add(characterButton[i]);
	}
	
	waitCharacter = true;
	
	//characterPanel.addActionListener(new CharacterListener());
	frame.getContentPane().add(characterPanel);
	frame.setSize(800,150);
	frame.setTitle("Choose Your Fighter");
	frame.setVisible(true);

	for (int i = 0; i < c.length; i++) {
		int x;
		osg = characterButton[i].createImage(150,105);
		g = osg.getGraphics();
		g.drawImage(c[i].getImage(),0,0,characterButton[i]);
		
		drawCharacter(c[i],g);
		
		icon[i].setImage(osg);
		characterButton[i].setIcon(icon[i]);
	}
	
	frame.repaint();
	
	while (waitCharacter) { 
		try {
			Thread.sleep(250); 
		} catch (InterruptedException ie) { ; }
	}

	// System.out.println (stateCharacter.getName() + " Selected");

	frame.setVisible(false);
//	frame.repaint();

	return stateCharacter;

}

public  boolean askBool(String s) {


	bYes.setEnabled(true);
	bNo.setEnabled(true);

// System.out.println("Ask bool");

	statusLabel.setText(s);
	waitBool = true;

	while (waitBool) { 
		try {
			Thread.sleep(250); 
		} catch (InterruptedException ie) { ; }
	}

	bYes.setEnabled(false);
	bNo.setEnabled(false);
	//readln("end askBool");
	return stateBool;

}

public  Angle askAngle(Angle a) {

//System.out.println("Ask angle");


	if ((a.isHigh() && a.isMedium()) || (a.isHigh() && a.isLow()) || (a.isMedium() && a.isLow())) {

		statusLabel.setText("Choose Angle");

		bHigh.setEnabled(a.isHigh());
		bMedium.setEnabled(a.isMedium());
		bLow.setEnabled(a.isLow());

		waitAngle = true;

		dialogPanel.validate();
		while (waitAngle) { 	
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}

		bHigh.setEnabled(false);
		bMedium.setEnabled(false);
		bLow.setEnabled(false);

		//readln("end askAngle");
		return stateAngle;
	} else {
		return a;
	}
}


public  void print(String s) { statusLabel.setText(s); statusPanel.validate(); frame.repaint(); }

public  void showCharacters(Player a, Player d) {

// readln("start showCharacters");


	displayTop=a; 
	displayBottom=d; 
	
	Graphics g;
	Image osg;

	String temp = new String();
	ImageIcon icon1 = new ImageIcon(a.getImage());
	ImageIcon icon2 = new ImageIcon(d.getImage());

	//System.out.println("A: " + a.getName() + " D: " + d.getName());

	JLabel characterLabel1= new JLabel(icon1);
	JLabel characterLabel2= new JLabel(icon2);
	
	characterPanel.removeAll();
	characterPanel.add(characterLabel1);
	characterPanel.add(characterLabel2);
	
//frame.repaint();
	
	osg = characterLabel1.createImage(150,105);
	g = osg.getGraphics();
	g.drawImage(a.getImage(),0,0,characterLabel1);
	drawCharacter(a,g);

	icon1.setImage(osg);
	characterLabel1.setIcon(icon1);
	
	osg = characterLabel2.createImage(150,105);
	g = osg.getGraphics();
	g.setFont(comicFont);
	g.drawImage(d.getImage(),0,0,characterLabel2);
	drawCharacter(d,g);
	icon2.setImage(osg);
	characterLabel2.setIcon(icon2);

	characterPanel.validate();
	frame.repaint(); 

	//readln("end showCharacters");

}

public void showDeck(Deck d) {

//readln("start showDeck");
displayPlayed = d; 
//frame.repaint(); 

	Graphics g;
	String temp = new String();
	JLabel[] cardLabel= new JLabel[d.size()];
	Image osg;
	ImageIcon[] icon = new ImageIcon[d.size()];
	
	// statusLabel.setText("Choose Your Defense");

	
	playedPanel.removeAll();
	for (int i= displayPlayed.size()-1; (i>=0)&&(i>displayPlayed.size()-7); i--) {
		if (displayPlayed.getCard(i).isCard()) {
			icon[i] = new ImageIcon(getImage(displayPlayed.getCard(i)));
			cardLabel[i] = new JLabel(icon[i]);
			playedPanel.add(cardLabel[i]);
		}
	}
//	frame.repaint();

//System.out.println("Number of cards: " + displayHand.size() );

	for (int i= displayPlayed.size()-1; (i>=0)&&(i>displayPlayed.size()-7); i--) {
		if (displayPlayed.getCard(i).isCard()) {

			osg = cardLabel[i].createImage(150,210);
			g = osg.getGraphics();
			g.drawImage(getImage(displayPlayed.getCard(i)),0,0,cardLabel[i]);
				drawCard(displayPlayed.getCard(i),g);

			icon[i].setImage(osg);
			cardLabel[i].setIcon(icon[i]);
		}
	}
	
	statusLabel.setText("Please Wait...");
	playedPanel.validate();
	frame.repaint();
	//readln("end showDeck");

}

class CharacterListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	
		if (waitCharacter) {
			// work out which card was clicked.
			// set The cardState to that card
			// where 0 = x / 150
			for (int i=0; i<displayCharacter.length; i++) {		
				if (e.getSource() == characterButton[i]) {
					stateCharacter=displayCharacter[i];
					waitCharacter = false;
				}
			}
		}
	}
}

class CardListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	
		if (waitCard) {
			// work out which card was clicked.
			// set The cardState to that card
			for (int i=0; i<displayHand.size(); i++) {		
				if (e.getSource() == handButton[i]) {
					stateCard=displayHand.getCard(i);
					waitCard = false;
				}
			}
			if (e.getSource() == bNone) {
				stateCard = new none();
				waitCard = false;
			}
		}
	}
}

class AngleListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (waitAngle) {
			if (e.getSource()==bHigh) { stateAngle=new Angle(true,false,false); waitAngle = false; }
			if (e.getSource()==bMedium) { stateAngle=new Angle(false,true,false); waitAngle = false; }
			if (e.getSource()==bLow) { stateAngle=new Angle(false,false,true); waitAngle = false; }
		}
	}
}

class BoolListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (waitBool) {
			if (e.getSource()==bYes) { stateBool=true; waitBool = false; }
			if (e.getSource()==bNo) { stateBool=false; waitBool = false; }
		}
	}
}

}
