package support.environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Carrega configuracoes de ambiente a partir do config.properties via classpath.
 * Permite execucao em diferentes ambientes via Maven: mvn test -Denvironment=HQA
 *
 * Hierarquia de configuracao (prioridade):
 *   1. System Property (-Denvironment=HQA) — override por execucao
 *   2. Variavel de ambiente (ENVIRONMENT=HQA) — CI/CD
 *   3. config.properties — valor padrao
 */
public class Environment {

    public String environment;
    public String domain;
    public String apiBasePath;
    public String baseUrl;

    private static Properties config;

    static {
        config = loadFromClasspath("config.properties");
    }

    public static String ambiente_DEV = config.getProperty("url_DEV");
    public static String ambiente_HQA = config.getProperty("url_HQA");
    public static String api_DEV = config.getProperty("api_DEV");
    public static String api_HQA = config.getProperty("api_HQA");

    public Environment() {
        setupEnvironment();
    }

    private void setupEnvironment() {
        // Prioridade: System Property > Env Var > config.properties
        if (System.getProperty("environment") != null) {
            environment = System.getProperty("environment");
        } else if (System.getenv("ENVIRONMENT") != null) {
            environment = System.getenv("ENVIRONMENT");
        } else {
            environment = config.getProperty("environment");
        }

        switch (environment) {
            case "DEV":
                baseUrl = ambiente_DEV;
                apiBasePath = api_DEV;
                break;
            case "HQA":
                baseUrl = ambiente_HQA;
                apiBasePath = api_HQA;
                break;
            default:
                baseUrl = ambiente_DEV;
                apiBasePath = api_DEV;
        }

        domain = config.getProperty("domain");
    }

    public String getProperty(String key) {
        // Prioridade: variavel de ambiente > config.properties
        String envValue = System.getenv(key.replace(".", "_").toUpperCase());
        if (envValue != null) {
            return envValue;
        }
        return config.getProperty(key);
    }

    /**
     * Carrega properties do classpath (src/test/resources/).
     * Funciona independente do diretorio de execucao (local, CI, IDE).
     */
    private static Properties loadFromClasspath(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = Environment.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException(
                    "Arquivo " + fileName + " nao encontrado no classpath. "
                    + "Verifique se esta em src/test/resources/");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar " + fileName + ": " + e.getMessage());
        }
        return properties;
    }
}
