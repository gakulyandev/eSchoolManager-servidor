/**
 * 
 */
package com.eschoolmanager.server.model;

import java.sql.Date;
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
import com.eschoolmanager.server.utilitats.Constants;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar les dades de l'escola
 */
@Entity
@Table(name="Escola")
public class Escola implements Constants {

	private int codi;
	private String nom;
	private String adreca;
	private String telefon;
	private List<Usuari> usuaris;
	private List<Empleat> empleats;
	private List<Departament> departaments;
	private List<Estudiant> estudiants;
	private List<Servei> serveis;
	

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
		this.setEmpleats(new ArrayList<Empleat>());
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
	 * Obté els empleats de l'escola
	 * @return empleats de l'escola
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="escola")
	public List<Empleat> getEmpleats() {
		return empleats;
	}

	/**
	 * Actualitza els empleats de l'escola
	 * @param empleats actualitzats de l'escola
	 */
	public void setEmpleats(List<Empleat> empleats) {
		this.empleats = empleats;
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
	 * @throws GestorExcepcions 
	 */
	public void baixaDepartament(Departament departament) throws GestorExcepcions {
		if (!departament.isBuit()) {
			throw new GestorExcepcions(ERROR_ELEMENTS_RELACIONATS_DEPARTAMENT);
		}
		this.departaments.remove(departament);
	}
	
	/**
	 * Dona d'alta un usuari al llistat
	 * @param nom d'usuari del nou usuari
	 * @param contrasenya del nou usuari
	 * @return usuari creat
	 * @throws GestorExcepcions 
	 */
	public Usuari altaUsuari(String nomUsuari, String contrasenya) throws GestorExcepcions {
		if (trobaUsuari(nomUsuari) != null) {
            throw new GestorExcepcions(ERROR_EXISTEIX_USUARI);
        }
		
		Usuari usuari = new Usuari(nomUsuari, contrasenya);
		this.usuaris.add(usuari);
		usuari.setEscola(this);
		
		return usuari;
	}
	
	/**
	 * Actualitza un usuari
     * @param l'usuari
     * @param nom d'usuari actualitzat
     * @param contrasenya actualitzada
	 * @throws GestorExcepcions 
	 */
	public void actualitzaUsuari(Usuari usuari, String nomUsuari, String contrasenya) throws GestorExcepcions {

		if ((trobaUsuari(nomUsuari) != null) && !usuari.getNomUsuari().equals(nomUsuari)) {
            throw new GestorExcepcions(ERROR_EXISTEIX_USUARI);
        }
		
		usuari.actualitza(nomUsuari, contrasenya);
	}
	
	/**
	 * Obté un usuari amb el nom d'usuari
	 * @return usuari trobat o null
	 */
	public Usuari trobaUsuari(String nomUsuari) {
		for(Usuari usuari : this.usuaris) {
			if (usuari.getNomUsuari().equals(nomUsuari)) {
				return usuari;
			}
		}
		
		return null;
	}
	
	/**
	 * Obté un usuari amb el nom d'usuari i contrasenya indicats
	 * @return usuari trobat o null
	 */
	public Usuari trobaUsuari(String nomUsuari, String contrasenya) {
		for(Usuari usuari : this.usuaris) {
			if (usuari.getNomUsuari().equals(nomUsuari) && usuari.getContrasenya().equals(contrasenya)) {
				return usuari;
			}
		}
		
		return null;
	}
	
	/**
	 * Dona de baixa un usuari
	 * @throws GestorExcepcions 
	 */
	public void baixaUsuari(Usuari usuari) throws GestorExcepcions {
		this.usuaris.remove(usuari);
	}
	
	/**
	 * Afegeix un empleat al llistat
     * @param dni del nou empleat
     * @param nom del nou empleat
     * @param cognoms del nou empleat
     * @param dataNaixement del nou empleat
     * @param telefon del nou empleat
     * @param email del nou empleat
     * @param adreça del nou empleat
     * @param departament a on es dona d'alta l'empleat
     * @return empleat creat
	 * @throws GestorExcepcions 
	 */
	public Empleat altaEmpleat(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca, Departament departament) throws GestorExcepcions {

		if (trobaEmpleat(dni) != null) {
            throw new GestorExcepcions(ERROR_EXISTEIX_EMPLEAT);
        }
		
		Empleat empleat = departament.altaEmpleat(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		this.empleats.add(empleat);
		empleat.setEscola(this);
		
		return empleat;
	}
	
	/**
	 * Actualitza un empleat
     * @param l'empleat
     * @param dni actualitzat de l'empleat
     * @param nom actualitzat de l'empleat
     * @param cognoms actualitzat de l'empleat
     * @param dataNaixement actualitzada de l'empleat
     * @param telefon actualitzat de l'empleat
     * @param email actualitzat de l'empleatt
     * @param adreça actualitzada de l'empleat
     * @param estat actualitzat de l'empleat
     * @param departament actualitzat de l'empleat
	 * @throws GestorExcepcions 
	 */
	public void actualitzaEmpleat(Empleat empleat, String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca, Boolean actiu, Departament departament) throws GestorExcepcions {

		if ((trobaEmpleat(dni) != null) && !empleat.getDni().equals(dni)) {
            throw new GestorExcepcions(ERROR_EXISTEIX_EMPLEAT);
        }
		
		empleat.actualitza(dni, nom, cognoms, dataNaixement, telefon, email, adreca, actiu, departament);
	}
	
	/**
	 * Obté un empleat amb el codi indicat
	 * @return empleat trobat o null
	 */
	public Empleat trobaEmpleat(int codi) {
		for(Empleat empleat : this.empleats) {
			if (empleat.getCodi() == codi) {
				return empleat;
			}
		}
		
		return null;
	}
	
	/**
	 * Obté un empleat amb el dni indicat
	 * @return empleat trobat o null
	 */
	public Empleat trobaEmpleat(String dni) {
		for(Empleat empleat : this.empleats) {
			if (empleat.getDni().equals(dni)) {
				return empleat;
			}
		}
		
		return null;
	}
	
	/**
	 * Dona de baixa un empleat
	 * @throws GestorExcepcions 
	 */
	public void baixaEmpleat(Empleat empleat) throws GestorExcepcions {
		if (!empleat.isBuit()) {
			throw new GestorExcepcions(ERROR_ELEMENTS_RELACIONATS_EMPLEAT);
		}
		this.empleats.remove(empleat);
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
	
	/**
	 * Dona de baixa un servei
	 * @throws GestorExcepcions 
	 */
	public void baixaServei(Servei servei) throws GestorExcepcions {
		if (!servei.isBuit()) {
			throw new GestorExcepcions(ERROR_ELEMENTS_RELACIONATS_SERVEI);
		}
		this.serveis.remove(servei);
	}

	/**
	 * Actualitza les dades de l'escola
	 * @param nom actualitzat
	 * @param adreça actualitzada
	 * @param telèfon actualitzat
	 */
	public void actualitza(String nom, String adreca, String telefon) {
		this.setNom(nom);
		this.setAdreca(adreca);
		this.setTelefon(telefon);
	}
	
	/**
	 * Afegeix un estudiant al llistat
     * @param dni del nou estudiant
     * @param nom del nou estudiant
     * @param cognoms del nou estudiant
     * @param dataNaixement del nou estudiant
     * @param telefon del nou estudiant
     * @param email del nou estudiant
     * @param adreça del nou estudiant
     * @return estudiant creat
	 * @throws GestorExcepcions 
	 */
	public Estudiant altaEstudiant(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) throws GestorExcepcions {

		if (trobaEstudiant(dni) != null) {
            throw new GestorExcepcions(ERROR_DUPLICAT_ESTUDIANT);
        }
		
		Estudiant estudiant = new Estudiant(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
		this.estudiants.add(estudiant);
		estudiant.setEscola(this);
		
		return estudiant;
	}
	
	/**
	 * Obté un estudiant amb el dni indicat
	 * @return estudiant trobat o null
	 */
	public Estudiant trobaEstudiant(String dni) {
		for(Estudiant estudiant : this.estudiants) {
			if (estudiant.getDni().equals(dni)) {
				return estudiant;
			}
		}
		
		return null;
	}
	
	/**
	 * Obté un estudiant amb el codi indicat
	 * @return estudiant trobat o null
	 */
	public Estudiant trobaEstudiant(int codi) {
		for(Estudiant estudiant : this.estudiants) {
			if (estudiant.getCodi() == codi) {
				return estudiant;
			}
		}
		
		return null;
	}
}
