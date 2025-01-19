package ec.neoris.app.transacciones.servicio.dominio;

import ec.neoris.app.transacciones.servicio.dominio.puertos.output.ICuentaDomainRepository;
import ec.neoris.app.transacciones.servicio.dominio.puertos.output.ITransaccionesDomainRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "ec.neoris.app.transacciones.servicio.dominio")
public class TransaccionTestConfiguration {
    @Bean
    ITransaccionesDomainRepository clientePersonaRepository() {
        return Mockito.mock(ITransaccionesDomainRepository.class);
    }

    @Bean
    ICuentaDomainRepository cuentaPersonaRepository() {
        return Mockito.mock(ICuentaDomainRepository.class);
    }


}
