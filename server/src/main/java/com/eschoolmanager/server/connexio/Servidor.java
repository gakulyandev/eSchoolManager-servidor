package com.eschoolmanager.server.connexio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.eschoolmanager.server.gestors.GestorExcepcions;
import com.eschoolmanager.server.gestors.GestorPeticions;
import com.eschoolmanager.server.gestors.GestorSessionsUsuari;

/**
 * @author Gayané Akulyan Akulyan
 * Classe servidor que espera i atén les connexions de diversos clients
 */
public class Servidor {

	private Integer port;
	private ServerSocket server;
	
	private final static String ENV_PORT = "PORT";
	private final static String PERSISTENCE_UNIT = "eSchoolManager";
	private final static String ERROR_GENERIC = "S'ha produit un error";

	/**
	 * Constructor per defecte sense paràmetres
	 * obre un socket a espera de clients
	 * @throws GestorExcepcions en cas d'error 
	 */
	public void executa() throws GestorExcepcions {
		
		try {
            //Creació del socket
    		port = Integer.parseInt(System.getenv(ENV_PORT));
            server = new ServerSocket(port);
            
            //Inicialització del gestor de peticions
    		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    		EntityManager entityManager = entityManagerFactory.createEntityManager();
    		GestorSessionsUsuari gestorSessionsUsuari = new GestorSessionsUsuari();
    		GestorPeticions gestorPeticions = new GestorPeticions(entityManager, gestorSessionsUsuari);
            
            //Bucle infinit esperant connexions
            int numeroClient=1;
            while(true) {
                System.out.println("Esperant client...");
                Socket socket = server.accept();
                System.out.println("Client " + numeroClient + " connectat");
                
                //Creació d'un nou fil per el nou client connectat
                new FilClient(socket, numeroClient, gestorPeticions).start();
                numeroClient++;
            }
            
        } catch (IOException ex) {
            throw new GestorExcepcions(ERROR_GENERIC);
        }
	}

}
