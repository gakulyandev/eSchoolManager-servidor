package com.eschoolmanager.server.app;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.eschoolmanager.server.connexio.Servidor;
import com.eschoolmanager.server.gestors.GestorExcepcions;
import com.eschoolmanager.server.model.Departament;
import com.eschoolmanager.server.model.Empleat;
import com.eschoolmanager.server.model.Escola;
import com.eschoolmanager.server.model.Usuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que executa l'aplicació servidor
 */
public class App 
{
	public static void main( String[] args ) {
    	
    	//Executar servidor a espera de clients
    	Servidor servidor = new Servidor();
    	try {
    		/**DADES EXEMPLE A ELIMINAR**/
    		altaDadesExemple();
    		
			servidor.executa();
			
		} catch (GestorExcepcions e) {
			System.out.println(e.getMessage());
		}
    }
	
	/**
	 * Mètode que inserta dades de prova a la base de dades A ELIMINAR
	 */
	private static void altaDadesExemple() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("eSchoolManager");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		// DADES D'EXEMPLE A ELIMINAR
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
		} catch (ParseException e) {
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
}
