/**
 * 
 */
package com.eschoolmanager.server.gestors;

import java.util.ArrayList;
import java.util.List;

import com.eschoolmanager.server.model.SessioUsuari;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 *
 */
public class GestorSessionsUsuari {
	
	private List<SessioUsuari> sessions;

	/**
	 * Constructor que inicialitza un llistat de sessions per emmagatzemar durant la vigència d'aquestes
	 */
	public GestorSessionsUsuari() {
		this.sessions = new ArrayList<SessioUsuari>();
	}
	
	/**
	 * Desa una sessió iniciada d'un usuari logat
	 * @param sessio a desar
	 */
	public void desaSessio(SessioUsuari sessio) {		
		this.sessions.add(sessio);
	}
	
	/**
	 * Troba la sessió iniciada amb el codi indicat
	 * @param codi de la sessió a trobar
	 */
	public SessioUsuari trobaSessio(String codi) {
		for(SessioUsuari sessio : sessions) {
			if (sessio.getCodi().equals(codi)) {
				return sessio;
			}
		}
		
		return null;
	}
	
	/**
	 * Esborra una sessió iniciada d'un usuari que ha tancat la sessió
	 * @param codi de la sessió a esborrar
	 */
	public void esborraSessio(String codi) {
		SessioUsuari sessio = trobaSessio(codi);
		if (sessio != null) {
			sessions.remove(sessio);
		}
	}
}
