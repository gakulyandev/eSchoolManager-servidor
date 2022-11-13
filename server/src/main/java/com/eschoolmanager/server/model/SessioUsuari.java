/**
 * 
 */
package com.eschoolmanager.server.model;

import java.util.List;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que emmagatzema les dades de la sessió iniciada pels usuaris de l'escola
 */
public class SessioUsuari {
	
	private String codi;
	private Usuari usuari;
	private String nomEmpleat;
	private String nomDepartament;
	private List<Permis> permisos;
	
	/**
     * Constructor que crea una sessio d'usuari a partir de paràmetres
     * @param codiSessio de l'usuari
     * @param nomEmpleat de l'usuari
     * @param codiDepartament de l'usuari
     */
	public SessioUsuari(String codiSessio, Usuari usuari, String nomEmpleat, String nomDepartament, List<Permis> permisos) {
		this.codi = codiSessio;
		this.usuari = usuari;
		this.nomEmpleat = nomEmpleat;
		this.nomDepartament = nomDepartament;
		this.permisos = permisos;
	}

	/**
	 * Obté el codi de sessió
	 * @return codi de sessió
	 */
	public String getCodi() {
		return codi;
	}

	/**
	 * Obté l'usuari de la sessió
	 * @return usuari de la sessió
	 */
	public Usuari getUsuari() {
		return usuari;
	}

	/**
	 * Obté el nom de l'empleat
	 * @return nomEmpleat de l'empleat
	 */
	public String getNomEmpleat() {
		return nomEmpleat;
	}

	/**
	 * Obté el nom del departament de l'empleat
	 * @return nomDepartament a on pertany l'empleat
	 */
	public String getNomDepartament() {
		return nomDepartament;
	}

	/**
	 * Obté els permisos del departament de l'empleat
	 * @return permisos del departament a on pertany l'empleat
	 */
	public List<Permis> getPermisos() {
		return permisos;
	}
}
