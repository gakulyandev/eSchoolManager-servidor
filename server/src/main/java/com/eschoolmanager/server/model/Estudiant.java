/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els estudiants de l'escola
 */
@Entity
@Table(name="Estudiant")
@DiscriminatorValue("ES")
public class Estudiant extends Persona {
	
	private Escola escola;
	private boolean registrat = false;
	private List<Beca> beques;
	private List<Sessio> sessions;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Estudiant() {
	}
	
	/**
     * Constructor parametritzat: construeix un nou estudiant amb els paràmetres especificats
     * @param escola a on és registrat l'estudiant
     * @param dni identificador de l'estudiant
     * @param nom de l'estudiant
     * @param cognoms de l'estudiant
     * @param dataNaixement de l'estudiant
     * @param telefon de contacte de l'estudiant
     * @param email de contacte de l'estudiant
     * @param adreca de l'estudiant
     */
	public Estudiant(Escola escola, String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) {
		super(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		
		this.setEscola(escola);
		this.setRegistrat(true);
		this.setBeques(new ArrayList<Beca>());
		this.setSessions(new ArrayList<Sessio>());
	}

	/**
	 * Obté l'escola de l'estudiant
	 * @return escola de l'estudiant
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="escola_codi", nullable=false)
	public Escola getEscola() {
		return escola;
	}

	/**
	 * Actualitza l'escola de l'estudiant
	 * @param escola actualitzada de l'estudiant
	 */
	public void setEscola(Escola escola) {
		this.escola = escola;
	}

	/**
	 * Obté l'estat de l'estudiant
	 * @return registrat true | false segons l'estat actual de l'estudiant
	 */
    @Column(name="registrat")
	public boolean isRegistrat() {
		return registrat;
	}

	/**
	 * Actualitza l'estat de l'estudiant
	 * @param estat actualitzat de l'estudiant
	 */
	public void setRegistrat(boolean registrat) {
		this.registrat = registrat;
	}
	
	/**
	 * Llista les beques adjudicades a l'estudiant
	 * @return beques de l'estudiant
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="estudiant")
	public List<Beca> getBeques() {
		return this.beques;
	}
	
	/**
	 * Actualitza les beques adjudicades a l'estudiant (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param beques actualitzades a l'estudiant 
	 */
	public void setBeques(List<Beca> beques) {
		this.beques = beques;
	}
	
	/**
	 * Llista les sessions realitzades a l'estudiant
	 * @return sessions realitzades a l'estudiant
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="estudiant")
	public List<Sessio> getSessions() {
		return this.sessions;
	}
	
	/**
	 * Actualitza les sessions realitzades a l'estudiant (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param sessions actualitzades de l'estudiant
	 */
	public void setSessions(List<Sessio> sessions) {
		this.sessions = sessions;
	}
}
