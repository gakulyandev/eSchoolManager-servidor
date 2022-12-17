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
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Estudiant;
import com.eschoolmanager.server.model.Professor;
import com.eschoolmanager.server.model.Servei;
import com.eschoolmanager.server.model.Sessio;
import com.eschoolmanager.server.utilitats.ConsultesDB;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes de sessions
 */
public class GestorSessio extends GestorEscola {
	
	public final static String DEPARTAMENT_DOCENT = "Docent";
	private final static String ENTITAT = "Sessio"; 
	private final static String[] DADES_CAMPS = {DADES_CAMP_CODI, DADES_CAMP_CODI_EMPLEAT, DADES_CAMP_CODI_ESTUDIANT, DADES_CAMP_CODI_SERVEI};

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
        
        Sessio sessio = escola.altaSessio((Professor) empleat, estudiant, servei, dataIHora);
        
        // Persisteix la sessió
        entityManager.getTransaction().begin();
        entityManager.merge(sessio);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Obté llistat de les sessions a l'escola
     * @param camp de dades per ordenar
     * @param ordre que ha de mostrar
     * @param valor que ha de mostrar
     * @return llista de sessions
     * @throws GestorExcepcions
     */
	public HashMap<Integer, Object> llista(String camp, String ordre, String valor) throws GestorExcepcions {
                
		// Verifica el filtre
		if (ConsultesDB.verificaFiltre(camp, ordre, valor, DADES_CAMPS, DADES_ORDENACIONS)) {
			List<Sessio> sessions;
			
			if (ConsultesDB.isFiltreDefecte(camp, ordre, valor)) {//Llista les sessions amb l'ordre per defecte
				sessions = escola.getSessions();
			} else {// Llista les sessions segons la petició
				
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
						case DADES_CAMP_CODI_EMPLEAT:
							valorConsulta = escola.trobaEmpleat(Integer.parseInt(valor));
							camp = "professor";
							break;
						case DADES_CAMP_CODI_ESTUDIANT:
							valorConsulta = escola.trobaEstudiant(Integer.parseInt(valor));
							camp = "estudiant";
							break;
						case DADES_CAMP_CODI_SERVEI:
							valorConsulta = escola.trobaServei(Integer.parseInt(valor));
							camp = "servei";
							break;
						default:
							valorConsulta = valor;
							break;
					}
				}

				// Crea consulta
				Query query = ConsultesDB.creaConsulta(entityManager, ENTITAT, camp, ordre, valorConsulta);
				
				// Obté els resultats
				sessions = query.getResultList();  
	    		
	            entityManager.getTransaction().commit();
			}
	
			
	        HashMap<Integer, Object> dadesSessions = new HashMap<Integer, Object>();
	        
			int i = 0;
	        for (Sessio sessio : sessions) {
	        	HashMap<String,Object> dadesSessio = new HashMap<String, Object>();
	        	dadesSessio.put(DADES_CODI_SESSIO, sessio.getCodi());
	        	dadesSessio.put(DADES_NOM_EMPLEAT_COMPLET, sessio.getProfessor().getNom());
	        	dadesSessio.put(DADES_COGNOMS_EMPLEAT_COMPLET, sessio.getProfessor().getCognoms());
	        	dadesSessio.put(DADES_NOM_ESTUDIANT, sessio.getEstudiant().getNom());
	        	dadesSessio.put(DADES_COGNOMS_ESTUDIANT_COMPLET, sessio.getEstudiant().getCognoms());
	        	dadesSessio.put(DADES_NOM_SERVEI, sessio.getServei().getNom());
	        	dadesSessio.put(DADES_DATA_I_HORA, sessio.getDataIHora());
	        	dadesSessions.put(i, dadesSessio);
	        	i++;
	    	}
	        
	        return dadesSessions;		
		}
		
		throw new GestorExcepcions(ERROR_CAMP);
    }
	

	/**
     * Obté les dades d'una sessió
     * @param codi de la sessió a cercar
     * @return dades de la sessió
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> consulta(int codi) throws GestorExcepcions {
        
		// Troba la sessió
		Sessio sessio = escola.trobaSessio(codi);
        if (sessio == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_SESSIO);
		}
        
        HashMap<String, Object> dadesSessio = new HashMap<String, Object>();
    	dadesSessio.put(DADES_CODI_SESSIO, sessio.getCodi());
    	dadesSessio.put(DADES_CODI_EMPLEAT, sessio.getProfessor().getCodi());
    	dadesSessio.put(DADES_NOM_EMPLEAT_COMPLET, sessio.getProfessor().getNom());
    	dadesSessio.put(DADES_COGNOMS_EMPLEAT_COMPLET, sessio.getProfessor().getCognoms());
    	dadesSessio.put(DADES_CODI_ESTUDIANT, sessio.getEstudiant().getCodi());
    	dadesSessio.put(DADES_NOM_ESTUDIANT, sessio.getEstudiant().getNom());
    	dadesSessio.put(DADES_COGNOMS_ESTUDIANT_COMPLET, sessio.getEstudiant().getCognoms());
    	dadesSessio.put(DADES_CODI_SERVEI, sessio.getServei().getCodi());
    	dadesSessio.put(DADES_NOM_SERVEI, sessio.getServei().getNom());
    	dadesSessio.put(DADES_DATA_I_HORA, sessio.getDataIHora());
        
        return dadesSessio;
    }
	
	/**
     * Actualitza una sessió
     * @param codi de la sessió a actualitzar
     * @param codi del professor a actualitzar
     * @param codi de l'estudiant a actualitzar
     * @param codi del servei a actualitzar
     * @param data i hora a actualitzar
     * @throws GestorExcepcions
     */
	public void actualitza(Integer codi, int codiEmpleat, int codiEstudiant, int codiServei, Date dataIHora) throws GestorExcepcions {
        
		// Troba la sessió i resta de dades
		Sessio sessio = escola.trobaSessio(codi);
        if (sessio == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_SESSIO);
		}
        
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
        
        // Actualitza la sessió
        escola.actualitzaSessio(sessio, (Professor) empleat, estudiant, servei, dataIHora);

        // Actualitza la base de dades
        entityManager.getTransaction().begin();
        entityManager.merge(sessio);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Dona de baixa una sessió
     * @param codi de la sessió a donar de baixa
     * @throws GestorExcepcions
     */
	public void baixa(int codi) throws GestorExcepcions {
        
		// Troba la sessió i resta de dades
		Sessio sessio = escola.trobaSessio(codi);
        if (sessio == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_SESSIO);
		}
        escola.baixaSessio(sessio);

        // Actualitza la base de dades
        entityManager.getTransaction().begin();
        entityManager.merge(escola);
        entityManager.remove(sessio);
        entityManager.getTransaction().commit();
    }

}
