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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.eschoolmanager.server.gestors.GestorExcepcions;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els estudiants de l'escola
 */
@Entity
@Table(name="Estudiant")
@DiscriminatorValue("ES")
public class Estudiant extends Persona {
	
	private boolean registrat = false;
	private Double importImputat;
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
	 * @throws GestorExcepcions 
     */
	public Estudiant(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) throws GestorExcepcions {
		super(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		
		this.setRegistrat(true);
		this.setImportImputat(0.00);
		this.setBeques(new ArrayList<Beca>());
		this.setSessions(new ArrayList<Sessio>());
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
	 * Obté l'import acumulat de les sessions realitzades
	 * @return importImputat
	 */
	public Double getImportImputat() {
		return importImputat;
	}

	/**
	 * Actualitza l'import acumulat de les sessions realitzades
	 * @param importImputat actualitzat
	 */
	public void setImportImputat(Double importImputat) {
		this.importImputat = importImputat;
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
	
	/**
	 * Actualitza l'estudiant
     * @param codi de l'estudiant
     * @param dni de l'estudiant
     * @param nom de l'estudiant
     * @param cognoms de l'estudiant
     * @param dataNaixement de l'estudiant
     * @param telefon de l'estudiant
     * @param email de l'estudiant
     * @param adreça de l'estudiant
     * @param estat de l'estudiant
	 * @throws GestorExcepcions 
	 */
	public void actualitza(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca, Boolean registrat) throws GestorExcepcions {
		this.actualitzaPersona(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		this.setRegistrat(registrat);
	}
	
	/**
	 * Indica si l'estudiant té o no elements relacionats
	 * @return true o false segons si té o no elements relacionats
	 */
	public Boolean isBuit() {
		return (this.sessions.isEmpty() && this.beques.isEmpty());
	}
	
	/**
	 * Afegeix una beca al llistat
	 * @param beca a afegir
	 */
	public void adjudicaBeca(Beca beca) {
		if(!beques.contains(beca)) {
			beques.add(beca);
		}
	}
	
	/**
	 * Assigna una sessió
	 * @param sessió a assignar
	 */
	public void assignaSessio(Sessio sessio) {
		if(!sessions.contains(sessio)) {
			sessions.add(sessio);
		}
		
		// Imputa l'import del servei
		this.imputaServei(sessio.getServei());
	}
	
	/**
	 * Imputa el servei impartit una vegada es crea una sessió
	 */
	public void imputaServei(Servei servei) {

		// Imputa part o la totalitat a la beca que hi hagi adjudicada sobre el servei
		double importAImputar = servei.getCost();
		for(Beca beca: this.beques) {
			if (beca.getServei() == servei) {
				double importRestant = beca.getImportRestant();
				if (importRestant >= importAImputar) {
					beca.imputaImport(importAImputar);
					importAImputar = 0.00;
				} else {
					beca.imputaImport(importRestant);
					System.out.println("Import a imputar " +importAImputar+  ", Import restant" + importRestant);
					importAImputar = importAImputar - importRestant;
					importAImputar = 15.00;
				}
			}
		}
		System.out.println("Import a imputar " +importAImputar);
		
		this.setImportImputat(this.getImportImputat() + importAImputar);
	}
	
	
}
