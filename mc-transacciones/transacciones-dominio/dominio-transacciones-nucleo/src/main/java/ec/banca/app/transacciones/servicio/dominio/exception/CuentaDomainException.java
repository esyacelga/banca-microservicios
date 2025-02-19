package ec.banca.app.transacciones.servicio.dominio.exception;

import ec.banca.app.excepcion.comun.dominio.DomainException;

public class CuentaDomainException extends DomainException {
    public CuentaDomainException(String message) {
        super(message);
    }

    public CuentaDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
