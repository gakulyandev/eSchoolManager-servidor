/**
 * 
 */
package com.eschoolmanager.server.model;

import static javax.persistence.InheritanceType.JOINED;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els empleats de l'escola
 */
@Entity
@Table(name="Empleat")
@Inheritance(strategy=JOINED)
@DiscriminatorValue("EM")
public class Empleat extends Persona {
	
	private boolean actiu = false;
	private Usuari usuari;
	private Departament departament;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Empleat() {
	}
	
	/**
     * Constructor parametritzat: construeix un nou empleat amb els paràmetres especificats
     * @param dni identificador de l'empleat
     * @param nom de l'empleat
     * @param cognoms de l'empleat
     * @param dataNaixement de l'empleat
     * @param telefon de contacte de l'empleat
     * @param email de contacte de l'empleat
     * @param adreca de l'empleat
     * @param departament de l'empleat
     */
	public Empleat(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) {
		super(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		
		this.setActiu(true);
	}

	/**
	 * Obté l'estat de l'empleat
	 * @return actiu true | false segons l'estat actual de l'empleat
	 */
    @Column(name="actiu")
	public boolean isActiu() {
		return actiu;
	}

	/**
	 * Actualitza l'estat de l'empleat
	 * @param estat actualitzat de l'empleat
	 */
	public void setActiu(boolean actiu) {
		this.actiu = actiu;
	}
	
	/**
	 * Obté l'usuari de l'empleat
	 * @return usuari de l'empleat
	 */
    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional=false, mappedBy="empleat", fetch=FetchType.LAZY)
	public Usuari getUsuari() {
		return usuari;
	}
	
	/**
	 * Actualitza l'usuari de l'empleat
	 * @param usuari actualitzat de l'empleat
	 */
	public void setUsuari(Usuari usuari) {
		this.usuari = usuari;
	}
	
	/**
	 * Obté el departament de l'empleat
	 * @return departament de l'empleat
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="departament_codi", nullable=false)
	public Departament getDepartament() {
		return departament;
	}
	
	/**
	 * Actualitza el departament de l'empleat
	 * @param departament actualitzat de l'empleat
	 */
	public void setDepartament(Departament departament) {
		this.departament = departament;
	}
	
	/**
	 * Afegeix l'usuari de l'empleat
	 * @param usuari de l'empleat
	 */
	public void assignaUsuari(Usuari usuari) {
		this.setUsuari(usuari);
		usuari.setEmpleat(this);
	}
}
