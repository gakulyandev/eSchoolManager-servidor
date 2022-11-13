package com.eschoolmanager.server.connexio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
    private GestorPeticions gestorPeticions;
    
	private final static String ERROR_GENERIC = "S'ha produit un error";
	
    /**
     * Constructor que crea un Fil per un client
     * @param client flux de dades amb un client
     * @param numeroClient número del client
     * @param gestorPeticions dels clients
     * @throws GestorExcepcions en cas d'error
     */
	public FilClient(Socket client, int numeroClient, GestorPeticions gestorPeticions) throws GestorExcepcions {
		
		this.gestorPeticions = gestorPeticions;
		
		try {
            this.client = client;
            this.in = new Scanner(client.getInputStream());
            this.out = new PrintWriter(client.getOutputStream(), true);
            this.numeroClient = numeroClient;
        } catch (IOException ex) {
            throw new GestorExcepcions(ERROR_GENERIC);
        } 
	}
	
	/**
     * Executa l'intercavi de dades del client amb el servidor
     */
    @Override
    public void run() {
    	
        while(!this.client.isClosed()) {
        	
        	if (this.in.hasNextLine()) {
            	String peticio = this.in.nextLine();
            	System.out.println("Client " + numeroClient + " => Peticio:" + peticio);
            	
            	String resposta = gestorPeticions.generaResposta(peticio);
            	System.out.println("Client " + numeroClient + " => Resposta:" + resposta);
                
                this.out.println(resposta);
                
                try {
    				this.client.close();
                } catch (IOException ex) {
                	GestorPeticions.generaRespostaNOK(ERROR_GENERIC);
                }
        	}
        }
        
        System.out.println("Client " + numeroClient + " desconnectat");
    }
}
