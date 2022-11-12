package com.eschoolmanager.server.connexio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.JSONException;
import org.json.JSONObject;

import com.eschoolmanager.server.gestors.GestorExcepcions;
import com.eschoolmanager.server.gestors.GestorPeticions;

/**
 * @author Gayané Akulyan Akulyan
 * Classe Fil que atén la petició d'un client
 */
public class FilClient extends Thread {

	private Socket client;
    private Scanner in;
    private PrintWriter out;
    private int numeroClient;
    private EntityManager entityManager;

	private final static String RESPOSTA = "resposta";
	private final static String RESPOSTA_NOK = "NOK";
	private final static String MISSATGE = "missatge";
	
    /**
     * Constructor que crea un Fil per un client
     * @param client flux de dades amb un client
     * @param numeroClient número del client
     * @throws GestorExcepcions en cas d'error
     */
	public FilClient(Socket client, int numeroClient, EntityManager entityManager) throws GestorExcepcions {
		
		this.entityManager = entityManager;
		
		try {
            this.client = client;
            this.in = new Scanner(client.getInputStream());
            this.out = new PrintWriter(client.getOutputStream(), true);
            this.numeroClient = numeroClient;
        } catch (IOException ex) {
            throw new GestorExcepcions("S'ha produit un error");
        } 
	}
	
	/**
     * Executa l'intercavi de dades del client amb el servidor
     */
    @Override
    public void run() {
        while(!this.client.isClosed()) {
        	String peticio = this.in.nextLine();
        	System.out.println("Client " + numeroClient + " => Peticio:" + peticio);
    		
        	GestorPeticions gestorPeticions = new GestorPeticions(entityManager);
        	String resposta = gestorPeticions.generaResposta(peticio);
        	
        	System.out.println("Client " + numeroClient + " => Resposta:" + resposta);
            
            this.out.println(resposta);
            
            try {
				this.client.close();
            } catch (IOException ex) {
            	GestorPeticions.generaRespostaNOK("S'ha produit un error en tancar la comunicació");
            } 
        }
        
        System.out.println("Client " + numeroClient + " desconnectat");
    }
}
