/**
 * 
 */
package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.json.JSONObject;

/**
 * @author Gayané Akulyan Akulyan
 * Classe per comprovar el funcionament de les gestions de departaments
 */
public class GestioDepartamentsTest extends BaseTest {

	private final static String CRIDA_ALTA = "ALTA DEPARTAMENT";
	private final static String DADES_NOM_DEPARTAMENT = "nomDepartament";
	private final static String DADES_PERMISOS = "permisos";
	private final static String PERMIS = "departament";
	
	private final static String ERROR_NO_AUTORITZAT = "L'usuari no està autoritzat per aquesta acció";
	private final static String ERROR_DUPLICAT = "Ja existeix un departament amb el mateix nom";
	private final static String ERROR_DADES = "Falten dades";
	
	private JSONObject dadesPeticioPermisos;
	
	/**
     * Neteja la base de dades i l'omple amb dades de prova
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	super.setUp();
    	dadesPeticioPermisos = new JSONObject();
    }
    
    /**
     * Tanca l'entityManager i la factoria d'entitats en acabar els tests
     */
    @After()
    public void classEnds() {
    	super.classEnds();
    	dadesPeticioPermisos = null;
    }

	/**
     * Mètode que prova donar d'alta un departament amb un usuari autoritzat i departament inexistent
     */
    @Test
    public void provaAltaDepartamentAutoritzatDadesCorrectes() {
        peticio.put(CRIDA, CRIDA_ALTA);
    	//Petició del client
        peticio.put(CODI_SESSIO, "codiProva1");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Docent");
    	dadesPeticioPermisos.put(PERMIS, true);
    	dadesPeticio.put(DADES_PERMISOS, dadesPeticioPermisos);
    	peticio.put(DADES, dadesPeticio);


    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
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
    	dadesPeticioPermisos.put(PERMIS, true);
    	dadesPeticio.put(DADES_PERMISOS, dadesPeticioPermisos);
    	peticio.put(DADES, dadesPeticio);


    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
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
    	dadesPeticioPermisos.put(PERMIS, true);
    	dadesPeticio.put(DADES_PERMISOS, dadesPeticioPermisos);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DUPLICAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova donar d'alta un departament amb amb dades incompletes
     */
    @Test
    public void provaAltaDepartamentDadesIncompletes() {
        peticio.put(CRIDA, CRIDA_ALTA);
    	//Petició del client
        peticio.put(CODI_SESSIO, "codiProva1");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Administratiu");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_DADES, resposta.get(MISSATGE));
    }

}
