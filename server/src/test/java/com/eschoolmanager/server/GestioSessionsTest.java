/**
 * 
 */
package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gayané Akulyan Akulyan
 * Classe per comprovar el funcionament de les gestions de sessions educatives
 * 
 *
 */
public class GestioSessionsTest extends BaseTest {

	/**
     * Neteja la base de dades i l'omple amb dades de prova
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	super.setUp();
    }
    
    /**
     * Tanca l'entityManager i la factoria d'entitats en acabar els tests
     */
    @After()
    public void classEnds() {
    	super.classEnds();
    }
    
    /**
     * Mètode que prova donar d'alta una sessió amb un usuari autoritzat i professor i servei i estudiant existents
     */
    @Test
    public void provaAltaSessioAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2022-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova donar d'alta una sessió amb un usuari no autoritzat
     */
    @Test
    public void provaAltaSessioNoAutoritzatDadesCorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaFinancer");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2022-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta una sessió amb un usuari autoritzat i professor i/o servei i/o estudiant inexistents
     */
    @Test
    public void provaAltaSessioAutoritzatDadesIncorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 50);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2022-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_PROFESSOR_ESTUDIANT_SERVEI, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta una sessió amb un usuari autoritzat i empleat no professor
     */
    @Test
    public void provaAltaSessioAutoritzatProfessorIncorrecte() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 14);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2022-12-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_PROFESSOR, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta una sessió amb un usuari autoritzat i un servei no impartit pel professor
     */
    @Test
    public void provaAltaSessioAutoritzatServeiIncorrecte() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, 12);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 6);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2022-12-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_RELACIO_PROFESSOR_SERVEI, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta una sessió amb dades incompletes
     */
    @Test
    public void provaAltaSessioDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, 20);
    	dadesPeticio.put(DADES_CODI_SERVEI, 5);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2022-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
}
