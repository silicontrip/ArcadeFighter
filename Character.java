import java.io.*;
import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;

public class Character implements Serializable  {

	public  String name;
	public  String style;
	
	public  int attack;
	public  int defense;
	public  int life;
	public  int energy;
	public int damage=0;
	public int stun=0;
	public int numberWin = 0;
	public int numberLose = 0;
	
	public String cardImageName;
	transient public ImageIcon cardImage;

	public int getAttack() { return attack; }
	public int getDefense() { return defense; }
	public int getLife() { return life; }
	public int getEnergy() { return energy; }
	public String getName() {return name; }
	public String getStyle() {return style; }
	public void win() { numberWin++; }
	public void lose() { numberLose++; }
	public int getWin() { return numberWin; }
	public int getLose() { return numberLose; }
	
	public Image getImage() { 

		if (cardImage == null) {
	
			java.net.URL imageURL = ArcadeFighter.class.getResource(cardImageName);
			if (imageURL != null) {
				cardImage =   new ImageIcon(imageURL);
			}
		}
		return cardImage.getImage(); 
	}

	public void getDamage(Deck d) { 
		d.getTopAttack().addStun(stun); 
		d.getTopAttack().addDamage(damage); 
	}

	public Character (String name, String style, int attack, int defense, int life, int energy, int damage, int stun, String cardImageName)
	{
		this.name = name;
		this.style = style;
		this.attack = attack;
		this.defense = defense;
		this.life = life;
		this.energy = energy;
		this.damage = damage;
		this.stun = stun;
		this.cardImageName = cardImageName;
	}

	public Character () { ; }
	public void setName (String name) { this.name = name; }
	public void setStyle (String style) { this.style = style; }
	public void setAttack (int attack) { this.attack = attack; }
	public void setDefense (int defense) { this.defense = defense; }
	public void setLife (int life) { this.life = life; }
	public void setEnergy (int energy) { this.energy = energy; }
	public void setDamage (int damage) { this.damage = damage; }
	public int getDamage () { return damage; }
	public void setStun (int stun) { this.stun = stun; }
	public int getStun () { return stun; }

	public void setImageName (String cardImageName) { this.cardImageName = cardImageName; }
	public String getImageName () { return cardImageName; }

}