package controller;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 
 * <p>
 * Crypteur et décrytpeur de l'application : pour des raisons de sécurité,
 * certaine données doivent être cryptées
 * </p>
 * 
 * @author Nathan Ponson
 */
public class Jasypt {

	/**
	 * Mot de passe permettant de crypter et décrypter les données
	 */
	private char[] password;

	/**
	 * <p>
	 * Crée le décrypteur
	 * </p>
	 * 
	 * @author Nathan Ponson
	 */
	public Jasypt() {
		// Mot de passe permettant de chiffrer et déchiffrer
		password = "4C45Lrh26YWbKz73FwtXYkc6".toCharArray();
	}

	/**
	 * <p>
	 * Crypte les données
	 * </p>
	 * 
	 * @author Nathan Ponson
	 * @param text Les données à crypter
	 * @return Les données cryptées
	 */
	public String encrypt(String text) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPasswordCharArray(password);
		String encryptedText = textEncryptor.encrypt(text);

		return encryptedText;
	}

	/**
	 * <p>
	 * Décrypte les données
	 * </p>
	 * 
	 * @author Nathan Ponson
	 * @param text Les données à décrypter
	 * @return Les données décryptées
	 */
	public String decrypt(String text) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPasswordCharArray(password);
		String decryptedText = textEncryptor.decrypt(text);

		return decryptedText;
	}
}