/**
 * 
 */
package com.eschoolmanager.server.gestors;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Permis;
import com.eschoolmanager.server.model.SessioUsuari;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que gestiona l'inici i tancament d'una sessió d'usuari
 */
public class GestorSessioUsuari extends GestorEscola {

	private GestorSessionsUsuari gestorSessionsUsuari;

	/**
     * Constructor que associa el gestor a un EntityManager i inicialitza el gestor d'usuaris
     * @param entityManager EntityManager al qual s'associa el gestor
     */
	public GestorSessioUsuari(GestorSessionsUsuari gestorSessionsUsuari, EntityManager entityManager) throws GestorExcepcions {
		super(entityManager);
		this.gestorSessionsUsuari = gestorSessionsUsuari;
	}
	
	/**
	 * Troba la sessió de l'usuari
	 * @param codiSessio de sessió a validar
	 * @return Sessió de l'usuari
	 * @return true o false segons si està validad o no
	 */
	public SessioUsuari trobaSessio(String codiSessio) throws GestorExcepcions {
		
		// Troba la sessió amb el codi indicat
		SessioUsuari sessio = gestorSessionsUsuari.trobaSessio(codiSessio);
		if (sessio == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_USUARI);
		}
		
		return sessio;
	}
	
	/**
	 * Confirma permisos
	 * @param sessioUsuari per confirmar
	 * @param crida per validar el permis de l'usuari
	 * @param codiEmpleat necessari per alguns permisos
	 */
	public boolean confirmaPermis(SessioUsuari sessioUsuari, String crida, int codiEmpleat) throws GestorExcepcions {
		
		Empleat empleat = sessioUsuari.getUsuari().getEmpleat();
		
		if ((crida.equals(CRIDA_CONSULTA_EMPLEAT) || crida.equals(CRIDA_MODI_EMPLEAT)) && 
				!empleat.getDepartament().confirmaPermis(crida) && 
				(codiEmpleat == empleat.getCodi())) {// Confirma permis si és el propi empleat el que fa consulta o modi
			return true;
		} else if (empleat.getDepartament().confirmaPermis(crida)) {// Confirma permis sobre la resta de crides
			return true;
		}
		
		return false;
	}
	
	/**
	 * Obté dades de la sessió iniciada de l'usuari
	 * @param nomUsuari de l'usuari que inicia sessió
	 * @param contrasenya de l'usuari que inicia sessió
	 * @return dades de la sessió iniciada
	 * @throws GestorExcepcions en cas que no s'hagi trobat cap usuari amb dades indicades
	 */
	public HashMap<String, Object> iniciaSessio(String nomUsuari, String contrasenya) throws GestorExcepcions {
		
		// Troba l'usuari per nom d'usuari i contrasenya
		Usuari usuari = escola.trobaUsuari(nomUsuari, contrasenya);
		if (usuari == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_USUARI);
		}
		
		// Inicia i desa sessió
		SessioUsuari sessio = usuari.generaSessio();
		gestorSessionsUsuari.desaSessio(sessio);
		
		// Llista els permisos del departament
        List<String> permisos = new ArrayList<String>();   
        for (Permis permisDepartament : sessio.getPermisos()) {
        	if(!permisDepartament.getNom().equals(PERMIS_ACCES)) {
        		permisos.add(permisDepartament.getNom());
        	}		
    	}
        
        HashMap<String, Object> dadesSessio = new HashMap<String, Object>();
        dadesSessio.put(DADES_CODI_SESSIO, sessio.getCodi());
        dadesSessio.put(DADES_CODI_EMPLEAT, sessio.getCodiEmpleat());
        dadesSessio.put(DADES_NOM_EMPLEAT, sessio.getNomEmpleat());
        dadesSessio.put(DADES_NOM_DEPARTAMENT, sessio.getNomDepartament());
        dadesSessio.put(DADES_PERMISOS_DEPARTAMENT, permisos);
        
		return dadesSessio;
	}
	
	/**
	 * Tanca la sessió de l'usuari
	 * @param codiSessio de l'usuari que te la sessió iniciada
	 * @throws GestorExcepcions en sas que no s'hagi trobat cap usuari amb dades indicades
	 */
	public void tancaSessio(String codiSessio) throws GestorExcepcions {
		
		// Confirma existencia d'una sessió amb el codi indicat
		if (gestorSessionsUsuari.trobaSessio(codiSessio) == null) {
			throw new GestorExcepcions(ERROR_INEXISTENT_USUARI);
		}
		
		// Esborra la sessió desada
		gestorSessionsUsuari.esborraSessio(codiSessio);
	}
}
