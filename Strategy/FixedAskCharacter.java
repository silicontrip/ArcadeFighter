public class FixedAskCharacter implements AskCharacterStrategy {

	String chooseName;
	
	public FixedAskCharacter (String name) {
		chooseName = name;
	}

	
	public Character askCharacter(String s, Character[] c) {

		for (int i=0; i < c.length; i++) {
			if (c[i].getName().equalsIgnoreCase(chooseName)) {
				return c[i];
			}
		}
	
		return c[0];
	}
}
