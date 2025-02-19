package ec.banca.app.persona.servicio.dominio;

import ec.banca.app.persona.servicio.dominio.puertos.output.IClientePersonaDomainRepository;
import ec.banca.app.persona.servicio.dominio.puertos.output.IClientePublisher;
import ec.banca.app.persona.servicio.dominio.puertos.output.IPersonaDomainRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "ec.banca.app.persona.servicio.dominio")
public class PersonaTestConfiguration {
    @Bean
    IClientePersonaDomainRepository clientePersonaRepository() {
        return Mockito.mock(IClientePersonaDomainRepository.class);
    }

    @Bean
    IPersonaDomainRepository personaRepositoryOut() {
        return Mockito.mock(IPersonaDomainRepository.class);
    }

    @Bean
    IClientePublisher clientePublisher() {
        return Mockito.mock(IClientePublisher.class);
    }

}
