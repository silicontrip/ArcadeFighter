class DuplicateCoverDiscardDefense implements AskDiscardDefenseStrategy {

	public Card askDiscardDefense (Deck d) { 

	int high=4;
	int medium=4;
	int low=4;

		for(int i=0; i < d.size(); i++ ) {

			int angles=0;

			if (d.getCard(i).getAngle().isHigh()) { angles++; }
			if (d.getCard(i).getAngle().isMedium()) { angles++; }
			if (d.getCard(i).getAngle().isLow()) { angles++; }
			
			if (angles == 0) {
				// WTF???
				System.out.println("A card with no angles");
			}
			
			if (d.getCard(i).getAngle().isHigh() && (high > angles)) { high =  angles; }
			if (d.getCard(i).getAngle().isMedium() && (medium > angles)) { medium = angles; }
			if (d.getCard(i).getAngle().isLow() && (low > angles)) { low = angles; }
		}

	// System.out.println ("High: " + high  + " medium: " + medium + " low: " + low);

		// dont discard if we have the 3 angles already covered
	//	if (high>1 || medium>1 || low>1)  {

		for(int i=0; i < d.size(); i++ ) {
			int cHigh = 4;
			int cMedium = 4;
			int cLow = 4;

			for(int j=0; j < d.size(); j++ ) {

				if (j!=i) {
					int angles=0;
					if (d.getCard(j).getAngle().isHigh()) { angles++; }
					if (d.getCard(j).getAngle().isMedium()) { angles++; }
					if (d.getCard(j).getAngle().isLow()) { angles++; }
		
					if (d.getCard(j).getAngle().isHigh() && (cHigh > angles)) { cHigh =  angles; }
					if (d.getCard(j).getAngle().isMedium() && (cMedium > angles)) { cMedium = angles; }
					if (d.getCard(j).getAngle().isLow() && (cLow > angles)) { cLow = angles; }
				}
			}
					// removing this card doesn't affect the angle coverage.
			if ((cHigh == high) && (cMedium == medium) && (cLow == low)) {
				// don't throw away openstance if there is a move.
				if (!d.getCard(i).getName().equalsIgnoreCase("openstance") || !(high==1 || medium==1||low==1)) 
				{
						// System.out.println ("Discarding: " + d.getCard(i).getName());
						return d.getCard(i);
				}
			}
		}
	//	}
		return new none();
	}

}
