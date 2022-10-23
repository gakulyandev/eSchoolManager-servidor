package com.eschoolmanager.server.connexio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Gayané Akulyan Akulyan
 * Classe Fil que atén la petició d'un client
 */
public class FilClient extends Thread {

	private Socket client;
    private Scanner in;
    private PrintWriter out;
    private int numeroClient;
	
    /**
     * Constructor que crea un Fil per un client
     * @param client flux de dades amb un client
     * @param numeroClient número del client
     */
	public FilClient(Socket client, int numeroClient) {
		try {
            this.client = client;
            this.in = new Scanner(client.getInputStream());
            this.out = new PrintWriter(client.getOutputStream(), true);
            this.numeroClient = numeroClient;
        } catch (IOException ex) {
        	ex.printStackTrace();
        } 
	}
	
	/**
     * Executa l'intercavi de dades del client amb el servidor
     */
    @Override
    public void run() {

        try {
            while(!this.client.isClosed()) {
                this.out.println("Hello World!");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}