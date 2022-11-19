/**
 * 
 */
package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	private final static String CRIDA_LLISTA = "LLISTA DEPARTAMENTS";
	private final static String CRIDA_CONSULTA = "CONSULTA DEPARTAMENT";
	private final static String CRIDA_MODI = "MODI DEPARTAMENT";
	private final static String DADES_NOM_DEPARTAMENT = "nomDepartament";
	private final static String DADES_CODI_DEPARTAMENT = "codiDepartament";
	private final static String DADES_PERMISOS = "permisos";
	private final static String PERMIS = "departament";
	
	private final static String ERROR_NO_AUTORITZAT = "L'usuari no està autoritzat per aquesta acció";
	private final static String ERROR_DUPLICAT = "Ja existeix un departament amb el mateix nom";
	private final static String ERROR_INEXISTENT = "No existeix el departament indicat";
	private final static String ERROR_CAMP = "No existeix el valor indicat";
	private final static String ERROR_FALTEN_DADES = "Falten dades";
	
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
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Financer");
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
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Financer");
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
    public void provaAltaDepartamentAutoritzatDadesDuplicades() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
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
     * Mètode que prova donar d'alta un departament amb dades incompletes
     */
    @Test
    public void provaAltaDepartamentDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_ALTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Financer");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar departaments amb un usuari autoritzat
     */
    @Test
    public void provaLlistaDepartamentsAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CAMP, "nom");
    	dadesPeticio.put(DADES_ORDRE, "ASC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Administrador", dadesResposta.getJSONObject("0").get(DADES_NOM_DEPARTAMENT));
    }
    
    /**
     * Mètode que prova llistar departaments amb un usuari no autoritzat
     */
    @Test
    public void provaLlistaDepartamentsNoAutoritzat() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CAMP, "nom");
    	dadesPeticio.put(DADES_ORDRE, "DESC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar departaments amb un usuari autoritzat i dades incorrectes
     */
    @Test
    public void provaLlistaDepartamentsAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CAMP, "nomS");
    	dadesPeticio.put(DADES_ORDRE, "DESC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_CAMP, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova llistar departaments amb dades incompletes
     */
    @Test
    public void provaLlistaDepartamentsDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LLISTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_ORDRE, "DESC");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }

	/**
     * Mètode que prova consultar un departament amb un usuari autoritzat i departament existent
     */
    @Test
    public void provaConsultaDepartamentAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, 3);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	dadesResposta = resposta.getJSONObject(DADES);
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
        assertEquals("Docent", dadesResposta.get(DADES_NOM_DEPARTAMENT));
        assertTrue(dadesResposta.get(DADES_PERMISOS) != null);
    }
    
    /**
     * Mètode que prova consultar un departament amb un usuari no autoritzat i departament existent
     */
    @Test
    public void provaConsultaDepartamentNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, 3);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_NO_AUTORITZAT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un departament amb un usuari autoritzat i departament inexistent
     */
    @Test
    public void provaConsultaDepartamentAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, 4);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_INEXISTENT, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova consultar un departament amb dades incompletes
     */
    @Test
    public void provaConsultaDepartamentDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_CONSULTA);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }
    
    /**
     * Mètode que prova modificar un departament amb un usuari autoritzat i departament existent
     */
    @Test
    public void provaModiDepartamentAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "3");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Financer");
    	dadesPeticioPermisos.put(PERMIS, true);
    	dadesPeticio.put(DADES_PERMISOS, dadesPeticioPermisos);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_OK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova modificar un departament amb un usuari no autoritzat
     */
    @Test
    public void provaModiDepartamentNoAutoritzatDadesCorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministratiu");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "3");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Financer");
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
     * Mètode que prova modificar un departament amb un usuari autoritzat i departament inexistent
     */
    @Test
    public void provaModiDepartamentAutoritzatDadesIncorrectes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "10");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Administratiu");
    	dadesPeticioPermisos.put(PERMIS, true);
    	dadesPeticio.put(DADES_PERMISOS, dadesPeticioPermisos);
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
    }
    
    /**
     * Mètode que prova modificar un departament amb un usuari autoritzat i departament existent amb el mateix nom
     */
    @Test
    public void provaModiDepartamentAutoritzatDadesDuplicades() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_CODI_DEPARTAMENT, "3");
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
     * Mètode que prova modificar un departament amb dades incompletes
     */
    @Test
    public void provaModiDepartamentDadesIncompletes() {
        
    	//Petició del client
        peticio.put(CRIDA, CRIDA_MODI);
        peticio.put(CODI_SESSIO, "codiProvaAdministrador");
    	dadesPeticio.put(DADES_NOM_DEPARTAMENT, "Financer");
    	peticio.put(DADES, dadesPeticio);

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(RESPOSTA_NOK, resposta.get(RESPOSTA));
        assertEquals(ERROR_FALTEN_DADES, resposta.get(MISSATGE));
    }

}
