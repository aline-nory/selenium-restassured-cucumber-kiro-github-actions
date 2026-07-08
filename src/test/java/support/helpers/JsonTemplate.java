package support.helpers;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Le arquivos JSON da pasta template.api no classpath.
 * Permite centralizar payloads de request em arquivos externos.
 */
public class JsonTemplate {

    private JsonTemplate() {}

    /**
     * Le o conteudo de um arquivo JSON em template.api/.
     * @param fileName nome do arquivo (ex: "POST_CriarPost.json")
     * @return conteudo do arquivo como String
     */
    public static String load(String fileName) {
        InputStream input = JsonTemplate.class.getClassLoader()
                .getResourceAsStream("template.api/" + fileName);
        if (input == null) {
            throw new RuntimeException("Template nao encontrado: template.api/" + fileName);
        }
        try (Scanner scanner = new Scanner(input, "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    /**
     * Le o template e substitui um placeholder por um valor.
     * Exemplo: load("PUT_AtualizarPost.json").replace("\"id\":1", "\"id\":" + id)
     */
    public static String load(String fileName, String placeholder, String value) {
        return load(fileName).replace(placeholder, value);
    }
}
