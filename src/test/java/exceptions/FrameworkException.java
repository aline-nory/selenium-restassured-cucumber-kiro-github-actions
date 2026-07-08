package exceptions;

/**
 * Excecao customizada do framework.
 * Usada para erros de infraestrutura (config nao encontrado, template ausente, etc).
 */
public class FrameworkException extends RuntimeException {

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
