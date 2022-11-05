/**
 * 
 */
package com.eschoolmanager.server;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;

import com.eschoolmanager.server.gestors.GestorPeticions;
import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Escola;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe base que extenen els tests amb funcionalitats genèriques
 */
public class BaseTest {
	
	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;
	protected GestorPeticions gestorPeticions;
	protected JSONObject peticio, dadesPeticio, resposta, dadesResposta;
	protected final static String CRIDA = "crida";
	protected final static String PERSISTENCE_UNIT = "eSchoolManager";
	
	protected final static String CODI_SESSIO = "codiSessio";
	protected final static String DADES = "dades";
	protected final static String RESPOSTA = "resposta";
	protected final static String RESPOSTA_OK = "OK";
	protected final static String RESPOSTA_NOK = "NOK";
	protected final static String MISSATGE = "missatge";
    
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
     * Tanca l'entityManager i la factoria d'entitats en acabar els tests
     */
    @After()
    public void classEnds() {
    	peticio = null;
        dadesPeticio = null;
        dadesResposta = null;
    	tancaGestor();
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
        
        
        
        Departament departament1 = new Departament (escola, "Administrador");
        departament1.setCodi(1);
        Departament departament2 = new Departament (escola, "Administratiu");
        departament2.setCodi(2);
        List<Departament> departaments = new ArrayList();
		departaments.add(departament1);
		departaments.add(departament2);
		escola.setDepartaments(departaments);
    	insertaDades(escola);
    	
        entityManager.getTransaction().begin();        
        escola = (Escola) entityManager.find(Escola.class, 1);       
        entityManager.getTransaction().commit();

        
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed1 = null, parsed2 = null, parsed3 = null;
		try {
			parsed1 = format.parse("1990-10-28");
			parsed2 = format.parse("1987-05-10");
		} catch (ParseException e) {
			System.out.println("S'ha produït un error");
		}
		Empleat empleat1 = new Empleat("22233344N", "Pedro", "Gomez", new Date(parsed1.getTime()), "622555222", "p.gomez@gmail.com", "c/S/N, 4", departament1);
		Empleat empleat2 = new Empleat("55533344N", "Clara", "Carrillo", new Date(parsed2.getTime()), "655666558", "c.carrillo@icloud.com", "c/Del Mar, 5", departament2);
		
		List<Empleat> empleats1 = new ArrayList();
		empleats1.add(empleat1);
		departament1.setEmpleats(empleats1);
		
		List<Empleat> empleats2 = new ArrayList();
		empleats2.add(empleat2);
		departament2.setEmpleats(empleats2);
		
		
		
		Usuari usuari1 = new Usuari(escola, "p.gomez", "passtest1");
		usuari1.setCodi(1);	
		usuari1.setCodiSessio("codiProva1");	
		Usuari usuari2 = new Usuari(escola, "c.carrillo", "passtest2");
		usuari2.setCodi(2);	
		usuari2.setCodiSessio("codiProva2");	
		
		empleat1.setUsuari(usuari1);
		empleat2.setUsuari(usuari2);
		usuari1.setEmpleat(empleat1);
		usuari2.setEmpleat(empleat2);
		
		List<Usuari> usuaris = new ArrayList();
		usuaris.add(usuari1);
		usuaris.add(usuari2);
		escola.setUsuaris(usuaris);
		
		insertaDades(escola);
    }
}
