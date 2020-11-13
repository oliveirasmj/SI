package ProjetoSI;

public class program {
	
	public static String criptografar(String msg, int chave) {
	    String msgCript = "";
	    for(int i = 0; i < msg.length(); i++) {
	        msgCript += (char) (msg.charAt(i) + chave);
	    }
	    return msgCript;
	}
	
	public static String descriptografar(String msgCript, int chave) {
	    String msg = "";
	    for(int i = 0; i < msgCript.length(); i++) {
	        msg += (char) (msgCript.charAt(i) - chave);
	    }
	    return msg;
	}
	
	public static void main(String[] args) {
	    String msg = "Olá, tudo bom?";
	    int chave = 3;

	    String msgCifrada = criptografar(msg, chave);
	    System.out.println("Msg criptografada: " + msgCifrada);
	    String textoPuro = descriptografar(msgCifrada, chave);
	    System.out.println("Msg original: " + textoPuro);
	}

}
