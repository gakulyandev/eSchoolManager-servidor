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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els professors de l'escola
 */
@Entity
@Table(name="Professor")
@DiscriminatorValue("PR")
public class Professor extends Empleat {
	
	private List<Servei> serveis;
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
     */
	public Professor(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) {
		super(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		this.setServeis(new ArrayList<Servei>());
		this.setSessions(new ArrayList<Sessio>());
	}
	
	/**
	 * Llista els serveis prestats pel professor
	 * @return serveis prestats pel professor
	 */
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="professors")
	public List<Servei> getServeis() {
		return this.serveis;
	}
	
	/**
	 * Actualitza els serveis prestats pel professor (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param serveis actualitzats del professor
	 */
	public void setServeis(List<Servei> serveis) {
		this.serveis = serveis;
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
	 * Afegeix un servei al llistat
	 * @param servei a afegir
	 */
	public void afegeixServei(Servei servei) {
		if(!serveis.contains(servei)) {
			serveis.add(servei);
		}
		if (!servei.getProfessors().contains(this)) {
			servei.assignaProfessor(this);			
		}
	}
}
