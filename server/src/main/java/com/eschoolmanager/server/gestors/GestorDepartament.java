/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.utilitats.ConsultesDB;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de departaments
 */
public class GestorDepartament extends GestorEscola {

	private final static String DEPARTAMENT_ADMINISTRADOR = "Administrador";   
	private final static String ENTITAT = "Departament"; 
	private final static String[] DADES_CAMPS = {DADES_CAMP_NOM, DADES_CAMP_CODI};
	
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorDepartament(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Dona d'alta un nou departament a l'escola
     * @param nom del nou departament
     * @param permisos del nou departament
     * @throws GestorExcepcions
     */
	public void alta(String nom, HashMap<String,Boolean>permisosPeticio) throws GestorExcepcions {
        
        // Crea llistat de permisos al departament a partir dels permisos d'administrador, els màxims
        List<Permis> permisos = escola.trobaDepartament(DEPARTAMENT_ADMINISTRADOR).getPermisos();
        List<Permis> permisosDepartament = new ArrayList<Permis>();
        for(Permis permis : permisos) {
        	if(permis.getNom().equals(PERMIS_ACCES)) {
        		permisosDepartament.add(permis);
        	}
        	
        	for (String permisNom: permisosPeticio.keySet()) {
            	if(permis.getNom().equals(permisNom) && (permisosPeticio.get(permisNom) == true)) {
            		permisosDepartament.add(permis);
            	}        		
        	}
        }       

        // Dona d'alta el departament a l'escola
        Departament departament = escola.altaDepartament(nom, permisosDepartament);

        // Persisteix el departament
        entityManager.getTransaction().begin();
        entityManager.merge(departament);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Obté llistat dels departaments de l'escola
     * @param camp de dades per ordenar
     * @param ordre que ha de mostrar
     * @param valor que ha de mostrar
     * @return llista de departaments
     * @throws GestorExcepcions
     */
	public HashMap<Integer, Object> llista(String camp, String ordre, String valor) throws GestorExcepcions {
                
		// Verifica el filtre
		if (ConsultesDB.verificaFiltre(camp, ordre, valor, DADES_CAMPS, DADES_ORDENACIONS)) {
			List<Departament> departaments;
			
			if (ConsultesDB.isFiltreDefecte(camp, ordre, valor)) {//Llista els departaments amb l'ordre per defecte
				departaments = escola.getDepartaments();
			} else {// Llista els departaments segons la petició
				
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
							valorConsulta = Integer.parseInt(valor);
							break;
						default:
							valorConsulta = valor;
							break;
					}
				}

				// Crea consulta
				Query query = ConsultesDB.creaConsulta(entityManager, ENTITAT, camp, ordre, valorConsulta);
	    		
	    		// Obté els resultats
	    		departaments = query.getResultList();  
	    		
	            entityManager.getTransaction().commit();
			}
	
			
	        HashMap<Integer, Object> dadesDepartaments = new HashMap<Integer, Object>();
	        
			int i = 0;
	        for (Departament departament : departaments) {
	        	HashMap<String,Object> dadesDepartament = new HashMap<String, Object>();
	        	dadesDepartament.put(DADES_CODI_DEPARTAMENT, departament.getCodi());
	        	dadesDepartament.put(DADES_NOM_DEPARTAMENT, departament.getNom());
	        	dadesDepartaments.put(i, dadesDepartament);
	        	i++;
	    	}
	        
	        return dadesDepartaments;		
		}
		
		throw new GestorExcepcions(ERROR_CAMP);
    }
	
	/**
     * Obté les dades d'un departament de l'escola
     * @param codi del departament a cercar
     * @return dades del departament
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> consulta(int codi) throws GestorExcepcions {
        
		// Troba el departament
        Departament departament = escola.trobaDepartament(codi);
        if (departament == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_DEPARTAMENT);
		}
        
        // Llista els permisos del departament
        List<String> permisos = new ArrayList<String>();   
        for (Permis permisDepartament : departament.getPermisos()) {
        	if(!permisDepartament.getNom().equals(PERMIS_ACCES)) {
        		permisos.add(permisDepartament.getNom());
        	}		
    	}
        
        HashMap<String, Object> dadesDepartament = new HashMap<String, Object>();
        dadesDepartament.put(DADES_CODI_DEPARTAMENT, departament.getCodi());
        dadesDepartament.put(DADES_NOM_DEPARTAMENT, departament.getNom());
        dadesDepartament.put(DADES_PERMISOS_DEPARTAMENT, permisos);
        
        return dadesDepartament;
    }
	
	/**
     * Actualitza departament de l'escola
     * @param codi del departament a actualitzar
     * @param nom actualitzat del departament
     * @param permisos actualitzats del nou departament
     * @throws GestorExcepcions
     */
	public void actualitza(Integer codi, String nom, HashMap<String,Boolean>permisosPeticio) throws GestorExcepcions {
        
		// Troba el departament
		Departament departament = escola.trobaDepartament(codi);
        if (departament == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_DEPARTAMENT);
		}

        // Crea llistat de permisos al departament a partir dels permisos d'administrador, els màxims
        List<Permis> permisos = escola.trobaDepartament(DEPARTAMENT_ADMINISTRADOR).getPermisos();
        List<Permis> permisosDepartament = new ArrayList<Permis>();
        for(Permis permis : permisos) {
        	if(permis.getNom().equals(PERMIS_ACCES)) {
        		permisosDepartament.add(permis);
        	}
        	
        	for (String permisNom: permisosPeticio.keySet()) {
            	if(permis.getNom().equals(permisNom) && (permisosPeticio.get(permisNom) == true)) {
            		permisosDepartament.add(permis);
            	}        		
        	}
        } 
        
        // Actualitza el departament a l'escola
        escola.actualitzaDepartament(departament, nom, permisosDepartament);

        // Actualitza el departament
        entityManager.getTransaction().begin();
        entityManager.merge(departament);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Dona de baixa un departament de l'escola
     * @param codi del departament a donar de baixa
     * @throws GestorExcepcions
     */
	public void baixa(int codi) throws GestorExcepcions {
        
		// Troba el departament
        Departament departament = escola.trobaDepartament(codi);
        if (departament == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_DEPARTAMENT);
		}
        escola.baixaDepartament(departament);
        
        // Actualitza el departament
        entityManager.getTransaction().begin();
        entityManager.merge(escola);
        entityManager.remove(departament);
        entityManager.getTransaction().commit();
    }
}
