package ec.neoris.app.transacciones.servicio.dominio.exception;

import ec.neoris.app.excepcion.comun.dominio.DomainException;

public class TransaccionNotFoundDomainException extends DomainException {
    public TransaccionNotFoundDomainException(String message) {
        super(message);
    }

    public TransaccionNotFoundDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
