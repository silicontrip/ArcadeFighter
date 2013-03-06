import java.io.*;
import java.util.*;

public class Deck extends ArrayList implements Serializable {  // Decorator
	
	static Random rand;
	
	public Deck() {
		rand = new java.util.Random(java.lang.System.currentTimeMillis());
	}
	
	
	public Card getCard (int c) {
		return (Card) super.get(c);
	}
	
	public Card removeTop () {
		// System.out.println("removeTop: this.size() =" + this.size());
		return (Card)this.remove(this.size()-1);
	}

	public Card getTop () {
		return (Card)this.get(this.size()-1);
	}


	public Card remove (Card c) {

		int ind = this.indexOf(c);
		if (ind >= 0) { return (Card)this.remove(ind); }
			// if it cannot find the card
			
		//System.out.println("Remove: cannot find reference");

		for (int sea=0; sea <this.size(); sea++) {
			if (c.getName().equalsIgnoreCase(this.getCard(sea).getName())) {
		// 	System.out.println("Removing by name " + c.getName());
				return (Card)this.remove(sea);
			}
		}
	// AAAARRRRRGGGGHHH 
		System.out.println("Remove: cannot find card");
		return new none();
	}

public Card getTopDefense() {

	int c = this.size()-1;
	if (this.getCard(c).isDefense()) { return this.getCard(c); }
	return new none();
}

public Card getTopAttack() { // same as getTopAttack(1)
	for (int c=this.size()-1; c>=0; c--) {
		if (this.getCard(c).isAttack()) { 
			return  this.getCard(c); 
		}
	}
	return new none();

}

public Card getTopAttack(int skip) {
	for (int c=this.size()-1; c>=0; c--) {
		if (this.getCard(c).isAttack()) {
			skip --;
			if (skip == 0) {
				return this.getCard(c); 
			}
		}
	}
	return new none();
}

public void shuffle () {
//  shuffle multiple times...
	for (int times = 0; times < 10; times ++ ) {
		for (int c=0; c<this.size(); c++) {
			Card temp;
			int swap = (rand.nextInt(this.size()));
			temp = (Card) this.get(c);
			this.set(c,this.get(swap));
			this.set(swap,temp);
		}
	}
}


}
