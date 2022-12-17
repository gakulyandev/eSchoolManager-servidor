/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Servei;
import com.eschoolmanager.server.utilitats.ConsultesDB;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de departaments
 */
public class GestorServei extends GestorEscola {

	private final static String ENTITAT = "Servei"; 
	private final static String[] DADES_CAMPS = {DADES_CAMP_NOM, DADES_CAMP_CODI, DADES_CAMP_DURADA, DADES_CAMP_COST};
    
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

        // Persisteix el servei
        entityManager.getTransaction().begin();
        entityManager.merge(servei);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Obté llistat dels serveis de l'escola
     * @param camp de dades per ordenar
     * @param ordre que ha de mostrar
     * @param valor que ha de mostrar
     * @return llista de serveis
     * @throws GestorExcepcions
     */
	public HashMap<Integer, Object> llista(String camp, String ordre, String valor) throws GestorExcepcions {

		// Verifica el filtre
		if (ConsultesDB.verificaFiltre(camp, ordre, valor, DADES_CAMPS, DADES_ORDENACIONS)) {
			List<Servei> serveis;
			
			if (ConsultesDB.isFiltreDefecte(camp, ordre, valor)) {//Llista els serveis amb l'ordre per defecte
				serveis = escola.getServeis();
			} else {// Llista els serveis segons la petició
				
				// Estableix Ordre per defecte
				if (ordre.length() == 0) {
					ordre = DADES_ORDRE_ASC;
				}
				
				entityManager.getTransaction().begin(); 
				
				// Ajusta el valor al tipus de la base de dades
				Object valorConsulta = null;				
				if (valor.length() > 0) {
					switch (camp) {
						case DADES_CAMP_CODI:
						case DADES_CAMP_DURADA:
							valorConsulta = Integer.parseInt(valor);
							break;
						case DADES_CAMP_COST:
							valorConsulta = Double.parseDouble(valor);
							break;
						default:
							valorConsulta = valor;
							break;
					}
				}

				// Crea consulta
				Query query = ConsultesDB.creaConsulta(entityManager, ENTITAT, camp, ordre, valorConsulta);
	    		
	    		// Obté els resultats
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
        
        Servei servei = escola.trobaServei(codi);
        if (servei == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_SERVEI);
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
        
		// Troba el servei
		Servei servei = escola.trobaServei(codi);
        if (servei == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_SERVEI);
		}
        
        // Actualitza el servei de l'escola
        escola.actualitzaServei(servei, nom, cost, durada);

        // Actualitza la base de dades
        entityManager.getTransaction().begin();
        entityManager.merge(servei);
        entityManager.getTransaction().commit();
    }

	/**
     * Dona de baixa un servei de l'escola
     * @param codi del servei a donar de baixa
     * @throws GestorExcepcions
     */
	public void baixa(int codi) throws GestorExcepcions {
        
		// Troba el servei
        Servei servei = escola.trobaServei(codi);
        if (servei == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_SERVEI);
		}
        escola.baixaServei(servei);

        // Actualitza la base de dades
        entityManager.getTransaction().begin();
        entityManager.merge(escola);
        entityManager.remove(servei);
        entityManager.getTransaction().commit();
    }
}
