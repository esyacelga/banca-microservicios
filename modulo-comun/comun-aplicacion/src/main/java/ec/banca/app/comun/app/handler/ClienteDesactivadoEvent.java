package ec.banca.app.comun.app.handler;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor  // ✅ Constructor vacío requerido para la deserialización
@AllArgsConstructor
public class ClienteDesactivadoEvent {
    private String clienteId;
    private boolean estado;
}