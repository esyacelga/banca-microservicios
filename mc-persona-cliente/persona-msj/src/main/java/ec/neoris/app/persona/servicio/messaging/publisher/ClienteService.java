package ec.neoris.app.persona.servicio.messaging.publisher;

import ec.neoris.app.comun.app.handler.ClienteDesactivadoEvent;
import ec.neoris.app.persona.servicio.dominio.puertos.output.IClientePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClienteService implements IClientePublisher {

    private final KafkaTemplate<String, ClienteDesactivadoEvent> kafkaTemplate;

    public ClienteService(KafkaTemplate<String, ClienteDesactivadoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void desactivarCliente(String clienteId) {
        try {
            log.info("🔄 Publicando evento de desactivación de cliente: {}", clienteId);

            ClienteDesactivadoEvent event = new ClienteDesactivadoEvent(clienteId, false);

            kafkaTemplate.send("cliente-desactivado-topic", event)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("✅ Evento enviado correctamente: {}", result.getProducerRecord().value());
                        } else {
                            log.error("Error al enviar evento a Kafka: {}", ex.getMessage());
                        }
                    });

        } catch (Exception ex) {
            log.error("Excepción inesperada al enviar evento de desactivación de cliente: {}", ex.getMessage(), ex);
        }
    }
}
