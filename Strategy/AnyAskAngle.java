
public class AnyAskAngle implements AskAngleStrategy {

	public  Angle askAngle (Player a, Player d, Deck p) {
		return new Angle(true,true,true);			
	}
}

