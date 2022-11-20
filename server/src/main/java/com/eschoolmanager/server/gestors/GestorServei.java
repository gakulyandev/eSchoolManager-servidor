/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.Servei;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de departaments
 */
public class GestorServei extends GestorEscola {

	private final static String DADES_CODI_SERVEI = "codiServei";  
	private final static String DADES_NOM_SERVEI = "nomServei";
	private final static String DADES_DURADA_SERVEI = "durada";
	private final static String DADES_COST_SERVEI = "cost";
	private final static String DADES_CAMP_CODI = "codi";  
	private final static String DADES_ORDRE_ASC = "ASC";  
	
	private final static String[] DADES_CAMPS = {"nom","codi","durada","cost"};
	
	private final static String ERROR_SERVEI_INEXISTENT = "No existeix el servei indicat";
    
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorServei(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Afeigeix un nou departament a la base de dades
     * @param nom del servei
     * @param cost del servei
     * @durada durada del servei
     * @throws GestorExcepcions
     */
	public void alta(String nom, Double cost, int durada) throws GestorExcepcions {
        
        // Dona d'alta el servei a l'escola
        Servei servei = escola.altaServei(nom, cost, durada);

        // Persisteix el departament
        entityManager.getTransaction().begin();
        entityManager.merge(servei);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Obté llistat dels serveis de l'escola
     * @param camp de dades per ordenar
     * @param ordre que ha de mostrar
     * @return llista de serveis
     * @throws GestorExcepcions
     */
	public HashMap<Integer, Object> llista(String camp, String ordre) throws GestorExcepcions {
                
        // Llista els permisos del departament
		if ((Arrays.asList(DADES_CAMPS).contains(camp) && Arrays.asList(DADES_ORDENACIONS).contains(ordre)) || 
			 camp.length() == 0 && ordre.length() == 0) {
			List<Servei> serveis;
			
			if ((camp.equals(DADES_CAMP_CODI) && ordre.equals(DADES_ORDRE_ASC)) || camp.length() == 0 && ordre.length() == 0) {
				serveis = escola.getServeis();
			} else {
				String consulta = "SELECT s FROM Servei s ORDER BY s." + camp + " " + ordre;
	
				entityManager.getTransaction().begin();   
				
	    		Query query = (Query) entityManager.createQuery(consulta);
	    		serveis = query.getResultList();  
	    		
	            entityManager.getTransaction().commit();
			}
	
			
	        HashMap<Integer, Object> dadesServeis = new HashMap<Integer, Object>();
	        
			int i = 0;
	        for (Servei servei : serveis) {
	        	HashMap<String,Object> dadesServei = new HashMap<String, Object>();
	        	dadesServei.put(DADES_CODI_SERVEI, servei.getCodi());
	        	dadesServei.put(DADES_NOM_SERVEI, servei.getNom());
	        	dadesServei.put(DADES_DURADA_SERVEI, servei.getDurada());
	        	dadesServei.put(DADES_COST_SERVEI, servei.getCost());
	        	dadesServeis.put(i, dadesServei);
	        	i++;
	    	}
	        
	        return dadesServeis;		
		}
		
		throw new GestorExcepcions(ERROR_CAMP);
    }
	
	/**
     * Obté les dades d'un servei de l'escola
     * @param codi del servei a cercar
     * @return dades del servei
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> consulta(int codi) throws GestorExcepcions {
        
		// Troba el servei
        Servei servei = escola.trobaServei(codi);
        if (servei == null) {
			throw new GestorExcepcions(ERROR_SERVEI_INEXISTENT);
		}
        
        HashMap<String, Object> dadesServei = new HashMap<String, Object>();
        dadesServei.put(DADES_CODI_SERVEI, servei.getCodi());
        dadesServei.put(DADES_NOM_SERVEI, servei.getNom());
    	dadesServei.put(DADES_DURADA_SERVEI, servei.getDurada());
    	dadesServei.put(DADES_COST_SERVEI, servei.getCost());
        
        return dadesServei;
    }
	
	/**
     * Actualitza servei de l'escola
     * @param codi del servei a actualitzar
     * @param nom actualitzat del servei
     * @param cost actualitzat del servei
     * @param durada actualitzada del servei
     * @throws GestorExcepcions
     */
	public void actualitza(Integer codi, String nom, Double cost, int durada) throws GestorExcepcions {
        
		// Troba el departament
		Servei servei = escola.trobaServei(codi);
        if (servei == null) {
			throw new GestorExcepcions(ERROR_SERVEI_INEXISTENT);
		}
        
        // Actualitza el servei de l'escola
        escola.actualitzaServei(servei, nom, cost, durada);

        // Actualitza el departament
        entityManager.getTransaction().begin();
        entityManager.merge(servei);
        entityManager.getTransaction().commit();
    }
}
