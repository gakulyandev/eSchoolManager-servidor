/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static javax.persistence.InheritanceType.JOINED;
import static javax.persistence.DiscriminatorType.STRING;

/**
 * @author Gayané Akulyan Akulyan
 * Classe abstracta que emmagatzema dades de les persones registrades
 */
@Entity
@Table(
		name="Persona",
		uniqueConstraints=@UniqueConstraint(columnNames={"dni"})
)
@Inheritance(strategy=JOINED)
@DiscriminatorColumn(name="tipus", discriminatorType=STRING,length=20)
public abstract class Persona {

	private int codi;
	private String dni;
	private String nom;
	private String cognoms;
	private Date dataNaixement;
	private String telefon;
	private String email;
	private String adreca;

	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Persona() {
	}
	
	/**
     * Constructor parametritzat: construeix un nou objecte persona amb els paràmetres especificats
     * @param dni identificador de la persona
     * @param nom de la persona
     * @param cognoms de la persona
     * @param dataNaixement de la persona
     * @param telefon de contacte de la persona
     * @param email de contacte de la persona
     * @param adreca de la persona
     */
	public Persona(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) {
        this.setDni(dni);
        this.setNom(nom); 
        this.setCognoms(cognoms); 
        this.setDataNaixement(dataNaixement); 
        this.setTelefon(telefon); 
        this.setEmail(email); 
        this.setAdreca(adreca); 
	}

	/**
	 * Obté el codi identificador de la persona
	 * @return codi identificador de la persona
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi identificador de la persona
	 * @param codi nou valor pel codi identificador de la persona
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}

	/**
	 * Obté el DNI de la persona
	 * @return DNI de la persona
	 */
    @Column(name="dni")
	public String getDni() {
		return dni;
	}

	/**
	 * Actualitza el DNI de la persona
	 * @param dni nou valor pel DNI de la persona
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Obté el nom de la persona
	 * @return nom de la persona
	 */
    @Column(name="nom")
	public String getNom() {
		return nom;
	}

	/**
	 * Actualitza el nom de la persona
	 * @param nom nou valor pel nom de la persona
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Obté els cognoms de la persona
	 * @return cognoms de la persona
	 */
    @Column(name="cognoms")
	public String getCognoms() {
		return cognoms;
	}

	/**
	 * Actualitza els cognoms de la persona
	 * @param cognoms nou valor pels cognoms de la persona
	 */
	public void setCognoms(String cognoms) {
		this.cognoms = cognoms;
	}

	/**
	 * Obté la data de naixement de la persona
	 * @return dataNaixement de la persona
	 */
    @Column(name="data_naixement")
	public Date getDataNaixement() {
		return dataNaixement;
	}

	/**
	 * Actualitza la data de naixement de la persona
	 * @param dataNaixement nou valor per la data de naixement de la persona
	 */
	public void setDataNaixement(Date dataNaixement) {
		this.dataNaixement = dataNaixement;
	}

	/**
	 * Obté el telèfon de contacte
	 * @return telefon de contacte de la persona
	 */
    @Column(name="telefon")
	public String getTelefon() {
		return telefon;
	}

	/**
	 * Actualitza el telèfon de contacte
	 * @param telefon nou valor pel telèfon de contacte de la persona
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * Obté l'email de contacte
	 * @return email de contacte de la persona
	 */
    @Column(name="email")
	public String getEmail() {
		return email;
	}

	/**
	 * Actualitza l'email de contacte
	 * @param email nou valor per l'email de contacte de la persona
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obté l'adreça de la persona
	 * @return adreca de la persona
	 */
    @Column(name="adreca")
	public String getAdreca() {
		return adreca;
	}

	/**
	 * Actualitza l'adreça de la persona
	 * @param adreca nou valor per l'adreça de la persona
	 */
	public void setAdreca(String adreca) {
		this.adreca = adreca;
	}


}
