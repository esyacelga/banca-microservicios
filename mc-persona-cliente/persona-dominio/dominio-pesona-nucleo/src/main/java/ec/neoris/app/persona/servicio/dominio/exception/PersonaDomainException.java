package ec.banca.app.persona.servicio.dominio.exception;

import ec.banca.app.excepcion.comun.dominio.DomainException;

public class PersonaDomainException extends DomainException {
    public PersonaDomainException(String message) {
        super(message);
    }

    public PersonaDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
