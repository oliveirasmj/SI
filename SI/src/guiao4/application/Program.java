package guiao4.application;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class Program {

	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		
		Provider[] provs = Security.getProviders();
		for (int i = 0; i < provs.length; i++) {
			System.out.println(i + " - Nome do provider: " + provs[i].getName());
		}

		
		Provider prov = Security.getProvider("SunPKCS11-CartaoCidadao");
		KeyStore ks = KeyStore.getInstance("PKCS11", prov);
		ks.load(null, null);
		

		Enumeration<String> als = ks.aliases();
		while (als.hasMoreElements()) {
			System.out.println(als.nextElement());
		}
	}

}
