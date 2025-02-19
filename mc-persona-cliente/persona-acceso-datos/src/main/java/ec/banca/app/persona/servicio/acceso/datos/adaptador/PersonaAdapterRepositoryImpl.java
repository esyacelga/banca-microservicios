package ec.banca.app.persona.servicio.acceso.datos.adaptador;

import ec.banca.app.persona.servicio.acceso.datos.entity.Persona;
import ec.banca.app.persona.servicio.acceso.datos.repository.IPersonaRepository;
import ec.banca.app.persona.servicio.dominio.dto.PersonaDto;
import ec.banca.app.persona.servicio.dominio.exception.PersonaDomainException;
import ec.banca.app.persona.servicio.dominio.puertos.output.IPersonaDomainRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PersonaAdapterRepositoryImpl implements IPersonaDomainRepository {
    private final IPersonaRepository personaRepository;

    public PersonaAdapterRepositoryImpl(IPersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public PersonaDto actualizarPersona(UUID uuidCliente, PersonaDto personaDto) throws PersonaDomainException {
        Persona per = personaRepository.actualizarPersona(Persona.builder()
                .genero(personaDto.getGenero())
                .id(personaDto.getUuId())
                .edad(personaDto.getEdad())
                .nombre(personaDto.getNombre())
                .identificacion(personaDto.getIdentificacion())
                .nombre(personaDto.getNombre())
                .direccion(personaDto.getDireccion())
                .telefono(personaDto.getTelefono())
                .build());
        return PersonaDto.builder()
                .genero(per.getGenero())
                .nombre(per.getNombre())
                .identificacion(per.getIdentificacion())
                .nombre(per.getNombre())
                .direccion(per.getDireccion())
                .telefono(per.getTelefono())
                .build();

    }
}
