package guiao3;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * 
 * @author luisp
 * https://www.devmedia.com.br/utilizando-criptografia-simetrica-em-java/31170
 */

public class Program {

	public static void main(String[] args) {
		try {
			KeyGenerator keygenerator = KeyGenerator.getInstance("DES"); //implementa um gerador de chaves simétricas --> DES - cifra simetrica por blocos
			SecretKey chaveDES = keygenerator.generateKey(); //implementa uma chave simétrica

			// Cria a cifra
			Cipher cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); //Cipher - permite realizar operações de cifra e decifra

			// Inicializa a cifra para o processo de ENCRIPTACAO
			cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES); //ENCRYPT_MODE

			// Texto puro
			byte[] textoPuro = "Exemplo de texto puro".getBytes();

			System.out.println("Texto [Formato de Byte] : " + textoPuro);
			System.out.println("Texto Puro : " + new String(textoPuro));

			// Texto encriptado
			byte[] textoEncriptado = cifraDES.doFinal(textoPuro);

			System.out.println("Texto Encriptado : " + textoEncriptado);

			// Inicializa a cifra também para o processo de DESINCRIPTACAO
			cifraDES.init(Cipher.DECRYPT_MODE, chaveDES); //DECRYPT_MODE

			// Decriptografa o texto
			byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado);

			System.out.println("Texto Decriptografado : " + new String(textoDecriptografado));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

	}
}
