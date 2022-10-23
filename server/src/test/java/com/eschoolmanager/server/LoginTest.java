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

import com.eschoolmanager.server.model.*;
import com.google.protobuf.TextFormat.ParseException;

/**
 * @author Gayané Akulyan Akulyan
 * Classe per comprovar el funcionament de les peticions de login
 */
public class LoginTest {

	private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JSONObject peticio, dadesPeticio, resposta, dadesResposta;
	private final static String CRIDA = "crida";
    private final static String PERSISTENCE_UNIT = "eSchoolManager";
	private final static String CRIDA_LOGIN = "LOGIN";
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
        insertaDades();
        peticio = new JSONObject();
        peticio.put(CRIDA, CRIDA_LOGIN);
        dadesPeticio = new JSONObject();
        resposta = new JSONObject();
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
        resposta = null;
        dadesResposta = null;
    	tancaGestor();
    }
    
    /**
     * Mètode que prova iniciar sessió amb un usuari i contrasenya correctes
     */
    @Test
    public void provaLoginUsuariContrasenyaCorrectes() {
    	//Petició del client
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	dadesPeticio.put(DADES_CONTRASENYA, "passtest1");
    	peticio.put(DADES, dadesPeticio);
    	
    	//Resposta del servidor una vegada processada la petició
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_OK);
        assertEquals(dadesResposta.get(DADES_CODI_DEPARTAMENT), 1);
        assertEquals(dadesResposta.get(DADES_NOM), "Pedro");
        assertTrue(dadesResposta.get(DADES_CODI_SESSIO) != null);
    }
    
    /**
     * Mètode que prova iniciar sessió amb un usuari i contrasenya incorrectes
     */
    @Test
    public void provaLoginUsuariContrasenyaIncorrectes() {
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	dadesPeticio.put(DADES_CONTRASENYA, "passtestFALSE");
    	peticio.put(DADES, dadesPeticio);
    	
    	//Resposta del servidor una vegada processada la petició
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_NOK);
        assertEquals(resposta.get(MISSATGE), "No existeix cap usuari amb les dades indicades");
    }
    
    /**
     * Mètode que prova iniciar sessió amb dades incompletes
     */
    @Test
    public void provaLoginUsuariDadesIncompletes() {
    	dadesPeticio.put(DADES_NOM_USUARI, "p.gomez");
    	peticio.put(DADES, dadesPeticio);
    	
    	//Resposta del servidor una vegada processada la petició
    	
    	//Comprovació
        assertEquals(resposta.get(RESPOSTA), RESPOSTA_NOK);
        assertEquals(resposta.get(MISSATGE), "Falten dades");
    }
    
    /**
     * Inserta dades d'exemple a la base de dades
     */
    private void insertaDades() {
    	Escola escola = new Escola("Escola Prova", "c/Prova, 1", "934445556");		
		entityManager.getTransaction().begin();
        entityManager.persist(escola);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();        
        escola = (Escola) entityManager.find(Escola.class, 1);       
        entityManager.getTransaction().commit();
        
        Departament departament1 = new Departament (escola, "Administrador");
		Departament departament2 = new Departament (escola, "Administratiu");
		Departament departament3 = new Departament (escola, "Docent");
		
		List<Departament> departaments = new ArrayList();
		departaments.add(departament1);
		departaments.add(departament2);
		departaments.add(departament3);
		escola.setDepartaments(departaments);
		
		entityManager.getTransaction().begin();
        entityManager.persist(escola);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();        
		escola = (Escola) entityManager.find(Escola.class, 1);       
        entityManager.getTransaction().commit();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed1 = null, parsed2 = null, parsed3 = null;
		try {
			parsed1 = format.parse("1990-10-28");
			parsed2 = format.parse("1987-05-10");
			parsed3 = format.parse("1991-05-11");
		} catch (java.text.ParseException e) {
			System.out.println("S'ha produït un error");
		}
		Empleat empleat1 = new Empleat("22233344N", "Pedro", "Gomez", new Date(parsed1.getTime()), "622555222", "p.gomez@gmail.com", "c/S/N, 4", departament1);
		Empleat empleat2 = new Empleat("55533344N", "Clara", "Carrillo", new Date(parsed2.getTime()), "655666558", "c.carrillo@icloud.com", "c/Del Mar, 5", departament2);
		Empleat empleat3 = new Empleat("45628915M", "Blas", "Roig", new Date(parsed3.getTime()), "658656558", "b.roig@gmail.com", "c/Del Pino, 1", departament3);
		
		List<Empleat> empleats1 = new ArrayList();
		empleats1.add(empleat1);
		departament1.setEmpleats(empleats1);
		
		List<Empleat> empleats2 = new ArrayList();
		empleats2.add(empleat2);
		departament2.setEmpleats(empleats2);
		
		List<Empleat> empleats3 = new ArrayList();
		empleats3.add(empleat3);
		departament1.setEmpleats(empleats3);
		
		entityManager.getTransaction().begin();
        entityManager.persist(escola);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();        
		escola = (Escola) entityManager.find(Escola.class, 1);       
        entityManager.getTransaction().commit();
		
		
		Usuari usuari1 = new Usuari(escola, "p.gomez", "passtest1");
		Usuari usuari2 = new Usuari(escola, "c.carrillo", "passtest2");
		Usuari usuari3 = new Usuari(escola, "b.roig", "passtest3");
		usuari3.setEscola(escola);
		empleat1.setUsuari(usuari1);
		empleat2.setUsuari(usuari2);
		empleat3.setUsuari(usuari3);
		usuari1.setEmpleat(empleat1);
		usuari2.setEmpleat(empleat2);
		usuari3.setEmpleat(empleat3);

		List<Usuari> usuaris = new ArrayList();
		usuaris.add(usuari1);
		usuaris.add(usuari2);
		usuaris.add(usuari3);
		escola.setUsuaris(usuaris);
		
		entityManager.getTransaction().begin();
        entityManager.persist(escola);
        entityManager.getTransaction().commit();
    }
    
    /**
     * Obre l'entityManager i gestors
     */
    private void obreGestor() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            entityManager = entityManagerFactory.createEntityManager();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tanca l'entityManager
     */
    private void tancaGestor() {
    	entityManager.close();
        entityManagerFactory.close();
    }

}
