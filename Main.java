public class Main {

	private static ArcadeFighter game = null;

	public static void main(String[] args) {

		Interface[] intf = new Interface[2];

		// For statistics
		Player winner=null,loser=null;
		int totalTurns = 0;
		int if1=0;
		int if2=0;
		Deck stats;
		int hCount=0, hDamage=0;
		int mCount=0, mDamage=0;
		int lCount=0, lDamage=0;

		// All Fighters
		
		// Character[] availableFighters={ new Hiro(), new Ivor(), new Joe(), new May() } ;
		
		CharacterFactory cf = new CharacterFactory();
		Character[] availableFighters = cf.newCharacter();
		DeckFactory df = new DeckFactory();
				
		if (args.length < 2) { 
			System.out.println("Requires 2 interfaces.  Where interface is gui, cli or computer"); 
			System.out.println("Starting with default options:  gui computer ");  
	
		} 
		int playRounds = 1;

		if (args.length == 3) {
			try {
				playRounds = Integer.parseInt(args[2]);
			} catch (NumberFormatException nfe) { ; }
		}
		for (int rounds=0; rounds < playRounds; rounds++) {
	
			// Set up the starting Attack and Defense Decks

			Deck  attackDeckStack1 = df.newDeck("attack1");
			Deck  defenseDeckStack1 = df.newDeck("defense1");
			Deck  attackDeckStack2 = df.newDeck("attack2");
			Deck  defenseDeckStack2 = df.newDeck("defense2");



			InterfaceFactory inf = new InterfaceFactory();

			if (args.length < 2) {
			
				// ( default to gui selection )
				// hard code gui and Best AI Strategy so far
			
				intf[0] = inf.newInterface("gui");
				intf[1] = inf.newInterface("ai");
			
			} else {
			// scan the command line for interfaces
				for (int g=0; g < 2; g++) {
						intf[g] = inf.newInterface(args[g]);
				}
			}
			
			if ((intf[0]!=null) && (intf[1]!=null)) {
		
				Player player1 = new Player(intf[0],attackDeckStack1,defenseDeckStack1);
				Player player2 = new Player(intf[1],attackDeckStack2,defenseDeckStack2);
				player1.getAttackStack().shuffle();
				player1.getDefenseStack().shuffle();
				player2.getAttackStack().shuffle();
				player2.getDefenseStack().shuffle();
			
				player1.setFighter(player1.askCharacter("", availableFighters));
			//	do {
					player2.setFighter(player2.askCharacter(player1.getName(), availableFighters));
			//	} while (player2.getName().equalsIgnoreCase(player1.getName()));

				player1.drawAttack();
				player2.drawDefense();

				// the defender starts with 3 energy
				player2.setEnergy(3);
				player2.resetNextEnergy(); 

				game = new ArcadeFighter(player1,player2);		
				//game.setDumpStats(true);
				game.go();
				
				winner = game.getWinner();
				loser = game.getLoser();
				
				totalTurns += game.getTurns();
				
				winner.getCharacter().win();
				loser.getCharacter().lose();
				
				if (winner.getInterface() == intf[0]) {
					if1++;
				} else { 
					if2++;
				}
				
				stats = game.getPlayed();
				for (int decks=0; decks < 2; decks ++ ) {
					for (int cards = 0; cards < stats.size(); cards++)
					{
						if (stats.getCard(cards).isAttack()) {
							if (stats.getCard(cards).getAngle().isHigh()) { hCount ++; hDamage += stats.getCard(cards).getDamage(); }
							if (stats.getCard(cards).getAngle().isMedium()) { mCount ++; mDamage += stats.getCard(cards).getDamage(); }
							if (stats.getCard(cards).getAngle().isLow()) { lCount ++; lDamage += stats.getCard(cards).getDamage(); }
						}
					}
					stats = game.getPlayedSwap();
				}
				
			} else {
				System.out.println ("Valid interfaces are : (cli|gui|network|ai)");
				
			}
		}
		if (args.length>1) {
			System.out.println(winner.getName() + " defeats " + loser.getName());
			System.out.println ("Average turns per game: " + ((double)totalTurns) / playRounds);

			if (playRounds > 1) {
				System.out.println (intf[0] + " wins " + if1);
				System.out.println (intf[1] + " wins " + if2);
				System.out.println("");
				for (int stat=0; stat < availableFighters.length; stat++) {
					System.out.print(availableFighters[stat].getName() + " W:" + availableFighters[stat].getWin() + " L:" + availableFighters[stat].getLose());
					if (availableFighters[stat].getWin() + availableFighters[stat].getLose() > 0) {
						System.out.print( " (" + 100.0 * availableFighters[stat].getWin() / (availableFighters[stat].getWin()+availableFighters[stat].getLose()) + "%)" );
					}
					System.out.println("");
				}
			}
			System.out.println("");
			System.out.println ("High: " + hDamage + "/" + hCount + " (" + ((double)hDamage)/hCount + ")");
			System.out.println ("Medium: " + mDamage + "/" + mCount +" ("+ ((double)mDamage)/mCount + ")");
			System.out.println ("Low: " + lDamage + "/" + lCount +" ("+ ((double)lDamage)/lCount +")");
			
			System.out.println("");
	
			for (int cl=0; cl < df.getAllCards().size(); cl++) {
				System.out.println (df.getAllCards().getCard(cl).getName() + " - " + df.getAllCards().getCard(cl).getCount());
			}
		
		

		}
	}

}