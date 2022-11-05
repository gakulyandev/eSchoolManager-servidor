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
	private final static String DADES_CODI_DEPARTAMENT = "codiDepartament";
	private final static String DADES_CODI_SESSIO = "codiSessio";
	private final static String DADES_NOM = "nom";
    
    /**
     * Mètode que prova iniciar sessió amb un usuari i contrasenya correctes
     */
    @Test
    public void provaLoginDadesCorrectes() {
        peticio.put(CRIDA, CRIDA_LOGIN);
    	//Petició del client
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	dadesPeticio.put(DADES_CONTRASENYA, "passtest1");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_OK);
        assertEquals(dadesResposta.get(DADES_CODI_DEPARTAMENT), 1);
        assertEquals(dadesResposta.get(DADES_NOM), "Pedro");
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
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_NOK);
        assertEquals(resposta.get(MISSATGE), "No existeix cap usuari amb les dades indicades");
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
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_NOK);
        assertEquals(resposta.get(MISSATGE), "Falten dades");
    }
    
    /**
     * Mètode que prova tancar sessió amb un codi de sessió correcte
     */
    @Test
    public void provaLogoutDadesCorrectes() {
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGOUT);
    	peticio.put(CODI_SESSIO, "codiProva1");

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_OK);
    }
    
    /**
     * Mètode que prova tancar sessió amb un codi de sessió incorrecte
     */
    @Test
    public void provaLogoutDadesIncorrectes() {
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGOUT);
    	peticio.put(CODI_SESSIO, "codiPR");

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_NOK);
        assertEquals(resposta.get(MISSATGE), "No existeix cap usuari amb les dades indicades");
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
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_NOK);
        assertEquals(resposta.get(MISSATGE), "Falten dades");
    }
}
