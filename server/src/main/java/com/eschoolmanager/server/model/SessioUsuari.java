/**
 * 
 */
package com.eschoolmanager.server.model;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que emmagatzema les dades de la sessió iniciada pels usuaris de l'escola
 */
public class SessioUsuari {
	
	private String codi;
	private String nomEmpleat;
	private String nomDepartament;
	private List<Permis> permisos;
	
	/**
     * Constructor que crea una sessio d'usuari a partir de paràmetres
     * @param codiSessio de l'usuari
     * @param nomEmpleat de l'usuari
     * @param codiDepartament de l'usuari
     */
	public SessioUsuari(String codiSessio, String nomEmpleat, String nomDepartament, List<Permis> permisos) {
		this.setCodi(codiSessio);
		this.setNomEmpleat(nomEmpleat);
		this.setNomDepartament(nomDepartament);
		this.setPermisos(permisos);
	}

	/**
	 * Obté el codi de sessió
	 * @return codi de sessió
	 */
	public String getCodi() {
		return codi;
	}

	/**
	 * Actualitza el codi de l'empleat
	 * @param codi nou valor pel codi de l'empleat
	 */
	public void setCodi(String codi) {
		this.codi = codi;
	}

	/**
	 * Obté el nom de l'empleat
	 * @return nomEmpleat de l'empleat
	 */
	public String getNomEmpleat() {
		return nomEmpleat;
	}

	/**
	 * Actualitza el nom de l'empleat
	 * @param nomEmpleat nou valor pel nom de l'empleat
	 */
	public void setNomEmpleat(String nomEmpleat) {
		this.nomEmpleat = nomEmpleat;
	}

	/**
	 * Obté el nom del departament de l'empleat
	 * @return nomDepartament a on pertany l'empleat
	 */
	public String getNomDepartament() {
		return nomDepartament;
	}

	/**
	 * Actualitza el nom del departament de l'empleat
	 * @param nomDepartament nou valor pel nom del departament de l'empleat
	 */
	public void setNomDepartament(String nomDepartament) {
		this.nomDepartament = nomDepartament;
	}

	/**
	 * Obté els permisos del departament de l'empleat
	 * @return permisos del departament a on pertany l'empleat
	 */
	public List<Permis> getPermisos() {
		return permisos;
	}

	/**
	 * Actualitza els permisos del departament de l'empleat
	 * @param permisos nou valor pels permisos del departament de l'empleat
	 */
	public void setPermisos(List<Permis> permisos) {
		this.permisos = permisos;
	}

}
