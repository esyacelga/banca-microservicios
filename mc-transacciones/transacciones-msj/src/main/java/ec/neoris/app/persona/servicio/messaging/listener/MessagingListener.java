package ec.neoris.app.persona.servicio.messaging.listener;

import ec.neoris.app.comun.app.handler.ClienteDesactivadoEvent;
import ec.neoris.app.transacciones.servicio.dominio.puertos.input.IListenerAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessagingListener {
    private final IListenerAppService listenerAppService;

    public MessagingListener(IListenerAppService listenerAppService) {
        this.listenerAppService = listenerAppService;
    }

    @KafkaListener(topics = "cliente-desactivado-topic", groupId = "grupo-cuentas")
    public void procesarClienteDesactivado(ClienteDesactivadoEvent event) {
        listenerAppService.inactivarCuentas(event.getClienteId(), event.isEstado());
        System.out.println("Cuentas desactivadas para cliente: " + event.getClienteId());
    }


    public void inactivarCuentas(String clienteId, Boolean activarInactivar) {
        listenerAppService.inactivarCuentas(clienteId, activarInactivar);
    }
}
