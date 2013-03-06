import java.util.*;

public class ArcadeFighter {

	Player attacker;
	Player defender;
	Random rand;
	Deck played;
	Deck played_swap;
	int state=2;
	int turns=0;
	int maxTurns=100;

	public final int ATTACK = 2;
	public final int DISCARD = 3;
	public final int DEFENSE = 4;
	public final int DAMAGE = 5;
	public final int WINNER = 6;

	public ArcadeFighter(Player p1, Player p2) {

		played = new Deck();
		played_swap = new Deck();

		attacker = p1;
		defender = p2;
	}

	public Player getWinner () {
		if (state == WINNER) {
			return attacker;
		}
		return null;
	}
	public Player getLoser () {
		if (state == WINNER) {
			return defender;
		}
		return null;
	}


	public Deck getPlayed() {
		return played;
	}

	public Deck getPlayedSwap() {
		return played_swap;
	}

	public int getTurns() {
		return turns;
	}
		

	public void swapPlayers () {
		
		Player ptemp;
		Deck dtemp;

		ptemp = attacker;
		attacker = defender;
		defender = ptemp;
		
		played.add(new none());
	
		dtemp = played_swap;
		played_swap = played;
		played = dtemp;
	
		attacker.startTurn();
		defender.resetNextEnergy();
		
		attacker.drawAttack();
		defender.drawDefense();

		turns++;

	//state is attack
		state = 2;
	}

	public void attack () {

		Card select;

	
		do {
			select = attacker.askAttack(attacker,defender,played);
		} while (!select.isValid(attacker,defender,played));

		if (select.isCard()) {
			select.play(attacker,defender,played); // choose high/medium/low
			attacker.getDamage(played);  // Add Ivor's special damage 

		// state is defense

			state = 4;
		} else {
			// state is discard card
			state = 3;
		} 

	}

	public void defend () {

		Card select;
	
		do {
			select = defender.askDefense(attacker,defender,played);
		} while (!select.isValid(attacker,defender,played));

		select.play(attacker,defender,played); // choose to pay 1 extra to retain
			//played.add(select.clone());

		// state is damage resolution
		state = 5;
	
	}

	public void damageResolution () {
	
		// The Attack is stored in the Attack Card.  
		// Because the defense card may use an Absolute damage reduction, so cannot be stored there.
		//	System.out.println ("1st " + this.played.getTopAttack().getCard());

	
	// System.out.println ("2nd " + this.played.getTopAttack().getCard());
	
	// this is not needed since we started with the attack card anyway.
	// doDamage in the Attack card class currently does nothing, but who knows???
	
		this.played.getTopAttack().doDamage(attacker,defender,played);
	
	//System.out.println ("3rd " + this.played.getTopAttack().getCard());

		this.played.getTopDefense().doDamage(attacker,defender,played);
		
	//System.out.println ("4th " + this.played.getTopAttack().getCard());

		attacker.showDeck(played);
		this.played.getTopAttack().doDamageResolution(attacker,defender,played);
	//Open stance reduces ALL costs to zero.
		this.played.getTopDefense().doDamageResolution(attacker,defender,played);

	//System.out.println (this.played.getTopAttack().getCard());

		attacker.doDamage(0,played.getTopAttack().getCost());
		defender.doDamage(played.getTopAttack().getDamage(),played.getTopAttack().getStun());	
		
		if (defender.isDead()) {
		// state is winner
			state = 6 ;
		} else { 
		// state is attack
			state = 2;
		}

	}

	public void discard () {

		Card select;

		do {
			select = attacker.askDiscardAttack(attacker.getAttackHand());
			if (select.isCard()) {
				attacker.getAttackHand().remove(select);
				attacker.getAttackDiscard().add(select);
			}
		} while (select.isCard());
		

	//defender.i.println("Discard Defense Cards");
		do {
			select = defender.askDiscardDefense(defender.getDefenseHand());
			if (select.isCard()) {
				defender.getDefenseHand().remove(select);
				defender.getDefenseDiscard().add(select);
			}
		} while (select.isCard());

		//state is swap_players
		state = 1;
	}


	public  Player go () {

		while (state != 6) {
	
			if (state==1) { 
				swapPlayers();
			} else if ( state == 2) {
				attacker.showCharacters(attacker,defender);
				defender.showCharacters(attacker,defender);
				attacker.showDeck(played);
				attack();
			} else if ( state == 3) {
				discard();
			} else if ( state == 4) {
				defender.showDeck(played);
				defend();
			} else if ( state == 5) {
				attacker.showDeck(played);  // Needed for Combo

				damageResolution();
				defender.showDeck(played);

			}
			
			if (turns > maxTurns) {
				System.out.println("turns: " + turns);
				maxTurns = turns;
				}
			
		}
	
	// winner !
		attacker.print(attacker.getName() + " defeats " + defender.getName());  
		defender.print(attacker.getName() + " defeats " + defender.getName());  

	// System.out.println("turns: " + turns);
	
	
		return attacker;

	}

}
