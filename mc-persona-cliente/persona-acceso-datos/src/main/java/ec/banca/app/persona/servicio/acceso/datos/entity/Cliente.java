package ec.banca.app.persona.servicio.acceso.datos.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona {

    @Column(nullable = false, unique = true, length = 50)
    private String clienteId;

    @Column(nullable = false, length = 255)
    private String contrasenia;

    @Column(nullable = false)
    private Boolean estado;
}