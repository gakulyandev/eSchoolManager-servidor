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
	 * Valida el codi de sessió de l'usuari
	 * @param codiSessio de sessió a validar
	 * @param crida per validar el permis de l'usuari
	 * @throws GestorExcepcions en cas que no sigui vàlid
	 */
	public void validaSessio(String codiSessio, String crida) throws GestorExcepcions {
		
		// Troba l'usuari pel codi de sessió
		Usuari usuari = gestorSessionsUsuari.trobaUsuari(codiSessio);
		if (usuari == null) {
			throw new GestorExcepcions(ERROR_USUARI_INEXISTENT);
		}
		
		// Confirma si te permisos per la crida
		Empleat empleat = usuari.getEmpleat();

		if (!empleat.getDepartament().confirmaPermis(crida)) {
			throw new GestorExcepcions(ERROR_NO_AUTORITZAT);
		}
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
			throw new GestorExcepcions(ERROR_USUARI_INEXISTENT);
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
		
		// Confirma existencia d'un usuari amb el codi de sessió
		if (gestorSessionsUsuari.trobaUsuari(codiSessio) == null) {
			throw new GestorExcepcions(ERROR_USUARI_INEXISTENT);
		}
		
		// Esborra la sessió desada
		gestorSessionsUsuari.esborraSessio(codiSessio);
	}
}
