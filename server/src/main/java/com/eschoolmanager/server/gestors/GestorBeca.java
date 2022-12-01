/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Estudiant;
import com.eschoolmanager.server.model.Servei;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de beques
 */
public class GestorBeca extends GestorEscola {

	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorBeca(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Dona d'alta una nova beca
     * @param adjudicant de la beca
     * @param import inicial de la beca
     * @param codi de l'estudiant
     * @param codi del servei
     * @throws GestorExcepcions
     */
	public void alta(String adjudicant, Double importInicial, int codiEstudiant, int codiServei) throws GestorExcepcions {
		
		// Troba l'estudiant i servei
        Estudiant estudiant = escola.trobaEstudiant(codiEstudiant);
        Servei servei = escola.trobaServei(codiServei);
        
        if (estudiant == null || servei == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_ESTUDIANT_SERVEI);
		}
        
        estudiant.adjudicaBeca(adjudicant, importInicial, servei);
        
        // Persisteix l'estudiant
        entityManager.getTransaction().begin();
        entityManager.merge(estudiant);
        entityManager.getTransaction().commit();
    }

}
