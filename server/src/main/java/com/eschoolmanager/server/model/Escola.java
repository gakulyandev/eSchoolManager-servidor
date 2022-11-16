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
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.eschoolmanager.server.gestors.GestorExcepcions;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar les dades de l'escola
 */
@Entity
@Table(name="Escola")
public class Escola {

	private int codi;
	private String nom;
	private String adreca;
	private String telefon;
	private List<Usuari> usuaris;
	private List<Departament> departaments;
	private List<Estudiant> estudiants;
	private List<Servei> serveis;

	protected final static String ERROR_EXISTEIX_DEPARTAMENT = "Ja existeix un departament amb el mateix nom";
	protected final static String ERROR_EXISTEIX_SERVEI = "Ja existeix un servei amb el mateix nom";
	

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
		this.setUsuaris(new ArrayList<Usuari>());
		this.setDepartaments(new ArrayList<Departament>());
		this.setEstudiants(new ArrayList<Estudiant>());
		this.setServeis(new ArrayList<Servei>());
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
	
	/**
	 * Afegeix un departament al llistat
	 * @param departament a afegir
	 * @throws GestorExcepcions 
	 */
	public void altaDepartament(Departament departament) throws GestorExcepcions {
		if (trobaDepartament(departament.getNom()) != null) {
            throw new GestorExcepcions(ERROR_EXISTEIX_DEPARTAMENT);
        }

		this.departaments.add(departament);
		departament.setEscola(this);
	}
	
	/**
	 * Obté un departament amb el codi indicat
	 * @return departament trobat o null
	 */
	public Departament trobaDepartament(int codi) {
		for(Departament departament : departaments) {
			if (departament.getCodi() == codi) {
				return departament;
			}
		}
		
		return null;
	}
	
	/**
	 * Obté un departament amb el nom indicat
	 * @return departament trobat o null
	 */
	public Departament trobaDepartament(String nom) {
		for(Departament departament : departaments) {
			if (departament.getNom().equals(nom)) {
				return departament;
			}
		}
		
		return null;
	}
	
	/**
	 * Afegeix un usuari al llistat
	 * @param usuari a afegir
	 */
	public void altaUsuari(Usuari usuari) {
		this.usuaris.add(usuari);
		usuari.setEscola(this);
	}
	
	/**
	 * Obté un usuari amb el nom d'usuari i contrasenya indicats
	 * @return usuari trobat o null
	 */
	public Usuari trobaUsuari(String nomUsuari, String contrasenya) {
		for(Usuari usuari : usuaris) {
			if (usuari.getNomUsuari().equals(nomUsuari) && usuari.getContrasenya().equals(contrasenya)) {
				return usuari;
			}
		}
		
		return null;
	}
	
	/**
	 * Afegeix un servei al llistat
	 * @param servei a afegir
	 * @throws GestorExcepcions 
	 */
	public void altaServei(Servei servei) throws GestorExcepcions {
		if (trobaServei(servei.getNom()) != null) {
            throw new GestorExcepcions(ERROR_EXISTEIX_SERVEI);
        }

		this.serveis.add(servei);
		servei.setEscola(this);
	}
	
	/**
	 * Obté un servei amb el nom indicat
	 * @return servei trobat o null
	 */
	public Servei trobaServei(String nom) {
		for(Servei servei : serveis) {
			if (servei.getNom().equals(nom)) {
				return servei;
			}
		}
		
		return null;
	}
}
