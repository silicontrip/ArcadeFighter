import java.util.*;


public class RandomAskBool implements AskBoolStrategy {

private static final Random rand = new java.util.Random(java.lang.System.currentTimeMillis());


	public boolean askBool (String s) {
		return rand.nextInt(2)==1;
	}

}

