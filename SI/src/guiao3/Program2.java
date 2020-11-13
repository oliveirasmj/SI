package guiao3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

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

public class Program2 {

	public static void main(String[] args) {
		try {
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Insira o nome do ficheiro: ");
			String fileName = sc.nextLine();
			
			String path = "C:\\Users\\miguel.oliveira\\Downloads\\" + fileName +".txt"; // caminho final para o arquivo
			
			KeyGenerator keygenerator = KeyGenerator.getInstance("DES"); //implementa um gerador de chaves simétricas --> DES - cifra simetrica por blocos
			SecretKey chaveDES = keygenerator.generateKey(); //implementa uma chave simétrica
			
			String[] lines = new String[] { Base64.getEncoder().encodeToString(chaveDES.getEncoded()) }; //colocar chave no array
			//String encodedKey = Base64.getEncoder().encodeToString(chaveDES.getEncoded());		
			System.out.println();

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
			
			//------------------------
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) { //true - indicar que não é para recriar arquivo
				for(String line : lines) { 	//para cada linha do array
					bw.write(line); 		//escrever na linha o texto da posicao do array
					bw.newLine(); 			//dar quebra de linha
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

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
