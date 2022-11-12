/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.SessioUsuari;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona l'inici i tancament d'una sessió d'usuari
 */
public class GestorSessioUsuari {

	private EntityManager entityManager = null;
	private GestorUsuari gestorUsuari;
	private final static String CODI_SESSIO = "codi";

	/**
     * Constructor que associa el gestor a un EntityManager i inicia el gestor d'usuaris
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorSessioUsuari(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.gestorUsuari = new GestorUsuari(entityManager);
	}
	
	/**
	 * Valida el codi de sessió de l'usuari
	 * @param codiSessio de sessió a validar
	 * @param crida per validar el permis de l'usuari
	 * @throws GestorExcepcions en cas que no sigui vàlid
	 */
	public void validaSessio(String codiSessio, String crida) throws GestorExcepcions {
		
		// Troba l'usuari pel codi de sessió
		Usuari usuari = gestorUsuari.troba(codiSessio);
		
		// Confirma si te permisos per la crida
		Empleat empleat = usuari.getEmpleat();

		if (!empleat.getDepartament().confirmaPermis(crida)) {
			throw new GestorExcepcions("L'usuari no està autoritzat per aquesta acció");
		}
	}
	
	/**
	 * Obté dades de la sessió iniciada de l'usuari
	 * @param nomUsuari de l'usuari que inicia sessió
	 * @param contrasenya de l'usuari que inicia sessió
	 * @return sessio amb dades de la sessió iniciada
	 * @throws GestorExcepcions en cas que no s'hagi trobat cap usuari amb dades indicades
	 */
	public SessioUsuari iniciaSessio(String nomUsuari, String contrasenya) throws GestorExcepcions {
	
		Usuari usuari = gestorUsuari.troba(nomUsuari, contrasenya);
		
		SessioUsuari sessio = usuari.iniciaSessio();
		
        entityManager.getTransaction().begin();
        entityManager.merge(usuari);
        entityManager.getTransaction().commit();
        
		return sessio;
	}
	
	/**
	 * Tanca la sessió de l'usuari
	 * @param codiSessio de l'usuari que te la sessió iniciada
	 * @throws GestorExcepcions en sas que no s'hagi trobat cap usuari amb dades indicades
	 */
	public void tancaSessio(String codiSessio) throws GestorExcepcions {

		Usuari usuari = gestorUsuari.troba(codiSessio);
		usuari.tancaSessio();
		
        entityManager.getTransaction().begin();
        entityManager.merge(usuari);
        entityManager.getTransaction().commit();
	}
}
