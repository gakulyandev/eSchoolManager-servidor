/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Beca;
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
        
        Beca beca = escola.altaBeca(adjudicant, importInicial, estudiant, servei);
        
        // Persisteix la beca
        entityManager.getTransaction().begin();
        entityManager.merge(beca);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Obté les dades d'una beca
     * @param codi de la beca a cercar
     * @return dades de la beca
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> consulta(int codi) throws GestorExcepcions {
        
		Beca beca = escola.trobaBeca(codi);
        if (beca == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_BECA);
		}
        
        HashMap<String, Object> dadesBeca = new HashMap<String, Object>();
        dadesBeca.put(DADES_CODI_BECA, beca.getCodi());
        dadesBeca.put(DADES_ADJUDICANT_BECA, beca.getAdjudicant());
        dadesBeca.put(DADES_IMPORT_INICIAL_BECA, beca.getImportInicial());
        dadesBeca.put(DADES_IMPORT_RESTANT_BECA, beca.getImportRestant());
        dadesBeca.put(DADES_ESTAT_BECA, beca.isFinalitzada());
        dadesBeca.put(DADES_CODI_ESTUDIANT, beca.getEstudiant().getCodi());
        dadesBeca.put(DADES_NOM_ESTUDIANT, beca.getEstudiant().getNom());
        dadesBeca.put(DADES_COGNOMS_ESTUDIANT, beca.getEstudiant().getCognoms());
        dadesBeca.put(DADES_CODI_SERVEI, beca.getServei().getCodi());
        dadesBeca.put(DADES_NOM_SERVEI, beca.getServei().getNom());
        
        return dadesBeca;
    }
	
	/**
     * Actualitza una beca a l'escola
     * @param codi de la beca a actualitzar
     * @param adjudicant actualitzat de la beca
     * @param import inicial actualitzat de la beca
     * @param codi de l'estudiant actualitzat
     * @param codi del servei actualitzat
     * @throws GestorExcepcions
     */
	public void actualitza(Integer codi, String adjudicant, Double importInicial, int codiEstudiant, int codiServei) throws GestorExcepcions {
        
		// Troba la beca
		Beca beca = escola.trobaBeca(codi);
        if (beca == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_BECA);
		}
        
        // Troba l'estudiant i servei
        Estudiant estudiant = escola.trobaEstudiant(codiEstudiant);
        Servei servei = escola.trobaServei(codiServei);
        
        if (estudiant == null || servei == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_ESTUDIANT_SERVEI);
		}
        
        // Actualitza la beca de l'escola
        escola.actualitzaBeca(beca, adjudicant, importInicial, estudiant, servei);

        // Actualitza la base de dades
        entityManager.getTransaction().begin();
        entityManager.merge(beca);
        entityManager.getTransaction().commit();
    }

	/**
     * Dona de baixa una beca a l'escola
     * @param codi de la beca a donar de baixa
     * @throws GestorExcepcions
     */
	public void baixa(int codi) throws GestorExcepcions {
        
		// Troba la beca
		Beca beca = escola.trobaBeca(codi);
        if (beca == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_BECA);
		}
        escola.baixaBeca(beca);
        
        // Actualitza la base de dades
        entityManager.getTransaction().begin();
        entityManager.merge(escola);
        entityManager.remove(beca);
        entityManager.getTransaction().commit();
    }

}
