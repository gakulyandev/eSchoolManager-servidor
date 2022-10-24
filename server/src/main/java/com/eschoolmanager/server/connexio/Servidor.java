package com.eschoolmanager.server.connexio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.eschoolmanager.server.gestors.GestorExcepcions;

/**
 * @author Gayané Akulyan Akulyan
 * Classe servidor que espera i atén les connexions de diversos clients
 */
public class Servidor {

	/**
	 * Constructor per defecte sense paràmetres
	 * obre un socket a espera de clients
	 * @throws GestorExcepcions en cas d'error 
	 */
	public void executa() throws GestorExcepcions {
		try {
            //Creació del socket
    		Integer port = Integer.parseInt(System.getenv("PORT"));
            ServerSocket server  = new ServerSocket(port);
            
            //Bucle infinit esperant connexions
            int numeroClient=1;
            while(true) {
                System.out.println("Esperant client...");
                Socket socket = server.accept();
                System.out.println("Client " + numeroClient + " connectat");
                
                //Creació d'un nou fil per el nou client connectat
                new FilClient(socket, numeroClient).start();
                numeroClient++;
                
            }
        } catch (IOException ex) {
            throw new GestorExcepcions("S'ha produit un error");
        }
	}

}
