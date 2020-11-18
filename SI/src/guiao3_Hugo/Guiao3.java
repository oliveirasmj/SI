/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiao3_Hugo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Hugo
 */
public class Guiao3 {

    public byte[] key;
    public Cipher cifra;
    public SecretKey chave = null;
    public KeyGenerator keygenerator;
    //“Algoritmo/MododeCifra/Padding"
    public String algoritmoCifra;
    public String modeloCifra;
    public String tamanhoModelo;
    
    Scanner sc = new Scanner(System.in);
    String ALG = "";

    public Guiao3() {
    }

    public void gerarChave() throws NoSuchAlgorithmException, IOException {
    	System.out.println("Escreva o tipo de algoritmo(AES ou DES):");
    	ALG = sc.nextLine();
    	
        keygenerator = KeyGenerator.getInstance(ALG); //implementa um gerador de chaves simétricas --> DES - cifra simetrica por blocos
        chave = keygenerator.generateKey(); //implementa uma chave simétrica

        key = chave.getEncoded();

        System.out.println("Insira o nome do ficheiro da chave: ");
        writeByte(key);
    }
  
    public void cifrarFicheiroSelecionado(String fileName) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        if (chave == null) {
            gerarChave();
        }
        
        // Cria a cifra
        cifra = Cipher.getInstance(ALG+"/ECB/PKCS5Padding"); //Cipher - permite realizar operações de cifra e decifra

        // Inicializa a cifra para o processo de ENCRIPTACAO
        cifra.init(Cipher.ENCRYPT_MODE, chave); //ENCRYPT_MODE

        byte[] textoPuro = lerFicheiro(fileName).toString().getBytes();

        // Texto encriptado
        byte[] textoEncriptado = cifra.doFinal(textoPuro);
        System.out.println("Insira o nome do ficheiro encriptado: ");

        writeByte(textoEncriptado);
    }

    /**
     * Função para decifrar ficheiro celecionado utilizando a chave DES
     *
     * @param fileName
     */
    public void decifrarFicheiro(String fileName) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Scanner sc = new Scanner(System.in);
        
        if (ALG == null) {
        	System.out.println("Escreva o tipo de algoritmo(AES ou DES):");
        	ALG = sc.nextLine();
        }
        
        //obter chave 
        System.out.println("Insira o nome do ficheiro com a chave");
        String pathKeyFile = sc.nextLine();

        byte[] keyFileContent = Files.readAllBytes(Paths.get(pathKeyFile + ".txt"));

        cifra = Cipher.getInstance(ALG+"/ECB/PKCS5Padding");
        SecretKeySpec chaveFromFile = new SecretKeySpec(keyFileContent, ALG);
        //criar chave a partir de array de bytes

        // Cria a cifra
        // Inicializa a cifra também para o processo de DESINCRIPTACAO
        cifra.init(Cipher.DECRYPT_MODE, chaveFromFile); //DECRYPT_MODE

        Path path = Paths.get(fileName + ".txt");
        byte[] textoEncriptado = Files.readAllBytes(path);

        // Decriptografa o texto
        byte[] textoDecriptografado = cifra.doFinal(textoEncriptado);

        escreveParaFicheiroString(new String(textoDecriptografado));
    }

    public void escreveParaFicheiroString(String textoNovamenteClaro) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        //System.out.println("Texto Decriptografado : " + textoNovamenteClaro);
        System.out.println("Escreva o nome do ficheiro para guardar o texto desencriptado: ");
        String path = sc.nextLine() + ".txt";
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) { //true - indicar que n�o � para recriar arquivo
            bw.write(textoNovamenteClaro);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public StringBuilder lerFicheiro(String fileName) {
        String ficheiroTextoClaro = fileName + ".txt"; //caminho do arquivo
        FileReader fr = null;
        BufferedReader br = null;
        String allLines = "";
        StringBuilder sb = null;

        try {
            fr = new FileReader(ficheiroTextoClaro); //instanciar filereader com o caminho do arquivo
            br = new BufferedReader(fr); //br = new BufferedReader(new FileReader(path))
            sb = new StringBuilder();

            String line = br.readLine(); //se arquivo estiver no final � devolvido nulo

            while (line != null) { //enquanto nao estiver no final
                sb.append(line);
                line = br.readLine();	//ler a linha
            }
            allLines = sb.toString();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return sb;
    }

    // Method which write the bytes into a file 
    static void writeByte(byte[] bytes) {
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine() + ".txt";

        try {
            File file = new File(path);
            // Initialize a pointer 
            // in file using OutputStream 
            OutputStream os = new FileOutputStream(file);

            // Starts writing the bytes in it 
            os.write(bytes);

            // Close the file 
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public byte[] getkey() {
        return key;
    }

    public void setkey(byte[] key) {
        this.key = key;
    }

    public Cipher getcifra() {
        return cifra;
    }

    public void setcifra(Cipher cifra) {
        this.cifra = cifra;
    }

    public SecretKey getchave() {
        return chave;
    }

    public void setchave(SecretKey chave) {
        this.chave = chave;
    }

    public KeyGenerator getKeygenerator() {
        return keygenerator;
    }

    public void setKeygenerator(KeyGenerator keygenerator) {
        this.keygenerator = keygenerator;
    }

    public String getAlgoritmoCifra() {
        return algoritmoCifra;
    }

    public void setAlgoritmoCifra(String algoritmoCifra) {
        this.algoritmoCifra = algoritmoCifra;
    }

    public String getModeloCifra() {
        return modeloCifra;
    }

    public void setModeloCifra(String modeloCifra) {
        this.modeloCifra = modeloCifra;
    }

    public String getTamanhoModelo() {
        return tamanhoModelo;
    }

    public void setTamanhoModelo(String tamanhoModelo) {
        this.tamanhoModelo = tamanhoModelo;
    }
    
    

}
