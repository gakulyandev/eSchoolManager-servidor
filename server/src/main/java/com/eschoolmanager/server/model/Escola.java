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

	private final static String ERROR_EXISTEIX_DEPARTAMENT = "Ja existeix un departament amb el mateix nom";
	private final static String ERROR_EXISTEIX_SERVEI = "Ja existeix un servei amb el mateix nom";
	private final static String ERROR_ELEMENTS_RELACIONATS = "Existeixen altres elements relacionats amb el departament";
	

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
	 * @param codi actualitzat de l'escola
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
	 * @param nom actualitzat de l'escola
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
	 * @param adreca actualitzada de l'escola
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
	 * @param telefon actualitzat de l'escola
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * Obté els usuaris de l'escola
	 * @return usuaris de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="escola")
	public List<Usuari> getUsuaris() {
		return usuaris;
	}

	/**
	 * Actualitza els usuaris de l'escola
	 * @param usuaris actualitzats de l'escola
	 */
	public void setUsuaris(List<Usuari> usuaris) {
		this.usuaris = usuaris;
	}

	/**
	 * Obté els departaments de l'escola
	 * @return departaments de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="escola")
	public List<Departament> getDepartaments() {
		return departaments;
	}

	/**
	 * Actualitza els departaments de l'escola
	 * @param departaments actualitzats de l'escola
	 */
	public void setDepartaments(List<Departament> departaments) {
		this.departaments = departaments;
	}

	/**
	 * Obté els estudiants de l'escola
	 * @return estudiants de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="escola")
	public List<Estudiant> getEstudiants() {
		return estudiants;
	}

	/**
	 * Actualitza els estudiants de l'escola
	 * @param estudiants actualitzats de l'escola
	 */
	public void setEstudiants(List<Estudiant> estudiants) {
		this.estudiants = estudiants;
	}

	/**
	 * Obté els serveis de l'escola
	 * @return serveis de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="escola")
	public List<Servei> getServeis() {
		return serveis;
	}

	/**
	 * Actualitza els serveis de l'escola
	 * @param serveis actualitzats de l'escola
	 */
	public void setServeis(List<Servei> serveis) {
		this.serveis = serveis;
	}
	
	/**
	 * Dona d'alta un departament a l'escola
	 * @param nom del nou departament
	 * @param permisos del nou departament
	 * @return departament donat d'alta
	 * @throws GestorExcepcions 
	 */
	public Departament altaDepartament(String nom, List<Permis> permisos) throws GestorExcepcions {
		if (trobaDepartament(nom) != null) {
            throw new GestorExcepcions(ERROR_EXISTEIX_DEPARTAMENT);
        }

		Departament departament = new Departament(nom, permisos);
		this.departaments.add(departament);
		departament.setEscola(this);
		
		return departament;
	}
	
	/**
	 * Actualitza un departament del llistat
	 * @param departament a actualitzar
	 * @param nom actualitzat del departament
	 * @param permisos actualitzats del departament
	 * @throws GestorExcepcions 
	 */
	
	public void actualitzaDepartament(Departament departament, String nom, List<Permis> permisos) throws GestorExcepcions {
		if ((trobaDepartament(nom) != null) && !departament.getNom().equals(nom)) {
            throw new GestorExcepcions(ERROR_EXISTEIX_DEPARTAMENT);
        }
		
		departament.actualitza(nom, permisos);
	}
	
	/**
	 * Obté un departament amb el codi indicat
	 * @return departament trobat o null
	 */
	public Departament trobaDepartament(int codi) {
		for(Departament departament : this.departaments) {
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
		for(Departament departament : this.departaments) {
			if (departament.getNom().equals(nom)) {
				return departament;
			}
		}
		
		return null;
	}
	
	/**
	 * Dona de baixa un departament
	 * @return departament trobat o null
	 * @throws GestorExcepcions 
	 */
	public void baixaDepartament(Departament departament) throws GestorExcepcions {
		if (!departament.isBuit()) {
			throw new GestorExcepcions(ERROR_ELEMENTS_RELACIONATS);
		}
		this.departaments.remove(departament);
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
	 * Dona d'alta un servei a l'escola
     * @param nom del servei
     * @param cost del servei
     * @durada durada del servei
     * @throws GestorExcepcions
	 */
	public Servei altaServei(String nom, Double cost, int durada) throws GestorExcepcions {
		if (trobaServei(nom) != null) {
            throw new GestorExcepcions(ERROR_EXISTEIX_SERVEI);
        }

		Servei servei = new Servei(nom, cost, durada);
		this.serveis.add(servei);
		servei.setEscola(this);
		
		return servei;
	}
	
	/**
	 * Actualitza un servei del llistat
	 * @param servei a actualitzar
	 * @param nom actualitzat del servei
     * @param cost actualitzat del servei
     * @param durada actualitzada del serveis
	 * @throws GestorExcepcions 
	 */
	
	public void actualitzaServei(Servei servei, String nom, Double cost, int durada) throws GestorExcepcions {
		if ((trobaServei(nom) != null) && !servei.getNom().equals(nom)) {
            throw new GestorExcepcions(ERROR_EXISTEIX_SERVEI);
        }
		
		servei.actualitza(nom, cost, durada);
	}
	
	/**
	 * Obté un servei amb el codi indicat
	 * @return servei trobat o null
	 */
	public Servei trobaServei(int codi) {
		for(Servei servei : serveis) {
			if (servei.getCodi() == codi) {
				return servei;
			}
		}
		
		return null;
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
