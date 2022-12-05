/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Beca;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Estudiant;
import com.eschoolmanager.server.model.Professor;
import com.eschoolmanager.server.model.Servei;
import com.eschoolmanager.server.model.Sessio;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de sessions
 */
public class GestorSessio extends GestorEscola {
	
	public final static String DEPARTAMENT_DOCENT = "Docent";

	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorSessio(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Dona d'alta una nova sessió
     * @param codi del professor
     * @param codi de l'estudiant
     * @param codi del servei
     * @param data i hora
     * @throws GestorExcepcions
     */
	public void alta(int codiEmpleat, int codiEstudiant, int codiServei, Date dataIHora) throws GestorExcepcions {

		// Troba el professor, l'estudiant i servei
        Empleat empleat = escola.trobaEmpleat(codiEmpleat);
        Estudiant estudiant = escola.trobaEstudiant(codiEstudiant);
        Servei servei = escola.trobaServei(codiServei);
        
        if (empleat == null || estudiant == null || servei == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_PROFESSOR_ESTUDIANT_SERVEI);
		}

        // Comprova si l'empleat és docent
        if (!empleat.getDepartament().getNom().equals(DEPARTAMENT_DOCENT)) {
			throw new GestorExcepcions(ERROR_INEXISTENT_PROFESSOR);
        }

        // Comprova si el professor imparteix el servei
        Professor professor = (Professor) empleat;
        if (!professor.imparteixServei(servei)) {
			throw new GestorExcepcions(ERROR_INEXISTENT_RELACIO_PROFESSOR_SERVEI);
        }
        
        Sessio sessio = escola.altaSessio(professor, estudiant, servei, dataIHora);
        
        // Persisteix la sessió
        entityManager.getTransaction().begin();
        entityManager.merge(sessio);
        entityManager.getTransaction().commit();
    }

}
