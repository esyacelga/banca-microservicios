package ec.banca.app.transacciones.servicio.dominio.exception;

import ec.banca.app.excepcion.comun.dominio.DomainException;

public class TransaccionNotFoundDomainException extends DomainException {
    public TransaccionNotFoundDomainException(String message) {
        super(message);
    }

    public TransaccionNotFoundDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
