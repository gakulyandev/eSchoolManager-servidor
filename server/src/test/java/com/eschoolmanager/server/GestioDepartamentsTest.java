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
 * Classe per comprovar el funcionament de les gestions de departaments
 */
public class GestioDepartamentsTest extends BaseTest {

	private final static String CRIDA_ALTA = "ALTA DEPARTAMENT";
	private final static String DADES_NOM_DEPARTAMENT = "nomDepartament";

	/**
     * Mètode que prova donar d'alta un departament amb un usuari autoritzat i departament inexistent
     */
    @Test
    public void provaAltaDepartamentAutoritzatDadesCorrectes() {
        peticio.put(CRIDA, CRIDA_ALTA);
    	//Petició del client
        peticio.put(CODI_SESSIO, "codiProva1");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Docent");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_OK);
    }
    
    /**
     * Mètode que prova donar d'alta un departament amb un usuari no autoritzat
     */
    @Test
    public void provaAltaDepartamentNoAutoritzatDadesCorrectes() {
        peticio.put(CRIDA, CRIDA_ALTA);
    	//Petició del client
        peticio.put(CODI_SESSIO, "codiProva2");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Docent");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_NOK);
        assertEquals(resposta.get(MISSATGE), "L'usuari no està autoritzat per aquesta acció");
    }
    
    /**
     * Mètode que prova donar d'alta un departament amb un usuari autoritzat i departament existent
     */
    @Test
    public void provaAltaDepartamentAutoritzatDadesIncorrectes() {
        peticio.put(CRIDA, CRIDA_ALTA);
    	//Petició del client
        peticio.put(CODI_SESSIO, "codiProva1");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Administratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_NOK);
        assertEquals(resposta.get(MISSATGE), "El departament ja existeix");
    }

}
