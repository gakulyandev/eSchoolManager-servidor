/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.eschoolmanager.server.gestors.GestorExcepcions;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els professors de l'escola
 */
@Entity
@Table(name="Professor")
@DiscriminatorValue("PR")
public class Professor extends Empleat {
	
	private List<Sessio> sessions;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Professor() {
	}
	
	/**
     * Constructor parametritzat: construeix un nou empleat amb els paràmetres especificats
     * @param dni identificador del professor
     * @param nom del professor
     * @param cognoms del professor
     * @param dataNaixement del professor
     * @param telefon de contacte del professor
     * @param email de contacte del professor
     * @param adreca del professor
	 * @throws GestorExcepcions 
     */
	public Professor(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) throws GestorExcepcions {
		super(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		this.setSessions(new ArrayList<Sessio>());
	}
	
	/**
	 * Llista les sessions realitzades pel professor
	 * @return sessions realitzades pel professor
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="professor")
	public List<Sessio> getSessions() {
		return this.sessions;
	}
	
	/**
	 * Actualitza les sessions realitzades pel professor (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param sessions actualitzades del professor
	 */
	public void setSessions(List<Sessio> sessions) {
		this.sessions = sessions;
	}
	
	/**
	 * Assigna una sessió
	 * @param sessió a assignar
	 */
	public void assignaSessio(Sessio sessio) {
		if(!sessions.contains(sessio)) {
			sessions.add(sessio);
		}
	}
	
	/**
	 * Indica si l'empleat té o no elements relacionats
	 * @return true o false segons si té o no elements relacionats
	 */
	public Boolean isBuit() {
		return (this.sessions.isEmpty());
	}
}
