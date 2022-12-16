package com.eschoolmanager.server.seguretat;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.eschoolmanager.server.gestors.GestorExcepcions;
import com.eschoolmanager.server.utilitats.Constants;

/**
 * @author Gayané Akulyan Akulyan
 * Classe que processa la encriptació i desencriptació de les peticions i respostes
 */
public class Seguretat implements Constants {

	public final static String ALGORITME = "AES";
	public final static String CLAU = "IOC";
	private Cipher cipher;
	private SecretKey clau;
	private int midaClau = 16;
	
	
	/**
	 * Constructor que crea la clau d'encriptació
	 */
	public Seguretat() {
		clau = new SecretKeySpec(Arrays.copyOf(CLAU.getBytes(), midaClau), ALGORITME);
	}
	
	/**
	 * Encripta el missatge a enviar al client
	 * @param missatge
	 * @return missatge encriptat
	 * @throws GestorExcepcions
	 */
	public String encripta(String missatge) throws GestorExcepcions {
		byte[] missatgeEncriptat = null;
		try {
			cipher = Cipher.getInstance(ALGORITME);

			cipher.init(Cipher.ENCRYPT_MODE, clau);
			missatgeEncriptat = cipher.doFinal(missatge.getBytes());
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new GestorExcepcions(ERROR_GENERIC);
		} 
		
		return Base64.getEncoder().encodeToString(missatgeEncriptat);
	}
	
	/**
	 * Desencripta el missatge enviat pel client
	 * @param missatge encriptat
	 * @return missatge desencriptat
	 * @throws GestorExcepcions
	 */
	public String desencripta(String missatge) throws GestorExcepcions {
		String missatgeDesencriptat = null;
		try {
			cipher = Cipher.getInstance(ALGORITME);

			cipher.init(Cipher.DECRYPT_MODE, clau);
			byte[] missatgeBytes = cipher.doFinal(Base64.getDecoder().decode(missatge));
			missatgeDesencriptat = new String(missatgeBytes);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new GestorExcepcions(ERROR_GENERIC);
		} 
		
		return missatgeDesencriptat;
	}
}

