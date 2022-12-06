/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Beca;
import com.eschoolmanager.server.model.Estudiant;
import com.eschoolmanager.server.model.Servei;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de beques
 */
public class GestorBeca extends GestorEscola {
	
	private final static String[] DADES_CAMPS = {"codi","adjudicant","importInicial", "importRestant", "codiEstudiant", "codiServei"};

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
     * Obté llistat de les beques a l'escola
     * @param camp de dades per ordenar
     * @param ordre que ha de mostrar
     * @return llista de beques
     * @throws GestorExcepcions
     */
	public HashMap<Integer, Object> llista(String camp, String ordre) throws GestorExcepcions {
                
		if ((Arrays.asList(DADES_CAMPS).contains(camp) && Arrays.asList(DADES_ORDENACIONS).contains(ordre)) || 
			 camp.length() == 0 && ordre.length() == 0) {
			List<Beca> beques;
			
			if ((camp.equals(DADES_CAMP_CODI) && ordre.equals(DADES_ORDRE_ASC)) || camp.length() == 0 && ordre.length() == 0) {
				beques = escola.getBeques(); //Llista les beques amb l'ordre per defecte
			} else {
				//Llista les beques segons la petició
				String consulta = "SELECT b FROM Beca b ORDER BY b." + camp + " " + ordre;
	
				entityManager.getTransaction().begin();   
				
	    		Query query = (Query) entityManager.createQuery(consulta);
	    		beques = query.getResultList();  
	    		
	            entityManager.getTransaction().commit();
			}
	
			
	        HashMap<Integer, Object> dadesBeques = new HashMap<Integer, Object>();
	        
			int i = 0;
	        for (Beca beca : beques) {
	        	HashMap<String,Object> dadesBeca = new HashMap<String, Object>();
	        	dadesBeca.put(DADES_CODI_BECA, beca.getCodi());
	        	dadesBeca.put(DADES_ADJUDICANT_BECA, beca.getAdjudicant());
	        	dadesBeca.put(DADES_IMPORT_INICIAL_BECA, beca.getImportInicial());
	        	dadesBeca.put(DADES_IMPORT_RESTANT_BECA, beca.getImportRestant());
	        	dadesBeca.put(DADES_NOM_ESTUDIANT, beca.getEstudiant().getNom());
	        	dadesBeca.put(DADES_COGNOMS_ESTUDIANT_COMPLET, beca.getEstudiant().getCognoms());
	        	dadesBeca.put(DADES_NOM_SERVEI, beca.getServei().getNom());
	        	dadesBeca.put(DADES_ESTAT_BECA, beca.isFinalitzada());
	        	dadesBeques.put(i, dadesBeca);
	        	i++;
	    	}
	        
	        return dadesBeques;		
		}
		
		throw new GestorExcepcions(ERROR_CAMP);
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
        dadesBeca.put(DADES_COGNOMS_ESTUDIANT_COMPLET, beca.getEstudiant().getCognoms());
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
	public void actualitza(Integer codi, String adjudicant, Double importInicial, int codiEstudiant, int codiServei, boolean finalitzada) throws GestorExcepcions {
        
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
        escola.actualitzaBeca(beca, adjudicant, importInicial, estudiant, servei, finalitzada);

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
