/**
 * 
 */
package com.eschoolmanager.server.gestors;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Servei;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de departaments
 */
public class GestorServei extends GestorEscola {
    
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorServei(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Afeigeix un nou departament a la base de dades
     * @param departament el departament que s'ha de desar a la base de dades
     * @throws GestorExcepcions
     */
	public void alta(String nom, Double cost, int durada) throws GestorExcepcions {
        
		// Inicialitza el servei
        Servei servei = new Servei(nom, cost, durada);

        // Dona d'alta el servei a l'escola
        escola.altaServei(servei);

        // Persisteix el departament
        entityManager.getTransaction().begin();
        entityManager.merge(servei);
        entityManager.getTransaction().commit();
    }
}
