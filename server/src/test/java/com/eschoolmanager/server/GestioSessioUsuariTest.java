/**
 * 
 */
package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.json.JSONObject;

/**
 * @author Gayané Akulyan Akulyan
 * Classe per comprovar el funcionament de les peticions de login
 */
public class GestioSessioUsuariTest extends BaseTest {

	private final static String CRIDA_LOGIN = "LOGIN";
	private final static String CRIDA_LOGOUT = "LOGOUT";
	private final static String DADES_NOM_USUARI = "usuari";
	private final static String DADES_CONTRASENYA = "contrasenya";
	private final static String DADES_NOM_DEPARTAMENT = "nomDepartament";
	private final static String DADES_CODI_SESSIO = "codiSessio";
	private final static String DADES_NOM = "nom";
	private final static String DADES_PERMISOS = "permisos";
	
	private final static String ERROR_USUARI_INEXISTENT = "No existeix cap usuari amb les dades indicades";
	private final static String ERROR_DADES = "Falten dades";
    
    /**
     * Mètode que prova iniciar sessió amb un usuari i contrasenya correctes
     */
    @Test
    public void provaLoginDadesCorrectes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGIN);
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	dadesPeticio.put(DADES_CONTRASENYA, "passtest1");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Administrador", dadesResposta.get(DADES_NOM_DEPARTAMENT));
        assertEquals("Pedro", dadesResposta.get(DADES_NOM));
        assertTrue(dadesResposta.get(DADES_PERMISOS) != null);
        assertTrue(dadesResposta.get(DADES_CODI_SESSIO) != null);
    }
    
    /**
     * Mètode que prova iniciar sessió amb dades incorrectes
     */
    @Test
    public void provaLoginDadesIncorrectes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGIN);
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	dadesPeticio.put(DADES_CONTRASENYA, "passtestFALSE");
    	peticio.put(DADES, dadesPeticio);
    	
    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_USUARI_INEXISTENT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova iniciar sessió amb dades incompletes
     */
    @Test
    public void provaLoginDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGIN);
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	peticio.put(DADES, dadesPeticio);
    	
    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova tancar sessió amb un codi de sessió correcte
     */
    @Test
    public void provaLogoutDadesCorrectes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGOUT);
    	peticio.put(CODI_SESSIO, "codiProvaAdministrador");

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova tancar sessió amb un codi de sessió incorrecte
     */
    @Test
    public void provaLogoutDadesIncorrectes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGOUT);
    	peticio.put(CODI_SESSIO, "codiProva");

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_USUARI_INEXISTENT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova tancar sessió amb dades incompletes
     */
    @Test
    public void provaLogoutDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGOUT);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DADES, resposta.get(MISSATGE));
    }
}
