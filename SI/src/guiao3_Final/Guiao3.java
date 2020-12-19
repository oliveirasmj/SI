/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiao3_Final;

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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Hugo
 */
public class Guiao3 {

    public byte[] key;
    public Cipher cifraDES;
    public SecretKey chave = null;
    public KeyGenerator keygenerator;
    //“Algoritmo/MododeCifra/Padding"
    public String algoritmoCifra;
    public String modeloCifra;
    public String tamanhoModelo;
    

    public Guiao3() {
    }

    /*

        // Texto puro
        byte[] textoPuro = "Exemplo de texto puro".getBytes();

        System.out.println("Texto [Formato de Byte] : " + textoPuro);
        System.out.println("Texto Puro : " + new String(textoPuro));

        // Texto encriptado
        byte[] textoEncriptado = cifraDES.doFinal(textoPuro);

        System.out.println("Texto Encriptado : " + textoEncriptado);

        // Inicializa a cifra também para o processo de DESINCRIPTACAO
        cifraDES.init(Cipher.DECRYPT_MODE, chave); //DECRYPT_MODE

        // Decriptografa o texto
        byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado);

        System.out.println("Texto Decriptografado : " + new String(textoDecriptografado));   
     */
 
    
    public void gerarChave(String alg) throws NoSuchAlgorithmException{
        keygenerator = KeyGenerator.getInstance(alg); //implementa um gerador de chaves simétricas --> DES - cifra simetrica por blocos
        chave = keygenerator.generateKey(); //implementa uma chave simétrica

        key = chave.getEncoded();

        System.out.println("Insira o nome do ficheiro com a chave");
        writeByte(key);
    }

    public void cifrar(String fileName, String alg) throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        byte[] textoPuro = lerFicheiro(fileName).toString().getBytes();
        
        
        // SE ALGORITMO != DE ECB , gerar vetor e guardar em ficheiro
        //fazer split a string do ALG, ver modo de cifra
        //se !== de ECB entao vai gerar vetor inicial e  criar ficheiro 

         if (chave == null) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Insira o tipo de chave a gerar: AES / DES");
            String tipoChave = sc.nextLine(); 
            gerarChave(tipoChave);
        }
         
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        
        // Texto encriptado
        byte[] textoEncriptado = cipher.doFinal(textoPuro);
        System.out.println("Insira o nome para o ficheiro ENCRIPTADO: ");

        writeByte(textoEncriptado);
    } 
  
  
    public void decifrar(String fileName, String alg, String tipoDeChave) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        Scanner sc = new Scanner(System.in);
        
        
        //SE ALGORITMO != ECB  pedir ficheiro do vetor inicial, considerar  IvParameterSpec().
        //fazer split a string do ALG, ver modo de cifra
        //se !== de ECB entao vai pedir ficheiro com vetor inicial 
        
        //obter caminho chave 
        System.out.println("Insira o nome do ficheiro com a chave");
        String pathKeyFile = sc.nextLine();
        
        //obter a chave em ficheiro
        byte[] keyFileContent = Files.readAllBytes(Paths.get(pathKeyFile ));
        //passar bytes para key 
        SecretKeySpec chaveFromFile = new SecretKeySpec(keyFileContent, tipoDeChave);
        
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, chaveFromFile);
        
        Path path = Paths.get(fileName );
        byte[] textoEncriptado = Files.readAllBytes(path);

        // Decriptografa o texto
        byte[] textoDecriptografado = cipher.doFinal(textoEncriptado);

        escreveParaFicheiroString(new String(textoDecriptografado));
    } 

    public void escreveParaFicheiroString(String textoNovamenteClaro) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escreva o nome do ficheiro novamente em claro");
        String path = sc.nextLine() ;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) { //true - indicar que n�o � para recriar arquivo
            bw.write(textoNovamenteClaro);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public StringBuilder lerFicheiro(String fileName) {
        String ficheiroTextoClaro = fileName ; //caminho do arquivo
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
        String path = sc.nextLine() ;

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

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public Cipher getCifraDES() {
        return cifraDES;
    }

    public void setCifraDES(Cipher cifraDES) {
        this.cifraDES = cifraDES;
    }

    public SecretKey getChave() {
        return chave;
    }

    public void setChave(SecretKey chave) {
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
