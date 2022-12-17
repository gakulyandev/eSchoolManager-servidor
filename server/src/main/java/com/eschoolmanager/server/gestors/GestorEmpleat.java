/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.Servei;
import com.eschoolmanager.server.model.Usuari;
import com.eschoolmanager.server.utilitats.ConsultesDB;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona les altes, baixes, modificacions i consultes d'empleats
 */
public class GestorEmpleat extends GestorEscola {

	private final static String ENTITAT = "Empleat"; 
	private final static String[] DADES_CAMPS = {DADES_CAMP_DNI, DADES_CAMP_NOM, DADES_CAMP_CODI, DADES_CAMP_COGNOMS, DADES_CAMP_TELEFON, DADES_CAMP_EMAIL, DADES_CAMP_ADRECA, DADES_CAMP_CODI_DEPARTAMENT};
	
	/**
     * Constructor que associa el gestor a un EntityManager
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorEmpleat(EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
	}
	
	/**
     * Dona d'alta un nou empleat a l'escola
     * @param dni del nou empleat
     * @param nom del nou empleat
     * @param cognoms del nou empleat
     * @param dataNaixement del nou empleat
     * @param telefon del nou empleat
     * @param email del nou empleat
     * @param adreça del nou empleat
     * @param codi del departament del nou empleat
     * @param nom d'usuari del nou empleat
     * @param contrasenya d'usuari del nou empleat
     * @throws GestorExcepcions
     */
	public void alta(String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca, int codiDepartament, String nomUsuari, String contrasenya) throws GestorExcepcions {
		
		// Troba el departament
        Departament departament = escola.trobaDepartament(codiDepartament);
        
        // Dona d'alta l'empleat a l'escola
        Empleat empleat = escola.altaEmpleat(dni, nom, cognoms, dataNaixement, telefon, email, adreca, departament);
        Usuari usuari = escola.altaUsuari(nomUsuari, contrasenya);
        empleat.assignaUsuari(usuari);
        
        // Persisteix l'empleat
        entityManager.getTransaction().begin();
        entityManager.merge(empleat);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Obté llistat dels empleats de l'escola
     * @param camp de dades per ordenar
     * @param ordre que ha de mostrar
     * @param valor que ha de mostrar
     * @return llista d'empleats
     * @throws GestorExcepcions
     */
	public HashMap<Integer, Object> llista(String camp, String ordre, String valor) throws GestorExcepcions {
         
		// Verifica el filtre
		if (ConsultesDB.verificaFiltre(camp, ordre, valor, DADES_CAMPS, DADES_ORDENACIONS)) {
			List<Empleat> empleats;
			
			if (ConsultesDB.isFiltreDefecte(camp, ordre, valor)) {//Llista els empleats amb l'ordre per defecte
				empleats = escola.getEmpleats();
			} else {// Llista els empleats segons la petició
				
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
						case DADES_CAMP_CODI_DEPARTAMENT:
							valorConsulta = escola.trobaDepartament(Integer.parseInt(valor));
							camp = "departament";
							break;
						default:
							valorConsulta = valor;
							break;
					}
				}

				// Crea consulta
				Query query = ConsultesDB.creaConsulta(entityManager, ENTITAT, camp, ordre, valorConsulta);
	    		
	    		// Obté els resultats
				empleats = query.getResultList();  
	    		
	            entityManager.getTransaction().commit();
			}
			
	        HashMap<Integer, Object> dadesEmpleats = new HashMap<Integer, Object>();
	        
			int i = 0;
	        for (Empleat empleat : empleats) {
	        	HashMap<String,Object> dadesEmpleat = new HashMap<String, Object>();
	        	dadesEmpleat.put(DADES_CODI_EMPLEAT, empleat.getCodi());
	        	dadesEmpleat.put(DADES_NOM_EMPLEAT, empleat.getNom());
	        	dadesEmpleat.put(DADES_COGNOMS_EMPLEAT, empleat.getCognoms());
	        	dadesEmpleat.put(DADES_CODI_DEPARTAMENT, empleat.getDepartament().getCodi());
	        	dadesEmpleat.put(DADES_NOM_DEPARTAMENT, empleat.getDepartament().getNom());
	        	dadesEmpleats.put(i, dadesEmpleat);
	        	i++;
	    	}
	        
	        return dadesEmpleats;		
		}
		
		throw new GestorExcepcions(ERROR_CAMP);
    }
	
	/**
     * Obté les dades d'un empleat de l'escola
     * @param codi de l'empleat a cercar
     * @return dades de l'empleat
     * @throws GestorExcepcions
     */
	public HashMap<String, Object> consulta(int codi) throws GestorExcepcions {
        
        Empleat empleat = escola.trobaEmpleat(codi);
        if (empleat == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_EMPLEAT);
		}
        
        HashMap<String, Object> dadesEmpleat = new HashMap<String, Object>();
        dadesEmpleat.put(DADES_CODI_EMPLEAT, empleat.getCodi());
        dadesEmpleat.put(DADES_DNI_EMPLEAT, empleat.getDni());
        dadesEmpleat.put(DADES_NOM_EMPLEAT, empleat.getNom());
        dadesEmpleat.put(DADES_COGNOMS_EMPLEAT, empleat.getCognoms());
        dadesEmpleat.put(DADES_DATA_NAIXEMENT_EMPLEAT, empleat.getDataNaixement());
        dadesEmpleat.put(DADES_ADRECA_EMPLEAT, empleat.getAdreca());
        dadesEmpleat.put(DADES_TELEFON_EMPLEAT, empleat.getTelefon());
        dadesEmpleat.put(DADES_EMAIL_EMPLEAT, empleat.getEmail());
        dadesEmpleat.put(DADES_CODI_DEPARTAMENT, empleat.getDepartament().getCodi());
        dadesEmpleat.put(DADES_NOM_DEPARTAMENT, empleat.getDepartament().getNom());
        dadesEmpleat.put(DADES_NOM_USUARI, empleat.getUsuari().getNomUsuari());
        dadesEmpleat.put(DADES_ESTAT_EMPLEAT, empleat.isActiu());
        
        return dadesEmpleat;
    }
	
	/**
     * Actualitza empleat de l'escola
     * @param codi de l'empleat
     * @param dni de l'empleat
     * @param nom de l'empleat
     * @param cognoms de l'empleat
     * @param dataNaixement de l'empleat
     * @param telefon de l'empleat
     * @param email de l'empleatt
     * @param adreça de l'empleat
     * @param codi del departament de l'empleat
     * @param nom d'usuari de l'empleat
     * @param contrasenya d'usuari de l'empleat
     * @param estat de l'empleat
     * @throws GestorExcepcions
     */
	public void actualitza(int codiEmpleat, String dni, String nom, String cognoms, Date dataNaixement, String telefon, String email, String adreca, int codiDepartament, String nomUsuari, String contrasenya, Boolean actiu) throws GestorExcepcions {

        // Troba l'empleat
        Empleat empleat = escola.trobaEmpleat(codiEmpleat);
        if (empleat == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_EMPLEAT);
		}
        
		// Troba el departament
        Departament departament = escola.trobaDepartament(codiDepartament);
        
        // Actualitza l'empleat i usuari
        escola.actualitzaEmpleat(empleat, dni, nom, cognoms, dataNaixement, telefon, email, adreca, actiu, departament);
        escola.actualitzaUsuari(empleat.getUsuari(), nomUsuari, contrasenya);
        
        // Persisteix el departament
        entityManager.getTransaction().begin();
        entityManager.merge(empleat);
        entityManager.getTransaction().commit();
    }
	
	/**
     * Dona de baixa un empleat de l'escola
     * @param codi de l'empleat a donar de baixa
     * @throws GestorExcepcions
     */
	public void baixa(int codi) throws GestorExcepcions {
        
		// Troba el departament
        Empleat empleat = escola.trobaEmpleat(codi);
        if (empleat == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_EMPLEAT);
		}
        escola.baixaEmpleat(empleat);
        escola.baixaUsuari(empleat.getUsuari());
        
        // Actualitza el departament
        entityManager.getTransaction().begin();
        entityManager.merge(escola);
        entityManager.remove(empleat);
        entityManager.getTransaction().commit();
    }

}
