/**
 * 
 */
package com.eschoolmanager.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar les dades de l'escola
 */
@Entity
@Table(name="Escola")
public class Escola implements Serializable {

	private int codi;
	private String nom;
	private String adreca;
	private String telefon;
	private List<Usuari> usuaris = new ArrayList<>();
	private List<Departament> departaments = new ArrayList<>();
	private List<Estudiant> estudiants = new ArrayList<>();
	private List<Servei> serveis = new ArrayList<>();

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Escola() {
	}
	
	/**
     * Constructor parametritzat: construeix una nova factura amb els paràmetres especificats
     * @param professor que presta el servei a la sessió
     * @param estudiant a qui es presta el servei a la sessió
     * @param servei que es presta a la sessió
     * @param dataIHora de la sessió realitzada
     */
	public Escola(String nom, String adreca, String telefon) {
		this.setNom(nom);
		this.setAdreca(adreca);
		this.setTelefon(telefon);
	}
	
	/**
	 * Obté el codi identificador de l'escola
	 * @return codi identificador de l'escola
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi identificador de l'escola
	 * @param codi nou valor pel codi identificador de l'escola
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}

	/**
	 * Obté el nom de l'escola
	 * @return nom de l'escola
	 */
    @Column(name="nom")
	public String getNom() {
		return nom;
	}

	/**
	 * Actualitza el nom de l'escola
	 * @param nom nou valor pel nom de l'escola
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Obté l'adreça de l'escola
	 * @return adreca de l'escola
	 */
    @Column(name="adreca")
	public String getAdreca() {
		return adreca;
	}

	/**
	 * Actualitza l'adreça de l'escola
	 * @param adreca nou valor per l'adreça de l'escola
	 */
	public void setAdreca(String adreca) {
		this.adreca = adreca;
	}

	/**
	 * Obté el telèfon de l'escola
	 * @return telefon de l'escola
	 */
    @Column(name="telefon")
	public String getTelefon() {
		return telefon;
	}

	/**
	 * Actualitza el telèfon de l'escola
	 * @param telefon nou valor pel telèfon de l'escola
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * Obté els usuaris de l'escola
	 * @return usuaris de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="escola")
	public List<Usuari> getUsuaris() {
		return usuaris;
	}

	/**
	 * Actualitza els usuaris de l'escola
	 * @param usuaris nou valor pels usuaris de l'escola
	 */
	public void setUsuaris(List<Usuari> usuaris) {
		this.usuaris = usuaris;
	}

	/**
	 * Obté els departaments de l'escola
	 * @return departaments de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="escola")
	public List<Departament> getDepartaments() {
		return departaments;
	}

	/**
	 * Actualitza els departaments de l'escola
	 * @param departaments nou valor pels departaments de l'escola
	 */
	public void setDepartaments(List<Departament> departaments) {
		this.departaments = departaments;
	}

	/**
	 * Obté els estudiants de l'escola
	 * @return estudiants de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="escola")
	public List<Estudiant> getEstudiants() {
		return estudiants;
	}

	/**
	 * Actualitza els estudiants de l'escola
	 * @param estudiants nou valor pels estudiants de l'escola
	 */
	public void setEstudiants(List<Estudiant> estudiants) {
		this.estudiants = estudiants;
	}

	/**
	 * Obté els serveis de l'escola
	 * @return serveis de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="escola")
	public List<Servei> getServeis() {
		return serveis;
	}

	/**
	 * Actualitza els serveis de l'escola
	 * @param serveis nou valor pels serveis de l'escola
	 */
	public void setServeis(List<Servei> serveis) {
		this.serveis = serveis;
	}

}
