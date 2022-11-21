/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Estudiant;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'estudiants
 */
public class GestorEstudiant extends GestorEscola {

	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorEstudiant(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}

	/**
     * Dona d'alta un nou estudiant a l'escola
     * @param dni del nou estudiant
     * @param nom del nou estudiant
     * @param cognoms del nou estudiant
     * @param dataNaixement del nou estudiant
     * @param telefon del nou estudiant
     * @param email del nou estudiant
     * @param adreça del nou estudiant
     * @throws GestorExcepcions
     */
	public void alta(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca) throws GestorExcepcions {
		
		// Dona d'alta l'estudiant a l'escola
        Estudiant estudiant = escola.altaEstudiant(dni, nom, cognoms, dataNaixement, telefon, email, adreca);
        
        // Persisteix l'estudiant
        entityManager.getTransaction().begin();
        entityManager.merge(estudiant);
        entityManager.getTransaction().commit();
    }
}
