package fr.ul.miage.reseauxsocial.model;

import javafx.util.Pair;

public class Paire extends Pair<String, String>{

	public Paire(String key, String value) {
		super(key, value);
	}

	@Override
	public String toString() {
		return "["+this.getKey()+"-"+getValue()+"]";
	}

	
}
