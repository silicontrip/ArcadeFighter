import java.io.*;
import java.beans.*;
import javax.xml.parsers.*;
import java.net.*;

public class CardFactory {

	public static final int ATTACK = 1;
	public static final int DEFENSE = 2;

	public static final int BLOCK = 1;
	public static final int MISC = 2;
	public static final int MOVE = 3;
	public static final int KICK = 4;
	public static final int PUNCH = 5;
	public static final int SPECIAL = 6;

	public Deck allCards;

	public CardFactory() {
	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			URL xmlURL = ArcadeFighter.class.getResource("Card.xml");
	
			if (xmlURL == null) { 
				System.out.println("CardFactory: xmlURL is null"); 
			} else {
				XMLDecoder d = new XMLDecoder(new BufferedInputStream(xmlURL.openStream()));
				allCards  = (Deck) d.readObject();
				// System.out.println(allCharacters);
				d.close();
			}
			
		} catch (ParserConfigurationException pce) {
			System.out.println("CardFactory: Parser Configuration Error " + pce);
		} catch (IOException ioe) {
			System.out.println("CardFactory: IO Error " + ioe);
		}

/*		
		Card c; 
		allCards = new Deck();
		String special;
		
		PlayStrategy ps; 
		IsValidStrategy ivs;
		DamageStrategy ds;
		DamageResolutionStrategy drs;
		CanUseStrategy cus;

		

		special = new String("You may pay an extra\n2 energy to avoid\ndiscarding this card");

		 ps = new DefenseRetainPlay(); 
		 ivs = new DefenseIsValid();
		 ds = new AbsoluteDamage();
		 drs = new NoneDamageResolution();
		 cus = new NoneCanUse();

		
		c = new Card ("BackStep",DEFENSE,MOVE, new Angle(false,true,false), 1,0,0,"defenseCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setRetainCost(2);
		allCards.add(c);
		
// DUCK
		
		special = new String("You may pay an extra\n2 energy to avoid\ndiscarding this card");
		
		 ps = new DefenseRetainPlay(); 
		 ivs = new DefenseIsValid();
		 ds = new AbsoluteDamage();
		 drs = new NoneDamageResolution();
		 cus = new NoneCanUse();

		
		c = new Card ("Duck",DEFENSE,MOVE, new Angle(true,false,false), 1,0,0,"defenseCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setRetainCost(2);
		allCards.add (c);

// JUMP

		special = new String("You may pay an extra\n2 energy to avoid\ndiscarding this card");
		 ps = new DefenseRetainPlay(); 
		 ivs = new DefenseIsValid();
		 ds = new AbsoluteDamage();
		 drs = new NoneDamageResolution();
		 cus = new NoneCanUse();

		c = new Card ("Jump",DEFENSE,MOVE, new Angle(false,false,true), 1,0,0,"defenseCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setRetainCost(2);
		allCards.add (c);
		

		special = new String("You may only use Ready\nagainst an attack that\nuses the same angle as\nthe opponent's previous\nattack");
		ps = new DefensePlay();
		ivs = new PreviousAngleIsValid();
		ds = new AbsoluteDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();

		c = new Card ("Ready",DEFENSE,MOVE, new Angle(true,true,true), 1,0,0,"defenseCardImage.png",special,ps,ivs,ds,drs,cus);
		allCards.add (c);

		ps = new DefensePlay();
		ivs = new DefenseIsValid();
		ds = new RelativeDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();


		c = new Card ("Guard",DEFENSE, BLOCK, new Angle(true,true,true), 2,-4,-1,"defenseCardImage.png",null,ps,ivs,ds,drs,cus);
		allCards.add (c);
		
		special = new String("Reduce the damage of\nthe attack by another\n1 if the angle of the\nattack is H");
		ps = new DefensePlay();
		ivs = new DefenseIsValid();
		ds = new BlockDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();

				
		c = new Card ("HighBlock",DEFENSE, BLOCK, new Angle(true,true,false), 0,-2,-1,"defenseCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setReduceAngle (new Angle(true,false,false));
		c.setReduceDamage(1);
		allCards.add (c);
		
		special = new String("Reduce the damage of\nthe attack by another\n1 if the angle of the\nattack is L");
		ps = new DefensePlay();
		ivs = new DefenseIsValid();
		ds = new BlockDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();

		c = new Card ("LowBlock",DEFENSE,BLOCK,new Angle(false,true,true),0,-2,-1,"defenseCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setReduceDamage(1);
		c.setReduceAngle(new Angle(false,false,true));
		allCards.add (c);

		special = new String("Immediately after you\nplay Open Stance. you\nmay play another defense\nagainst the same attack\nfor free");

		ps = new OpenStancePlay();
		ivs = new AnotherCardIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();

		c = new Card("OpenStance",DEFENSE,MISC,new Angle(true,true,true),0,0,0,"defenseCardImage.png",special,ps,ivs,ds,drs,cus);
		allCards.add (c);

		special = new String("Just before damage is\ndealt, the attacker may\nspend 3 extra energy to\ntriple the damage. Remove\nfrom game after use");
		
		ps = new RemovePlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new ComboDamageResolution();
		cus = new NoneCanUse();		

		c= new Card("Combo",ATTACK,SPECIAL,new Angle(false,false,true),2,0,2,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setAddCost(3);
		c.setMulDamage(3);
		allCards.add (c);

		special = new String("Remove from game after\nuse");

		ps = new RemovePlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();		


		c= new Card("SpecialAttack",ATTACK,SPECIAL,new Angle (false,true,false), 5,2,6,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		allCards.add (c);

		special = new String("Remove from game after\nuse");

		ps = new RemovePlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();		

		c = new Card("HeadButt",ATTACK,SPECIAL, new Angle(true,false,false),2,4,1,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		allCards.add (c);

		ps = new AttackPlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();		

		c= new Card("HeavyKick",ATTACK,KICK, new Angle(true,true,true),4,2,4,"attackCardImage.png",null,ps,ivs,ds,drs,cus);
		allCards.add (c);
		
		ps = new AttackPlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();		

		c= new Card("Kick",ATTACK,KICK,new Angle(true,true,true),3,1,3,"attackCardImage.png",null,ps,ivs,ds,drs,cus);
		allCards.add (c);
		
		ps = new AttackPlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();		
		
		c= new Card("LightKick",ATTACK,KICK,new Angle(true,true,true),2,0,2,"attackCardImage.png",null,ps,ivs,ds,drs,cus);
		allCards.add (c);
		
		ps = new AttackPlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();		

		
		c= new Card("HeavyPunch",ATTACK,PUNCH,new Angle(true,true,true),4,3,3,"attackCardImage.png",null,ps,ivs,ds,drs,cus);
		allCards.add (c);
		
		ps = new AttackPlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();		

		c= new Card("LightPunch",ATTACK,PUNCH,new Angle(true,true,true),2,1,1,"attackCardImage.png",null,ps,ivs,ds,drs,cus);
		allCards.add (c);
		
		ps = new AttackPlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoneCanUse();		
		
		c= new Card("Punch",ATTACK,PUNCH,new Angle(true,true,true),3,2,2,"attackCardImage.png",null,ps,ivs,ds,drs,cus);
		allCards.add (c);

		special = new String("Blocks may not be used\nagainst this attack. The\nopponent gains 2 less\nenergy next turn if they\nare damaged by this\nattack.");
		ps = new AttackPlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new ReduceDamageResolution();
		cus = new NoBlockCanUse();		

		c= new Card("KneeStrike",ATTACK,KICK,new Angle(false,true,true),3,0,2,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setNextEnergy(-2);
		allCards.add (c);		
		
		special = new String("Blocks may not be used\nagainst this attack. The\nopponent gains 2 less\nenergy next turn if they\nare damaged by this\nattack.");
		ps = new AttackPlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new ReduceDamageResolution();
		cus = new NoBlockCanUse();		

		c= new Card("OpenHandStrike",ATTACK,PUNCH,new Angle(true,true,false),3,1,1,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setNextEnergy(-2);
		allCards.add (c);		

		special = new String("Blocks may not be used\nagainst this attack.\nRemove from game after\nuse");
		ps = new RemovePlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoBlockCanUse();		

		c= new Card("Roundhouse",ATTACK,SPECIAL,new Angle(true,false,false),4,1,3,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		allCards.add (c);	

		ps = new RemovePlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new NoneDamageResolution();
		cus = new NoBlockCanUse();		

		special = new String("Blocks may not be used\nagainst this attack.\nRemove from game after\nuse");
		c= new Card("SlidingKick",ATTACK,SPECIAL,new Angle (false,false,true), 4,3,1,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		allCards.add (c);
	
		special = new String("Defenses cost 2 more\nagainst this attack.\nRemove from game after\nuse");
		ps = new RemovePlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new IncreaseCostDamageResolution();
		cus = new IncreasedDefenseCostCanUse();		

		c= new Card("Rush",ATTACK,SPECIAL,new Angle (false,true,false), 4,3,1,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		allCards.add (c);
		


		special = new String("Gain 2 energy if the\nattack damages the\nopponent. Remove from\ngame after use");
		ps = new RemovePlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new GainDamageResolution();
		cus = new NoneCanUse();		

		c= new Card("Sweep",ATTACK,SPECIAL,new Angle (false,false,true), 3,0,2,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setAddCost(-2);
		allCards.add (c);

		special = new String("Gain 2 energy if the\nattack damages the\nopponent. Remove from\ngame after use");
		ps = new RemovePlay();
		ivs = new AttackIsValid();
		ds = new NoneDamage();
		drs = new GainDamageResolution();
		cus = new NoneCanUse();		

		c= new Card("Uppercut",ATTACK,SPECIAL,new Angle (true,false,false), 4,1,3,"attackCardImage.png",special,ps,ivs,ds,drs,cus);
		c.setAddCost(-2);
		allCards.add (c);
		
		try {
			XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Card.xml")));
			e.writeObject(allCards);
			e.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("deckfactory: file not found " + fnfe);
		}
		*/
		
	}

	public Card newCard (String cardName) {
	
		for (int cards=0; cards < allCards.size(); cards++ )
		{
			if (cardName.equalsIgnoreCase(allCards.getCard(cards).getName())) {
				return allCards.getCard(cards);
			}
			
		}
	
		return null;
	}
	
	public Deck getAllCards() { return allCards; }

}
