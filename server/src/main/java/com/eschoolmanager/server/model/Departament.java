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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.eschoolmanager.server.gestors.GestorExcepcions;

/**
 * @author Gayané Akulyan Akulyan
 * Classe persistent per emmagatzemar els departaments de l'escola
 */
@Entity
@Table(
		name="Departament",
		uniqueConstraints=@UniqueConstraint(columnNames={"nom"})
)
public class Departament {

	private int codi;
	private Escola escola;
	private String nom;
	private List<Empleat> empleats;
	private List<Permis> permisos;

	protected final static String ERROR_PERMISOS_INEXISTENTS = "Els permisos son incorrectes";
	
	/**
	 * Constructor per defecte sense paràmetres
	 * necessari per el correcte funcionament de l'ORM EclipseLink
	 */
	public Departament() {
	}
	
	/**
     * Constructor parametritzat: construeix un nou departament amb els paràmetres especificats
     * @param escola a on és el departament
     * @param nom del departament
     * @param permisos del departament
     */
	public Departament(String nom, List<Permis> permisos) {
        this.setNom(nom);
        this.setEmpleats(new ArrayList<Empleat>());
    	this.setPermisos(permisos);
        
	}
	
	/**
	 * Obté el codi identificador del departament
	 * @return codi identificador del departament
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="codi")
	public int getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi identificador del departament
	 * @param codi actualitzat del departament
	 */
	public void setCodi(int codi) {
		this.codi = codi;
	}

	/**
	 * Obté l'escola a on és el departament
	 * @return escola a on és el departament
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="escola_codi")
	public Escola getEscola() {
		return escola;
	}

	/**
	 * Actualitza l'escola a on és el departament
	 * @param escola actualitzada del departament
	 */
	public void setEscola(Escola escola) {
		this.escola = escola;
	}
	
	/**
	 * Obté el nom del departament
	 * @return nom del departament
	 */
    @Column(name="nom")
	public String getNom() {
		return nom;
	}

	/**
	 * Actualitza el nom del departament
	 * @param nom actualitzat del departament
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Llista els empleats donats d'alta al departament
	 * @return empleats del departament
	 */
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="departament")
	public List<Empleat> getEmpleats() {
		return this.empleats;
	}
	
	/**
	 * Actualitza els empleats donats d'alta al departament (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param empleats actualitzats del departament
	 */
	public void setEmpleats(List<Empleat> empleats) {
		this.empleats = empleats;
	}
	
	/**
	 * Llista els permisos dels empleats del departament 
	 * @return permisos del departament
	 */
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name="departaments_permisos",
			joinColumns=@JoinColumn(name="departament"),
			inverseJoinColumns=@JoinColumn(name="permis")
	)
	public List<Permis> getPermisos() {
		return this.permisos;
	}
	
	/**
	 * Actualitza els permisos dels empleats del departament (només s'inclou pel correcte funcionament del mapeig ORM)
	 * @param permisos actualitzats del departament
	 */
	public void setPermisos(List<Permis> permisos) {
		this.permisos = permisos;
	}
	
	/**
	 * Afegeix un permis al llistat
	 * @param permis a afegir
	 */
	public void adjudicaPermis(Permis permis) {
		if(!permisos.contains(permis)) {
			permisos.add(permis);
		}
		permis.afegeixDepartament(this);
	}
	
	/**
	 * Confirma si el departament té permis per una crida
	 * @param crida a confirmar
	 * @return true o false segons si la crida està entre els permisos del departament
	 */
	public boolean confirmaPermis(String cridaPeticio) {
		for (Permis permis : permisos) {
			String[] crides = permis.getCrides().split(";");
			
			for(String crida : crides) {
				if (crida.equals(cridaPeticio)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Afegeix un empleat al llistat
	 * @param empleat a afegir
	 */
	public void altaEmpleat(Empleat empleat) {
		this.empleats.add(empleat);
		empleat.setDepartament(this);
	}

	/**
	 * Actualitza les dades del Departament
	 * @param nom actualitzat del departament
	 * @param permisos actualitzats del departament
	 */
	public void actualitza(String nom, List<Permis> permisos) {
		this.setNom(nom);
		this.setPermisos(permisos);
	}
	
}
