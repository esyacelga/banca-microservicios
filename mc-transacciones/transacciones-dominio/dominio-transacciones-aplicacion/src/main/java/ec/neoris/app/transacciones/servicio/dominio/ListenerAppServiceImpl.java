package ec.neoris.app.transacciones.servicio.dominio;

import ec.neoris.app.transacciones.servicio.dominio.handlers.TransaccionPersistCommandHandler;
import ec.neoris.app.transacciones.servicio.dominio.puertos.input.IListenerAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ListenerAppServiceImpl implements IListenerAppService {
    private final TransaccionPersistCommandHandler transaccionPersistCommandHandler;

    public ListenerAppServiceImpl(TransaccionPersistCommandHandler transaccionPersistCommandHandler) {
        this.transaccionPersistCommandHandler = transaccionPersistCommandHandler;
    }

    @Override
    public void inactivarCuentas(String clienteId, Boolean activarInactivar) {
        this.transaccionPersistCommandHandler.inactivarCuentasPorCliente(clienteId, activarInactivar);
    }
}
