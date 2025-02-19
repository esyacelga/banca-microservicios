package ec.banca.app.transacciones.servicio.dominio.exception;

import ec.banca.app.excepcion.comun.dominio.DomainException;

public class TransaccionesConstrainViolationException extends DomainException {
    public TransaccionesConstrainViolationException(String message) {
        super(message);
    }

    public TransaccionesConstrainViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
