/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiao3_Hugo;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Hugo
 */
public class main {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {

        Scanner sc = new Scanner(System.in);
        int opcao;
        Guiao3 g = new Guiao3();

        do {
            menu();
            opcao = sc.nextInt();
            sc.nextLine(); 
            switch (opcao) {
                case 1: {
                    try {
                        System.out.println("Insira o tipo de chave a gerar: AES || DES");
                        String tipoChave = sc.nextLine(); 
                        g.gerarChave(tipoChave);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case 2: {
                    System.out.println("Insira o nome do ficheiro a CIFRAR");
                    String path = sc.nextLine(); 
                    System.out.println("Insira o algoritmo/mododecifra/padding a utilizar: AES/ECB/PKCS5Padding || DES/ECB/PKCS5Padding || AES/CBC/PKCS5Padding");
                    String alg = sc.nextLine(); 
                    g.cifrar(path, alg);
                    break;
                }case 3: {
                    System.out.println("Insira o nome do ficheiro a DECIFRAR");
                    String path = sc.nextLine(); 
                    System.out.println("Insira o algoritmo/mododecifra/padding a utilizar: AES/ECB/PKCS5Padding || DES/ECB/PKCS5Padding || AES/CBC/PKCS5Padding");
                    String alg = sc.nextLine(); 
                    System.out.println("Insira o tipo de chave a utilizar na DECIFRA: AES || DES");
                    String tipoChave = sc.nextLine(); 
                    g.decifrar(path, alg,tipoChave);
                    break;
                }
            }

        } while (opcao != 7);
    }
    
    public static void menu(){
        System.out.println("Selecione uma opcao");
        System.out.println("1 - Gerar chave DES");
        System.out.println("2 - CIFRAR um ficheiro");
        System.out.println("3 - DECIFRAR um ficheiro");
        System.out.println("7 - Fechar programa");
    }

}
