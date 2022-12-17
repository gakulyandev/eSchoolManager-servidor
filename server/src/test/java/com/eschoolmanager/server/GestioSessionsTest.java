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
    	dadesPeticio.put(DADES_CODI_EMPLEAT, CODI_EXEMPLE_EMPLEAT_DOCENT);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
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
    	dadesPeticio.put(DADES_CODI_EMPLEAT, CODI_EXEMPLE_EMPLEAT_DOCENT);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
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
    	dadesPeticio.put(DADES_CODI_EMPLEAT, CODI_EXEMPLE_INEXISTENT);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
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
    	dadesPeticio.put(DADES_CODI_EMPLEAT, CODI_EXEMPLE_EMPLEAT_ADMINISTRATIU);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2022-12-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_PROFESSOR, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta una sessió amb dades incompletes
     */
    @Test
    public void provaAltaSessioDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2022-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    

    /**
     * Mètode que prova llistar sessions amb un usuari autoritzat
     */
    @Test
    public void provaLlistaSessionsAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_SESSIONS);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CAMP, "codiEmpleat");
    	dadesPeticio.put(DADES_ORDRE, "ASC");
    	dadesPeticio.put(DADES_VALOR, String.valueOf(CODI_EXEMPLE_EMPLEAT_DOCENT));
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Clara", dadesResposta.getJSONObject("0").get(DADES_NOM_ESTUDIANT));
    }
    
    /**
     * Mètode que prova llistar sessions amb un usuari no autoritzat
     */
    @Test
    public void provaLlistaSessionsNoAutoritzat() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_SESSIONS);
        peticio.put(CODI_SESSIO, "codiProvaFinancer");
    	dadesPeticio.put(DADES_CAMP, "codiEmpleat");
    	dadesPeticio.put(DADES_ORDRE, "ASC");
    	dadesPeticio.put(DADES_VALOR, String.valueOf(CODI_EXEMPLE_EMPLEAT_DOCENT));
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar sessions amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaLlistaSessionsAutoritzatDadesIncorrectes() {

    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_SESSIONS);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CAMP, "data");
    	dadesPeticio.put(DADES_ORDRE, "DESC");
    	dadesPeticio.put(DADES_VALOR, String.valueOf(CODI_EXEMPLE_EMPLEAT_DOCENT));
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_CAMP, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar sessions amb dades incompletes
     */
    @Test
    public void provaLlistaSessionsDadesIncompletes() {
    	
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA_SESSIONS);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_ORDRE, "DESC");
    	dadesPeticio.put(DADES_VALOR, String.valueOf(CODI_EXEMPLE_EMPLEAT_DOCENT));
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar una sessió amb un usuari autoritzat i beca existent
     */
    @Test
    public void provaConsultaSessioAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_SESSIO_1);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Clara", dadesResposta.get(DADES_NOM_ESTUDIANT));
    }
    
    /**
     * Mètode que prova consultar una sessió amb un usuari no autoritzat i beca existent
     */
    @Test
    public void provaConsultaSessioNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaFinancer");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_SESSIO_1);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar una sessió amb un usuari autoritzat i beca inexistent
     */
    @Test
    public void provaConsultaSessioAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_INEXISTENT);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_SESSIO, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar una sessió amb dades incompletes
     */
    @Test
    public void provaConsultaSessioDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova modificar una sessió amb un usuari autoritzat i sessió, professor, servei i estudiant existents
     */
    @Test
    public void provaModiSessioAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_SESSIO_1);
    	dadesPeticio.put(DADES_CODI_EMPLEAT, CODI_EXEMPLE_EMPLEAT_DOCENT);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2024-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova modificar una sessió amb un usuari no autoritzat
     */
    @Test
    public void provaModiSessioNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaFinancer");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_SESSIO_1);
    	dadesPeticio.put(DADES_CODI_EMPLEAT, CODI_EXEMPLE_EMPLEAT_DOCENT);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2024-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova modificar una sessió amb un usuari autoritzat i servei inexistent
     */
    @Test
    public void provaModiSessioAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_INEXISTENT);
    	dadesPeticio.put(DADES_CODI_EMPLEAT, CODI_EXEMPLE_EMPLEAT_DOCENT);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2024-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova modificar una sessió amb dades incompletes
     */
    @Test
    public void provaModiSessioDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_EMPLEAT, CODI_EXEMPLE_EMPLEAT_DOCENT);
    	dadesPeticio.put(DADES_CODI_ESTUDIANT, CODI_EXEMPLE_ESTUDIANT_1);
    	dadesPeticio.put(DADES_CODI_SERVEI, CODI_EXEMPLE_SERVEI_PSICOLOGIA);
    	dadesPeticio.put(DADES_DATA_I_HORA, "2024-1-04 16:00:00");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa una sessió amb un usuari autoritzat i sessió existent
     */
    @Test
    public void provaBaixaSessioAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_SESSIO_1);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que donar de baixa una sessió amb un usuari no autoritzat i sessió existent
     */
    @Test
    public void provaBaixaSessioNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaFinancer");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_SESSIO_1);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa una sessió amb un usuari autoritzat i sessió inexistent
     */
    @Test
    public void provaBaixaSessioAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_INEXISTENT);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT_SESSIO, resposta.get(MISSATGE));
    }

    /**
     * Mètode que donar de baixa una sessió amb un usuari autoritzat i sessió facturada
     */
    @Test
    public void provaBaixaSessioAutoritzatSessioFacturada() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	dadesPeticio.put(DADES_CODI_SESSIO, CODI_EXEMPLE_SESSIO_2);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_SESSIO_FACTURADA, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que donar de baixa una sessió amb dades incompletes
     */
    @Test
    public void provaBaixaSessioDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_BAIXA_SESSIO);
        peticio.put(CODI_SESSIO, "codiProvaDocent");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
}
