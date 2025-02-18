package ec.neoris.app.transacciones.servicio.messaging.listener;

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

    @KafkaListener(topics = "cliente-desactivado-topic", groupId = "grupo-cuentas", containerFactory = "kafkaListenerContainerFactory")
    public void procesarClienteDesactivado(ClienteDesactivadoEvent event) {
        log.info("🔄 Procesando evento de desactivación de cliente: {}", event.getClienteId());
        listenerAppService.inactivarCuentas(event.getClienteId(), event.isEstado());
        log.info("Cuentas desactivadas para cliente: " + event.getClienteId());
    }

}
