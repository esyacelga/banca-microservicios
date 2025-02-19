package ec.banca.app.transacciones.servicio.dominio.exception;

import ec.banca.app.excepcion.comun.dominio.DomainException;

public class TransaccionDomainException extends DomainException {
    public TransaccionDomainException(String message) {
        super(message);
    }

    public TransaccionDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
