/**
 * 
 */
package com.eschoolmanager.server.gestors;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que captura i personalitza les excepcions llançades a l'aplicació
 */
@SuppressWarnings("serial")
public class GestorExcepcions extends Exception {

	/**
     * Construeix una excepcio
     * @param message missatge de l'excepcio
     */
	public GestorExcepcions(String missatge) {
		super(missatge);
	}

}
