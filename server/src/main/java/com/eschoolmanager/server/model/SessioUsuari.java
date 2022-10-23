/**
 * 
 */
package com.eschoolmanager.server.model;

import javax.persistence.EntityManager;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que emmagatzema les dades de la sessió iniciada pels usuaris de l'escola
 */
public class SessioUsuari {
	
	private String codi;
	private String nomEmpleat;
	private int codiDepartament;
	
	/**
     * Constructor que crea una sessio d'usuari a partir de paràmetres
     * @param codiSessio de l'usuari
     * @param nomEmpleat de l'usuari
     * @param codiDepartament de l'usuari
     */
	public SessioUsuari(String codiSessio, String nomEmpleat, int codiDepartament) {
		this.setCodi(codiSessio);
		this.setNomEmpleat(nomEmpleat);
		this.setCodiDepartament(codiDepartament);
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
	 * Obté el codi del departament de l'empleat
	 * @return codiDepartament a on pertany l'empleat
	 */
	public int getCodiDepartament() {
		return codiDepartament;
	}

	/**
	 * Actualitza el codi del departament de l'empleat
	 * @param codiDepartament nou valor pel codi del departament de l'empleat
	 */
	public void setCodiDepartament(int codiDepartament) {
		this.codiDepartament = codiDepartament;
	}

}
