package ec.neoris.app.excepcion.comun.dominio.valor;

import java.util.UUID;

public class CuentaId extends BaseId<UUID> {
    public CuentaId(UUID value) {
        super(value);
    }
}
