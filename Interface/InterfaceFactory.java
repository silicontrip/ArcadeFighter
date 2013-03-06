public class InterfaceFactory {

	public Interface newInterface (String s) {

		AskCharacterStrategy ac = null;
		String[] ss = s.split(":");

		if (ss.length > 1) {
			ac = new FixedAskCharacter(ss[1]);
		} else {
			ac = new RandomAskCharacter();
		}

		if (ss[0].equalsIgnoreCase("cli")) {
		// steve says Capitalise the Class name ... Cli, Computer, Gui
			return new Cli();
		}
		if (ss[0].equalsIgnoreCase("random")) {
					
			AskDefenseStrategy ads = new RandomDefense();
			AskAttackStrategy aas = new RandomAttack();
			AskDiscardDefenseStrategy add = new RandomDiscardDefense();
			AskDiscardAttackStrategy ada = new RandomDiscardAttack();
			AskAngleStrategy aa = new AnyAskAngle();
			AskBoolStrategy ab = new RandomAskBool();
			return new Strategy(ads,aas,add,ada,aa,ac,ab,4);
		}
		if (ss[0].equalsIgnoreCase("ivor")) {
					
			AskDefenseStrategy ads = new BestDefense();
			AskAttackStrategy aas = new MostDamageAttack();
			AskDiscardDefenseStrategy add = new KeepZeroCostDefense();
			AskDiscardAttackStrategy ada = new LeastDamageDiscardAttack();
			AskAngleStrategy aa = new NotRepeatedAngle();
			ac = new FixedAskCharacter("ivor");
			AskBoolStrategy ab = new RandomAskBool();
			return new Strategy(ads,aas,add,ada,aa,ac,ab,0);
		}
		
		if (ss[0].equalsIgnoreCase("gui")) {
			return new Gui();
		}
		if (ss[0].equalsIgnoreCase("ain")) {
			AskDefenseStrategy ads = new BestDefense();
			AskAttackStrategy aas = new ProbabilityAngle();
			AskDiscardDefenseStrategy add = new DuplicateCoverDiscardDefense();
			AskDiscardAttackStrategy ada = new LeastDamageDiscardAttack();
			AskAngleStrategy aa = (AskAngleStrategy)aas;
			AskBoolStrategy ab = new RandomAskBool();
			
			return new Strategy(ads,aas,add,ada,aa,ac,ab,4);
		}
		if (ss[0].equalsIgnoreCase("cheat")) {
			AskDefenseStrategy ads = new BestDefense();
			AskAttackStrategy aas = new CheatingAngle();
			AskAngleStrategy aa = (AskAngleStrategy)aas;
			AskDiscardDefenseStrategy add = new DuplicateCoverDiscardDefense();
			AskDiscardAttackStrategy ada = new LeastDamageDiscardAttack();
			AskBoolStrategy ab = new RandomAskBool();
			
			return new Strategy(ads,aas,add,ada,aa,ac,ab,4);
		}
		if (ss[0].equalsIgnoreCase("ai")) {
			AskDefenseStrategy ads = new BestDefense();
			AskAttackStrategy aas = new MostDamageAttack();
			AskDiscardDefenseStrategy add = new DuplicateCoverDiscardDefense();
			AskDiscardAttackStrategy ada = new LeastDamageDiscardAttack();
			AskAngleStrategy aa = new NotRepeatedAngle();
			AskBoolStrategy ab = new RandomAskBool();
			
			return new Strategy(ads,aas,add,ada,aa,ac,ab,4);
		}
			
		if (ss[0].equalsIgnoreCase("network")) {
			return new Network();
		}
		
		System.out.println ("Unknown Interface: " + s );
		return null;
		
}

}