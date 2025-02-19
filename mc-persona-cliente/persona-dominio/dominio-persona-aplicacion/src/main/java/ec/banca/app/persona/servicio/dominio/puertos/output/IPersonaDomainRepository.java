package ec.banca.app.persona.servicio.dominio.puertos.output;

import ec.banca.app.persona.servicio.dominio.dto.PersonaDto;
import ec.banca.app.persona.servicio.dominio.exception.PersonaDomainException;

import java.util.UUID;

public interface IPersonaDomainRepository {

    PersonaDto actualizarPersona(UUID uuidCliente, PersonaDto personaDto) throws PersonaDomainException;
}
