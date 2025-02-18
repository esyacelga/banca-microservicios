package ec.neoris.app.transacciones.servicio.dominio.exception;

import ec.neoris.app.excepcion.comun.dominio.DomainException;

public class TransaccionDomainException extends DomainException {
    public TransaccionDomainException(String message) {
        super(message);
    }

    public TransaccionDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
