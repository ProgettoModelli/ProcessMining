/***********************************************************
 * This software is part of the ProM package * http://www.processmining.org/ * *
 * Copyright (c) 2003-2006 TU/e Eindhoven * and is licensed under the * Common
 * Public License, Version 1.0 * by Eindhoven University of Technology *
 * Department of Information Systems * http://is.tm.tue.nl * *
 **********************************************************/

/* Generated By:JavaCC: Do not edit this line. TpnParser.java */
package org.processmining.plugins.petrinet.importing.tpn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.elements.Arc;
import org.processmining.models.graphbased.directed.petrinet.elements.Place;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.semantics.petrinet.Marking;

public class TpnParser implements TpnParserConstants {

	private final Map<String, Place> placeMap = new HashMap<String, Place>();
	private final Marking state = new Marking();

	public Marking getState() {
		return state;
	}

	private Place addPlace(Petrinet net, String placeName, int init) {
		Place p = placeMap.get(placeName);

		if (p == null) {
			p = net.addPlace(placeName);
			placeMap.put(placeName, p);
		}
		if (init > 0) {
			state.add(p, init);
		}
		// for (int i = 0; i < init; i++) {
		// //TODO: Not supported Yet
		// p.addToken(new org.processmining.framework.models.petrinet.Token());
		// }
		return p;
	}

	private void addTrans(Petrinet net, String name, String label, List<String> in, List<String> out) {
		// create transition
		Transition t = net.addTransition(label.equals("") ? name : label);
		t.getAttributeMap().put("TPN Name:", name);

		// create ingoing arcs
		for (int i = 0; i < in.size(); i++) {
			String placeName = in.get(i);
			Place p = addPlace(net, placeName, 0);
			Arc a = net.getArc(p, t);
			if (a == null) {
				net.addArc(p, t, 1);
			} else {
				a.setWeight(a.getWeight() + 1);
			}
		}

		// create outgoing arcs
		for (int i = 0; i < out.size(); i++) {
			String placeName = out.get(i);

			Place p = addPlace(net, placeName, 0);

			Arc a = net.getArc(t, p);
			if (a == null) {
				net.addArc(t, p, 1);
			} else {
				a.setWeight(a.getWeight() + 1);
			}
		}
	}

	private String stripQuotes(String s) {
		return (s.length() >= 2) && (s.charAt(0) == '"') && (s.charAt(s.length() - 1) == '"') ? s.substring(1, s
				.length() - 1) : s;
	}

	final public void start(Petrinet net) throws ParseException {
		label_1: while (true) {
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case 1 :
				case 2 :
					;
					break;
				default :
					jj_la1[0] = jj_gen;
					break label_1;
			}
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case 1 :
					Place(net);
					break;
				case 2 :
					Trans(net);
					break;
				default :
					jj_la1[1] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
			}
		}
		jj_consume_token(0);
		return;
	}

	final public void Place(Petrinet net) throws ParseException {
		Token placeName, init = null;
		jj_consume_token(1);
		placeName = jj_consume_token(NAME);
		switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case 3 :
				jj_consume_token(3);
				init = jj_consume_token(NUMBER);
				break;
			default :
				jj_la1[2] = jj_gen;
				;
		}
		jj_consume_token(8);
		addPlace(net, stripQuotes(placeName.toString()), init == null ? 0 : Integer.parseInt(init.toString()));
	}

	final public void Trans(Petrinet net) throws ParseException {
		Token transName, label = null;
		List<String> in = new ArrayList<String>(), out = new ArrayList<String>();
		jj_consume_token(2);
		transName = jj_consume_token(NAME);
		switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case 7 :
				jj_consume_token(7);
				label = jj_consume_token(NAME);
				break;
			default :
				jj_la1[3] = jj_gen;
				;
		}
		switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case 5 :
				jj_consume_token(5);
				in = Arcs();
				break;
			default :
				jj_la1[4] = jj_gen;
				;
		}
		switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case 6 :
				jj_consume_token(6);
				out = Arcs();
				break;
			default :
				jj_la1[5] = jj_gen;
				;
		}
		jj_consume_token(8);
		addTrans(net, stripQuotes(transName.toString()), stripQuotes(label == null ? "" : label.toString()), in, out);
	}

	final public List<String> Arcs() throws ParseException {
		ArrayList<String> arcs = new ArrayList<String>();
		String s;
		label_2: while (true) {
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case NAME :
					;
					break;
				default :
					jj_la1[6] = jj_gen;
					break label_2;
			}
			s = Arc();
			arcs.add(s);
		}
		return arcs;
	}

	final public String Arc() throws ParseException {
		Token name;
		name = jj_consume_token(NAME);
		switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case NUMBER :
				jj_consume_token(NUMBER);
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
					case 4 :
					case NUMBER :
						switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case NUMBER :
								jj_consume_token(NUMBER);
								break;
							case 4 :
								jj_consume_token(4);
								break;
							default :
								jj_la1[7] = jj_gen;
								jj_consume_token(-1);
								throw new ParseException();
						}
						break;
					default :
						jj_la1[8] = jj_gen;
						;
				}
				break;
			default :
				jj_la1[9] = jj_gen;
				;
		}
		return stripQuotes(name.toString());
	}

	public TpnParserTokenManager token_source;
	SimpleCharStream jj_input_stream;
	public Token token, jj_nt;
	private int jj_ntk;
	private int jj_gen;
	final private int[] jj_la1 = new int[10];
	static private int[] jj_la1_0;
	static {
		jj_la1_0();
	}

	private static void jj_la1_0() {
		jj_la1_0 = new int[] { 0x6, 0x6, 0x8, 0x80, 0x20, 0x40, 0x200, 0x410, 0x410, 0x400, };
	}

	public TpnParser(java.io.InputStream stream) {
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new TpnParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 10; i++) {
			jj_la1[i] = -1;
		}
	}

	public void ReInit(java.io.InputStream stream) {
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 10; i++) {
			jj_la1[i] = -1;
		}
	}

	public TpnParser(java.io.Reader stream) {
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new TpnParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 10; i++) {
			jj_la1[i] = -1;
		}
	}

	public void ReInit(java.io.Reader stream) {
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 10; i++) {
			jj_la1[i] = -1;
		}
	}

	public TpnParser(TpnParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 10; i++) {
			jj_la1[i] = -1;
		}
	}

	public void ReInit(TpnParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 10; i++) {
			jj_la1[i] = -1;
		}
	}

	final private Token jj_consume_token(int kind) throws ParseException {
		Token oldToken;
		if ((oldToken = token).next != null) {
			token = token.next;
		} else {
			token = token.next = token_source.getNextToken();
		}
		jj_ntk = -1;
		if (token.kind == kind) {
			jj_gen++;
			return token;
		}
		token = oldToken;
		jj_kind = kind;
		throw generateParseException();
	}

	final public Token getNextToken() {
		if (token.next != null) {
			token = token.next;
		} else {
			token = token.next = token_source.getNextToken();
		}
		jj_ntk = -1;
		jj_gen++;
		return token;
	}

	final public Token getToken(int index) {
		Token t = token;
		for (int i = 0; i < index; i++) {
			if (t.next != null) {
				t = t.next;
			} else {
				t = t.next = token_source.getNextToken();
			}
		}
		return t;
	}

	final private int jj_ntk() {
		if ((jj_nt = token.next) == null) {
			return (jj_ntk = (token.next = token_source.getNextToken()).kind);
		} else {
			return (jj_ntk = jj_nt.kind);
		}
	}

	private final java.util.Vector<int[]> jj_expentries = new java.util.Vector<int[]>();
	private int[] jj_expentry;
	private int jj_kind = -1;

	public ParseException generateParseException() {
		jj_expentries.removeAllElements();
		boolean[] la1tokens = new boolean[15];
		for (int i = 0; i < 15; i++) {
			la1tokens[i] = false;
		}
		if (jj_kind >= 0) {
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 10; i++) {
			if (jj_la1[i] == jj_gen) {
				for (int j = 0; j < 32; j++) {
					if ((jj_la1_0[i] & (1 << j)) != 0) {
						la1tokens[j] = true;
					}
				}
			}
		}
		for (int i = 0; i < 15; i++) {
			if (la1tokens[i]) {
				jj_expentry = new int[1];
				jj_expentry[0] = i;
				jj_expentries.addElement(jj_expentry);
			}
		}
		int[][] exptokseq = new int[jj_expentries.size()][];
		for (int i = 0; i < jj_expentries.size(); i++) {
			exptokseq[i] = jj_expentries.elementAt(i);
		}
		return new ParseException(token, exptokseq, tokenImage);
	}

	final public void enable_tracing() {
	}

	final public void disable_tracing() {
	}

}