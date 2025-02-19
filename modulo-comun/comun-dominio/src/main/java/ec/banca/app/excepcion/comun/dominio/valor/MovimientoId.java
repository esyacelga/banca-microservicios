package ec.banca.app.excepcion.comun.dominio.valor;

import java.util.UUID;

public class MovimientoId extends BaseId<UUID> {
    public MovimientoId(UUID value) {
        super(value);
    }
}
