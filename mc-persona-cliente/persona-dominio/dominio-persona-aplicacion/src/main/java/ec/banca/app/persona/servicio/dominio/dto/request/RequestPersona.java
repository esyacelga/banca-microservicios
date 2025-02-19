package ec.banca.app.persona.servicio.dominio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPersona {
    @NotNull (message = "El nombre de la persona no debe ser nula")
    private String nombre;
    @NotNull (message = "El genero de la persona no debe ser nula")
    private String genero;
    @NotNull (message = "La edad de la persona no debe ser nula")
    private Integer edad;
    @NotNull (message = "La identificacion de la persona no debe ser nula")
    private String identificacion;
    @NotNull (message = "La direccion de la persona no debe ser nula")
    private String direccion;
    @NotNull (message = "El telefono de la persona no debe ser nula")
    private String telefono;
}
