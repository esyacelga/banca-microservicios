package ec.neoris.app.excepcion.comun.dominio;


public class DomainException extends RuntimeException {


    public DomainException(String message) {
        super(message);
    }


    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
