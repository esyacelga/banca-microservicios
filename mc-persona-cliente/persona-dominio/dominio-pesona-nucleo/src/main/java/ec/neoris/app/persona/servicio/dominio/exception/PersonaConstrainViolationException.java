package ec.neoris.app.persona.servicio.dominio.exception;

import ec.neoris.app.excepcion.comun.dominio.DomainException;

public class PersonaConstrainViolationException extends DomainException {
    public PersonaConstrainViolationException(String message) {
        super(message);
    }

    public PersonaConstrainViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
