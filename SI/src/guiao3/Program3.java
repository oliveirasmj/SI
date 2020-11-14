package guiao3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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


public class Program3 {

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			
			//Caminho do ficheiro
			System.out.println("Insira o nome do ficheiro para guardar a chave: ");
			String fileName = sc.nextLine();
			String path = "C:\\Users\\miguel.oliveira\\Downloads\\" + fileName +".txt"; // caminho final para o arquivo
			
			//Gerar a chave
			KeyGenerator keygenerator = KeyGenerator.getInstance("DES"); //implementa um gerador de chaves simétricas --> DES - cifra simetrica por blocos
			SecretKey chaveDES = keygenerator.generateKey(); //implementa uma chave simétrica
			byte[] lines = chaveDES.getEncoded();
			System.out.println();
			
			//Escrever a chave no ficheiro
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) { //true - indicar que não é para recriar arquivo
				for(byte line : lines) { 	//para cada linha do array
					bw.write(line); 		//escrever na linha o texto da posicao do array
					bw.newLine(); 			//dar quebra de linha
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Cria a cifra
			Cipher cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding"); //Cipher - permite realizar operações de cifra e decifra
			// Inicializa a cifra para o processo de ENCRIPTACAO
			cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES); //ENCRYPT_MODE

			//Ficheiro a encriptar
			System.out.println("Qual o ficheiro a encriptar: ");
			fileName = sc.nextLine();
			String ficheiroTextoClaro = "C:\\Users\\miguel.oliveira\\Downloads\\" + fileName + ".txt"; //caminho do arquivo
			FileReader fr = null;
			BufferedReader br = null;
			
			String allLines = "";
			try {
				fr = new FileReader(ficheiroTextoClaro); //instanciar filereader com o caminho do arquivo
				br = new BufferedReader(fr); //br = new BufferedReader(new FileReader(path))
				StringBuilder sb = new StringBuilder();
				
				String line = br.readLine(); //se arquivo estiver no final é devolvido nulo
				
				while(line != null) { //enquanto nao estiver no final
					//System.out.println(line); //escrever a linha
					sb.append(line);
					line = br.readLine();	//ler a linha
				}
				allLines = sb.toString();
				
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			// Ir buscar o ficheiro da chave
			System.out.println("Introduza o nome do ficheiro da chave: ");
			fileName = sc.nextLine();
			String ficheiroChave = "C:\\Users\\miguel.oliveira\\Downloads\\" + fileName + ".txt"; //caminho do arquivo
			fr = null;
			br = null;
			
			try {
				fr = new FileReader(ficheiroChave); //instanciar filereader com o caminho do arquivo
				br = new BufferedReader(fr); //br = new BufferedReader(new FileReader(path))
				
				String line = br.readLine(); //se arquivo estiver no final é devolvido nulo
				
				while(line != null) { //enquanto nao estiver no final
					System.out.println(line); //escrever a linha
					line = br.readLine();	//ler a linha
				}
				
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			//--------------------------------------------------------------------
			//byte[] textoPuro = "Exemplo de texto puro".getBytes();
			byte[] textoPuro = allLines.getBytes();
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