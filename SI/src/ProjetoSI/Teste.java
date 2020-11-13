package ProjetoSI;

public class Teste {

	public static void main(String[] args) {
		
		//Pergunta 1
		Object[] arum = { true, false, false, true, true };
		Object[] ardois = { false, true, false, true, true };
		System.out.println(mesmosElementos(arum, ardois));

		//Pergunta 2
		String str1 = "acdBCDabc";
		getOccuringChar(str1);
		
		//Pergunta 3
		System.out.println(primo(7));
	}
	
	//Pergunta 1
	public static boolean mesmosElementos(Object[] a1, Object[] a2) {
		try {
			// verifica todos os elementos de ambos os arrays e caso haja alguma diferença devolve falso
			for (int i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return false;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// caso os arrays tenham tamanhos diferentes
			return false;
		}
		// é porque são iguais
		return true;
	}

	//Pergunta 2
	public static void getOccuringChar(String str) {
		// Criar um array
		int count[] = new int[256];

		int len = str.length();

		// Iniciar contagem
		for (int i = 0; i < len; i++)
			count[str.charAt(i)]++;

		// Criar matriz
		char ch[] = new char[str.length()];
		for (int i = 0; i < len; i++) {
			ch[i] = str.charAt(i);
			int find = 0;
			for (int j = 0; j <= i; j++) {

				// Se houver correspondencia
				if (str.charAt(i) == ch[j])
					find++;
			}

			if (find == 1)
				System.out.println(str.charAt(i) + "=" + count[str.charAt(i)]);
		}
	}
	
	//Pergunta 3
	public static boolean primo(int p) {
		if (p == 0 || p == 1) {
			return false;
		}
		else {
			for (int j = 2; j < p; j++) {
		        if (p % j == 0)
		            return false;   
		    }
		    return true;
		}
	}

}
