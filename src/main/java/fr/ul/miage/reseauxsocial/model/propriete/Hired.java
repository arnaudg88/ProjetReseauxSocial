package fr.ul.miage.reseauxsocial.model.propriete;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import fr.ul.miage.reseauxsocial.model.Propriete;

public class Hired extends Propriete {
	
	private static final Logger LOG = Logger.getLogger(Hired.class.getName());

	private Date valeur;
	private static final String[] TYPE_POSSIBLE = {};
	
	public Hired(String attribut, Date valeur) {
		super(attribut, Arrays.asList(TYPE_POSSIBLE));
		this.valeur = valeur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valeur == null) ? 0 : valeur.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hired other = (Hired) obj;
		if (valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!valeur.equals(other.valeur))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date date = dateFormat.parse(dateFormat.format(valeur));
			return "Hired=" + dateFormat.format(date);
		} catch (ParseException e) {
			LOG.severe(e.getMessage());
		}
		return "Hired=" + valeur;
	}
}
