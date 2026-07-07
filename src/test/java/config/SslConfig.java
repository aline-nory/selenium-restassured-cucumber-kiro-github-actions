package config;

import java.io.File;

/**
 * Configuração de infraestrutura SSL.
 *
 * Necessário em ambientes onde um proxy SSL (ex: antivírus corporativo)
 * intercepta conexões HTTPS e o Java 8 rejeita os certificados substituídos.
 * O arquivo ~/.maven-cacerts deve conter o certificado raiz do proxy importado.
 *
 * Em CI (GitHub Actions) este arquivo não existe e a configuração é ignorada,
 * pois o Java no Linux tem cacerts atualizados sem interferência de proxy.
 */
public class SslConfig {

    private static final String CACERTS_PATH =
            System.getProperty("user.home") + "/.maven-cacerts";
    private static final String CACERTS_PASSWORD = "changeit";

    private SslConfig() {
        // Classe utilitária — não instanciar
    }

    /**
     * Aplica o truststore customizado se o arquivo existir no ambiente local.
     * Deve ser chamado antes de qualquer requisição HTTPS.
     */
    public static void configurar() {
        if (new File(CACERTS_PATH).exists()) {
            System.setProperty("javax.net.ssl.trustStore", CACERTS_PATH);
            System.setProperty("javax.net.ssl.trustStorePassword", CACERTS_PASSWORD);
        }
    }
}
