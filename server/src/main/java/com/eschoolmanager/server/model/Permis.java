/**
 * 
 */
package com.eschoolmanager.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els permisos dels empleats d'un departament
 */
@Entity
@Table(name="Permis")
public class Permis {

	private int codi;
	private String nom;
	private String crides;
	private List<Departament> departaments;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Permis() {
	}
	
	/**
     * Constructor parametritzat: construeix un nou departament amb els paràmetres especificats
     * @param escola a on és el departament
     * @param nom del departament
     */
	public Permis(String nom, String crides) {
        this.setNom(nom);
        this.setCrides(crides);
        this.setDepartaments(new ArrayList<Departament>());
	}

	/**
	 * Obté el codi identificador del permis
	 * @return codi identificador del permis
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi identificador del permis
	 * @param codi nou valor pel codi identificador del permis
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}

	/**
	 * Obté el nom del permis
	 * @return nom del permis
	 */
    @Column(name="nom")
	public String getNom() {
		return nom;
	}

	/**
	 * Actualitza el nom del permis
	 * @param nom nou valor per el nom del permis
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Obté les crides a les que s'aplica el permis
	 * @return crides a les que s'aplica el permis
	 */
    @Column(name="crides")
	public String getCrides() {
		return crides;
	}

	/**
	 * Actualitza les crides a les que s'aplica el permis
	 * @param crides nou valor per les crides a les que s'aplica el permis
	 */
	public void setCrides(String crides) {
		this.crides = crides;
	}

	/**
	 * Obté els departaments als que s'associa el permis
	 * @return departaments als que s'associa el permis
	 */
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="permisos")
	public List<Departament> getDepartaments() {
		return departaments;
	}
	
	/**
	 * Actualitza els departaments als que s'associa el permis
	 * @param departaments nou valor pels departaments als que s'associa el permis
	 */
	public void setDepartaments(List<Departament> departaments) {
		this.departaments = departaments;
	}
	
	/**
	 * Afegeix un departament al llistat
	 * @param departament a afegir
	 */
	public void afegeixDepartament(Departament departament) {
		this.departaments.add(departament);
	}
}
