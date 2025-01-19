package ec.neoris.app.transacciones.servicio.dominio.exception;

import ec.neoris.app.excepcion.comun.dominio.DomainException;

public class CuentaNotFoundDomainException extends DomainException {
    public CuentaNotFoundDomainException(String message) {
        super(message);
    }

    public CuentaNotFoundDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
