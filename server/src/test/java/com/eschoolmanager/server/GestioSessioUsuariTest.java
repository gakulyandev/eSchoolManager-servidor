/**
 * 
 */
package com.eschoolmanager.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.json.JSONObject;

import com.eschoolmanager.server.gestors.GestorPeticions;
import com.eschoolmanager.server.model.*;

/**
 * @author Gayané Akulyan Akulyan
 * Classe per comprovar el funcionament de les peticions de login
 */
public class GestioSessioUsuariTest {

	private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private GestorPeticions gestorPeticions;
    private JSONObject peticio, dadesPeticio, resposta, dadesResposta;
	private final static String CRIDA = "crida";
    private final static String PERSISTENCE_UNIT = "eSchoolManager";
	private final static String CRIDA_LOGIN = "LOGIN";
	private final static String CRIDA_LOGOUT = "LOGOUT";
	private final static String CODI_SESSIO = "codiSessio";
	private final static String DADES = "dades";
	private final static String DADES_NOM_USUARI = "usuari";
	private final static String DADES_CONTRASENYA = "contrasenya";
	private final static String DADES_CODI_DEPARTAMENT = "codiDepartament";
	private final static String DADES_CODI_SESSIO = "codiSessio";
	private final static String DADES_NOM = "nom";
	private final static String RESPOSTA = "resposta";
	private final static String RESPOSTA_OK = "OK";
	private final static String RESPOSTA_NOK = "NOK";
	private final static String MISSATGE = "missatge";

	/**
     * Neteja la base de dades i l'omple amb dades de prova
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	obreGestor();
    	generaDades();
        peticio = new JSONObject();
        dadesPeticio = new JSONObject();
        dadesResposta = new JSONObject();
    }
    
    /**
     * Tanca l'entityManager i la factoria d'entities en acabar els tests 
     * manager
     */
    @After()
    public void classEnds() {
    	peticio = null;
        dadesPeticio = null;
        dadesResposta = null;
    	tancaGestor();
    }
    
    /**
     * Mètode que prova iniciar sessió amb un usuari i contrasenya correctes
     */
    @Test
    public void provaLoginUsuariContrasenyaCorrectes() {
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
    public void provaLogoutCodiSessioCorrecte() {
    	//Petició del client
        peticio.put(CRIDA, CRIDA_LOGOUT);
    	peticio.put(CODI_SESSIO, "codiPROVA");

    	//Resposta del servidor una vegada processada la petició
    	resposta = new JSONObject(gestorPeticions.generaResposta(peticio.toString()));
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_OK);
    }
    
    /**
     * Mètode que prova tancar sessió amb dades incompletes
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
     * Mètode que prova tancar sessió amb un codi de sessió incorrecte
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
    
    /**
     * Genera dades d'exemple a la base de dades
     */
    private void generaDades() {
    	Escola escola = new Escola("Escola Prova", "c/Prova, 1", "934445556");
    	escola.setCodi(1);	
    	insertaDades(escola);
        entityManager.getTransaction().begin();        
        escola = (Escola) entityManager.find(Escola.class, 1);       
        entityManager.getTransaction().commit();
        
        
        Departament departament = new Departament (escola, "Administrador");
        departament.setCodi(1);
        List<Departament> departaments = new ArrayList();
		departaments.add(departament);
		escola.setDepartaments(departaments);
    	insertaDades(escola);
    	
        entityManager.getTransaction().begin();        
        escola = (Escola) entityManager.find(Escola.class, 1);       
        entityManager.getTransaction().commit();

        
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = null;
		try {
			parsed = format.parse("1990-10-28");
		} catch (java.text.ParseException e) {
			System.out.println("S'ha produït un error");
		}
		Empleat empleat = new Empleat("22233344N", "Pedro", "Gomez", new Date(parsed.getTime()), "622555222", "p.gomez@gmail.com", "c/S/N, 4", departament);
		empleat.setCodi(1);	
		List<Empleat> empleats = new ArrayList();
		empleats.add(empleat);
		departament.setEmpleats(empleats);
		
		
		Usuari usuari = new Usuari(escola, "p.gomez", "passtest1");
		usuari.setCodi(1);	
		usuari.setCodiSessio("codiProva");	
		empleat.setUsuari(usuari);
		usuari.setEmpleat(empleat);
		
		List<Usuari> usuaris = new ArrayList();
		usuaris.add(usuari);
		escola.setUsuaris(usuaris);
		
		insertaDades(escola);
    }
    
    /**
     * Inserta dades d'exemple a la base de dades
     */
    private void insertaDades(Object objecte) {
		entityManager.getTransaction().begin();
        entityManager.persist(objecte);
        entityManager.getTransaction().commit();
        entityManager.detach(objecte);    
    }
    
    /**
     * Obre l'entityManager
     */
    private void obreGestor() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            entityManager = entityManagerFactory.createEntityManager();
            
            gestorPeticions = new GestorPeticions(entityManager);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tanca l'entityManager
     */
    private void tancaGestor() {
    	entityManager.clear();
    	entityManager.close();
        entityManagerFactory.close();
    }

}
