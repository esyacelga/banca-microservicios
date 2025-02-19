package ec.banca.app.transacciones.servicio.dominio.exception;

import ec.banca.app.excepcion.comun.dominio.DomainException;

public class ClienteNotFoundDomainException extends DomainException {
    public ClienteNotFoundDomainException(String message) {
        super(message);
    }

    public ClienteNotFoundDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
