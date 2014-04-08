package jess_test;

import jess.*;

public class Main {
	public static void main(String[] args) throws JessException {
		Rete r = new Rete();
		Sala s = new Sala();
		Sala s2 = new Sala();
		Sala s3 = new Sala();
		s.setTemperatura(100);
		s2.setTemperatura(100);
		s3.setTemperatura(100);
		r.add(s);
		r.add(s2);
		r.add(s3);
		r.batch("rules.clp");
		r.run();
		System.out.println(s.getTemperatura());
		System.out.println(s2.getTemperatura());
		System.out.println(s3.getTemperatura());
	}
}
