package com.eschoolmanager.server.app;

import com.eschoolmanager.server.connexio.Servidor;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que executa l'aplicació servidor
 */
public class App 
{
	public static void main( String[] args ) {
    	
    	//Executar servidor a espera de clients
    	Servidor servidor = new Servidor();
    	servidor.executa();
    }
}