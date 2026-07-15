package config;

/**
 * Gerencia configuracoes de ambiente.
 * Carrega o .properties correto com base em -Denvironment=dev|hml|prod
 *
 * Hierarquia:
 *   1. System Property (-Denvironment=hml)
 *   2. Variavel de ambiente (ENVIRONMENT=hml)
 *   3. Padrao: dev
 */
public class Environment {

    private final ConfigReader config;
    private final String env;

    public final String baseUrl;
    public final String apiBaseUrl;
    public final String siteRoot;

    public Environment() {
        this.env = resolveEnvironment();
        this.config = new ConfigReader("environments/" + env + ".properties");
        this.baseUrl = config.get("base.url");
        this.apiBaseUrl = config.get("api.base.url");
        this.siteRoot = baseUrl.replaceAll("/web/index\\.php/.*", "");
    }

    public String get(String key) {
        return config.get(key);
    }

    public String get(String key, String defaultValue) {
        return config.get(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return config.getInt(key, defaultValue);
    }

    public String getEnv() {
        return env;
    }

    /**
     * Monta a URL completa de uma pagina interna a partir do path relativo.
     * Ex: getPageUrl("/web/index.php/pim/viewEmployeeList")
     */
    public String getPageUrl(String path) {
        return siteRoot + path;
    }

    private String resolveEnvironment() {
        if (System.getProperty("environment") != null) {
            return System.getProperty("environment");
        }
        if (System.getenv("ENVIRONMENT") != null) {
            return System.getenv("ENVIRONMENT");
        }
        return "dev";
    }
}
