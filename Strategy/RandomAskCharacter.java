import java.util.*;


public class RandomAskCharacter implements AskCharacterStrategy {

private static final Random rand = new java.util.Random(java.lang.System.currentTimeMillis());



	public Character askCharacter (String s, Character[] c) {
		
		int choice;
		do {
			choice = rand.nextInt(c.length);
		} while (c[choice].getName().equalsIgnoreCase(s));
		
		return c[choice];
	}

}

