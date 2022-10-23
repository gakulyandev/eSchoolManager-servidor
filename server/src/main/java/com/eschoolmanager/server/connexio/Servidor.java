package com.eschoolmanager.server.connexio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Gayané Akulyan Akulyan
 * Classe servidor que espera i atén les connexions de diversos clients
 */
public class Servidor {

	/**
	 * Constructor per defecte sense paràmetres
	 * obre un socket a espera de clients
	 */
	public void executa() {
		try {
            //Creació del socket
    		Integer port = Integer.parseInt(System.getenv("PORT"));
            ServerSocket servidor  = new ServerSocket(port);
            
            //Bucle infinit esperant connexions
            int numeroClient=1;
            while(true) {
                System.out.println("Esperant client...");
                Socket socket = servidor.accept();
                System.out.println("Client " + numeroClient + " connectat");
                
                //Creació d'un nou fil per el nou client connectat
                new FilClient(socket, numeroClient).start();
                numeroClient++;
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

}